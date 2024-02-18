package com.example.task.client;


import com.example.task.entity.request.CurrentOrder;
import com.example.task.entity.request.PlaceOrderReq;
import com.example.task.entity.response.HistoryOrder;
import com.example.task.entity.response.KLineRes;
import com.example.task.entity.response.Order;
import com.example.task.entity.response.Page;
import com.example.task.entity.response.Response;
import com.example.task.entity.response.SmallExchangeQueryRes;
import com.example.task.entity.response.SmallExchangeRes;
import com.example.task.entity.response.SpotAssetRes;
import com.example.task.entity.response.SunShines;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Component
@FeignClient(
        name = "mexcWebClient",
        url = "${mexc.web.url:https://www.mexc.co}",
        configuration = {MexcWebClientFeignConfig.class},
        fallbackFactory = MexcWebClientFallbackFactory.class
)
public interface MexcWebClient {

    /**
     * token校验续期
     *
     * @return
     */
    @ResponseBody
    @PostMapping("/ucenter/api/login/validation")
    Response<?> refreshToken();

    /**
     * 查询阳光普照
     *
     * @return
     */
    @ResponseBody
    @GetMapping("/api/operateactivity/sun_shines/list")
    Response<List<SunShines>> querySunShinesList();


    /**
     * 查询阳光普照
     *
     * @return
     */
    @ResponseBody
    @PostMapping("/api/operateactivity/sun_shines/apply_lock/un_lock_mode/batch")
    Response<List<String>> applySunShinesBatch(@RequestParam("poolId") String poolId);


    /**
     * 查询现货资产
     *
     * @return
     */
    @ResponseBody
    @GetMapping("/api/platform/asset/api/asset/spot/convert/v2")
    Response<SpotAssetRes> querySpotAsset();

    /**
     * 小额兑换查询
     *
     * @return
     */
    @ResponseBody
    @GetMapping("/api/assetactivity/jot_currency_exchange/v2")
    Response<SmallExchangeQueryRes> querySmallCurrencyExchange();

    /**
     * 小额兑换执行
     *
     * @return
     */
    @ResponseBody
    @PostMapping("/api/assetactivity/jot_currency_exchange/v2/exchange")
    Response<SmallExchangeRes> smallCurrencyExchange(
            @RequestParam("currencyIds") String currencyIds,
            @RequestParam("version") String version
    );

    /**
     * k线查询
     *
     * @return
     */
    @ResponseBody
    @GetMapping("/api/platform/spot/market/kline")
    Response<KLineRes> queryKLine(
            @RequestParam("start") String start,
            @RequestParam("end") String end,
            @RequestParam("interval") String interval,
            @RequestParam("openPriceMode") String openPriceMode,
            @RequestParam("symbol") String symbol
    );

    /**
     * 下单
     *
     * @param req
     * @return
     */
    @ResponseBody
    @PostMapping("/api/platform/spot/order/place")
    Response<String> placeOrder(@RequestBody PlaceOrderReq req);


    /**
     * 当前订单
     *
     * @return
     */
    @ResponseBody
    @GetMapping("/api/platform/spot/order/current/orders/v2")
    Response<Page<CurrentOrder>> queryCurrentOrders(
            @RequestParam("currency") String currency,
            @RequestParam("market") String market,
            @RequestParam("orderTypes") String orderTypes,
            @RequestParam(value = "pageNum") Integer pageNum,
            @RequestParam(value = "pageSize") Integer pageSize
    );

    /**
     * 历史订单
     *
     * @return
     */
    @ResponseBody
    @GetMapping("/api/platform/spot/order/history/orders/v2")
    Response<Page<HistoryOrder>> queryHistoryOrders(
            @RequestParam("states") String states,
            @RequestParam("startTime") Long startTime,
            @RequestParam("endTime") Long endTime,
            @RequestParam(value = "pageNum") Integer pageNum,
            @RequestParam(value = "pageSize") Integer pageSize
    );

    /**
     * 订单详情
     *
     * @return
     */
    @ResponseBody
    @GetMapping("/api/platform/spot/order/deal/detail")
    Response<Order> queryOrderDetail(
            @RequestParam("orderId") String orderId,
            @RequestParam("orderType") String orderType
    );


    /**
     * 全部撤单
     *
     * @return
     */
    @ResponseBody
    @DeleteMapping("/api/platform/spot/order/cancel/v2")
    Response<?> cancelOrder();
}

@Slf4j
@Component
class MexcWebClientFallbackFactory implements FallbackFactory<MexcWebClient> {

    @Override
    public MexcWebClient create(Throwable cause) {
        log.warn("MexcWebClient,request fail,msg:" + cause.getMessage(), cause);
        return new MexcWebClient() {

            @Override
            public Response<?> refreshToken() {
                return null;
            }

            @Override
            public Response<List<SunShines>> querySunShinesList() {
                return null;
            }

            @Override
            public Response<List<String>> applySunShinesBatch(String poolId) {
                return null;
            }

            @Override
            public Response<SpotAssetRes> querySpotAsset() {
                return null;
            }

            @Override
            public Response<SmallExchangeQueryRes> querySmallCurrencyExchange() {
                return null;
            }

            @Override
            public Response<SmallExchangeRes> smallCurrencyExchange(String currencyIds, String version) {
                return null;
            }

            @Override
            public Response<KLineRes> queryKLine(String start, String end, String interval, String openPriceMode, String symbol) {
                return null;
            }

            @Override
            public Response<String> placeOrder(PlaceOrderReq req) {
                return null;
            }

            @Override
            public Response<Page<CurrentOrder>> queryCurrentOrders(String currency, String market, String orderTypes, Integer pageNum, Integer pageSize) {
                return null;
            }

            @Override
            public Response<Page<HistoryOrder>> queryHistoryOrders(String states, Long startTime, Long endTime, Integer pageNum, Integer pageSize) {
                return null;
            }

            @Override
            public Response<Order> queryOrderDetail(String orderId, String orderType) {
                return null;
            }

            @Override
            public Response<?> cancelOrder() {
                return null;
            }
        };
    }
}