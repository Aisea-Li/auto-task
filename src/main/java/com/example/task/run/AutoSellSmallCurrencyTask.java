package com.example.task.run;

import com.example.task.client.MexcWebClient;
import com.example.task.entity.enums.TradeOrderType;
import com.example.task.entity.enums.TradeType;
import com.example.task.entity.request.PlaceOrderReq;
import com.example.task.entity.response.KLineRes;
import com.example.task.entity.response.Response;
import com.example.task.entity.response.SpotAsset;
import com.example.task.entity.response.SpotAssetRes;
import com.example.task.manager.SmallCurrencyExchangeService;
import com.example.task.utils.ResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

@Slf4j
@Component
public class AutoSellSmallCurrencyTask {

    @Autowired
    private MexcWebClient mexcWebClient;

    @Autowired
    private SmallCurrencyExchangeService smallCurrencyExchangeService;

    @Value("${mexc.asset.exclude:}")
    private Set<String> excludeSet;

    @Scheduled(fixedRate = 5, timeUnit = TimeUnit.SECONDS)
    public void execute() {
        Response<SpotAssetRes> res = mexcWebClient.querySpotAsset();
        if (!ResponseUtils.hasData(res)) {
            log.warn("query spot asset fail,res:{}", res);
            return;
        }
        SpotAssetRes spotAssetRes = res.getData();
        ListUtils.emptyIfNull(spotAssetRes.getAssets()).stream()
                .filter(it -> {
                    if (excludeSet.contains(StringUtils.lowerCase(it.getCurrency()))) {
                        return false;
                    }
                    if (CollectionUtils.isEmpty(it.getMarkets()) || !it.getMarkets().contains("USDT")) {
                        return false;
                    }
                    if (Double.parseDouble(it.getUsdtAvailable()) < 0.5) {
                        return false;
                    }
                    if (Double.parseDouble(it.getUsdtTotal()) > 50) {
                        return false;
                    }
                    return true;
                })
                .forEach(this::handleSpotAsset);
    }

    /**
     * 处理资产
     *
     * @param spotAsset 现货资产
     */
    private void handleSpotAsset(SpotAsset spotAsset) {
        // 查询k线 看是否需要出售
        String currency = spotAsset.getCurrency();
        String market = "USDT";
        Date endDate = new Date();
        Date startDate = DateUtils.addDays(endDate, -30);
        String start = String.valueOf(startDate.getTime());
        String end = String.valueOf(endDate.getTime());
        String interval = "Day1";
        String openPriceMode = "LAST_CLOSE";
        String symbol = String.format("%s_%s", currency, market);
        Response<KLineRes> res = mexcWebClient.queryKLine(
                start,
                end,
                interval,
                openPriceMode,
                symbol
        );
        if (!ResponseUtils.hasData(res)) {
            log.warn("queryKLine,fail,res:{}", res);
            return;
        }
        KLineRes kLineRes = res.getData();
        List<Double> highPriceList = kLineRes.getH();
        double maxHighPrice = 0;
        for (Double h : highPriceList) {
            maxHighPrice = Math.max(maxHighPrice, h);
        }
        List<Double> openPriceList = kLineRes.getO();
        double minOpenPrice = Double.MAX_VALUE;
        for (Double o : openPriceList) {
            minOpenPrice = Math.min(minOpenPrice, o);
        }
        List<Double> closePriceList = kLineRes.getC();
        double currentPrice = closePriceList.get(closePriceList.size() - 1);
        double quantity = Double.parseDouble(spotAsset.getAvailable());
        double totalUsdt = currentPrice * quantity;
        // 价格有上涨空间 持有
        if (currentPrice > maxHighPrice * 0.9) {
            // 当前价大于最近30天最高价出售
            log.debug("more than 90% of maxHighPrice,currentPrice:{},maxHighPrice:{}", currentPrice, maxHighPrice);
            return;
        }
        // 总价小于4/跌破最低价且总价低于5 小额兑换
        if (totalUsdt < 4 || (currentPrice < minOpenPrice && totalUsdt < 5)) {
            log.info("price too low,do small currency exchange,currency:{}", currency);
            smallCurrencyExchangeService.smallCurrencyExchange();
            return;
        }
        // 总价过低/过高 无法卖出
        if (totalUsdt < 5 || totalUsdt > 50) {
            log.warn("can not sell,total usdt:{}", totalUsdt);
            return;
        }
        // 售出
        PlaceOrderReq req = PlaceOrderReq.builder()
                .currency(currency)
                .market(market)
                .tradeType(TradeType.SELL.name())
                .orderType(TradeOrderType.MARKET_ORDER.name())
                .price(String.valueOf(currentPrice))
                .quantity(spotAsset.getAvailable())
                .build();
        Response<String> placeOrderRes = mexcWebClient.placeOrder(req);
        if (ResponseUtils.isSuccess(placeOrderRes)) {
            log.info("sell success,currency:{}", currency);
            // 卖不完 进行一次小额兑换 异步延迟处理等待卖单完成
            CompletableFuture.runAsync(() -> {
                LockSupport.parkNanos(TimeUnit.MINUTES.toNanos(2));
                smallCurrencyExchangeService.smallCurrencyExchange();
            });
        } else {
            log.warn("sell fail,currency:{},res:{}", currency, placeOrderRes);
        }
    }
}
