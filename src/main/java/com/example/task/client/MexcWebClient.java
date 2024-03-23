package com.example.task.client;


import com.example.task.entity.request.CurrentOrder;
import com.example.task.entity.request.LaunchpadBatchApplyReq;
import com.example.task.entity.request.PlaceOrderReq;
import com.example.task.entity.response.HistoryOrder;
import com.example.task.entity.response.KLineRes;
import com.example.task.entity.response.Launchpad;
import com.example.task.entity.response.Order;
import com.example.task.entity.response.Page;
import com.example.task.entity.response.Response;
import com.example.task.entity.response.SmallExchangeQueryRes;
import com.example.task.entity.response.SmallExchangeRes;
import com.example.task.entity.response.SpotAssetRes;
import com.example.task.entity.response.SunShines;
import lombok.extern.slf4j.Slf4j;
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
     * @return 返回值
     */
    @ResponseBody
    @PostMapping("/ucenter/api/login/validation")
    Response<?> refreshToken();

    /**
     * 查询阳光普照
     *
     * @return 返回值
     */
    @ResponseBody
    @GetMapping("/api/operateactivity/sun_shines/list")
    Response<List<SunShines>> querySunShinesList();


    /**
     * 批量投入阳光普照
     *
     * @return 返回值
     */
    @ResponseBody
    @PostMapping("/api/operateactivity/sun_shines/apply_lock/un_lock_mode/batch")
    Response<List<String>> applySunShinesBatch(@RequestParam("poolId") String poolId);

    /**
     * 查询launchpad
     *
     * @return 返回值
     */
    @ResponseBody
    @GetMapping("/api/operateactivity/launchpad")
    Response<List<Launchpad>> queryLaunchpadList(@RequestParam("hasJoin") String hasJoin);

    /**
     * 批量投入launchpad
     *
     * @return 返回值
     */
    @ResponseBody
    @PostMapping("/api/operateactivity/launchpad/batch_apply")
    Response<List<String>> applyLaunchpadBatch(@RequestBody LaunchpadBatchApplyReq req);

    /**
     * 查询现货资产
     *
     * @return 返回值
     */
    @ResponseBody
    @GetMapping("/api/platform/asset/api/asset/spot/convert/v2")
    Response<SpotAssetRes> querySpotAsset();

    /**
     * 小额兑换查询
     *
     * @return 返回值
     */
    @ResponseBody
    @GetMapping("/api/assetactivity/jot_currency_exchange/v2")
    Response<SmallExchangeQueryRes> querySmallCurrencyExchange();

    /**
     * 小额兑换执行
     *
     * @return 返回值
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
     * @return 返回值
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
     * @param req 下单请求参数
     * @return 返回值
     */
    @ResponseBody
    @PostMapping("/api/platform/spot/order/place")
    Response<String> placeOrder(@RequestBody PlaceOrderReq req);


    /**
     * 当前订单
     *
     * @return 返回值
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
     * @return 返回值
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
     * @return 返回值
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
     * @return 返回值
     */
    @ResponseBody
    @DeleteMapping("/api/platform/spot/order/cancel/v2")
    Response<?> cancelOrder();
}

@Slf4j
@Component
class MexcWebClientFallbackFactory extends FeignAbstractFallbackFactory<MexcWebClient> {
}