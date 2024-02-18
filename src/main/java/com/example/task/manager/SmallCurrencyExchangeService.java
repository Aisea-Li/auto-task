package com.example.task.manager;

import com.example.task.client.MexcWebClient;
import com.example.task.entity.response.Response;
import com.example.task.entity.response.SmallExchangeItem;
import com.example.task.entity.response.SmallExchangeQueryRes;
import com.example.task.entity.response.SmallExchangeRes;
import com.example.task.utils.ResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class SmallCurrencyExchangeService {

    @Autowired
    private MexcWebClient mexcWebClient;

    /**
     * 小额兑换
     * 查询+兑换
     */
    public void smallCurrencyExchange() {
        Response<SmallExchangeQueryRes> res = mexcWebClient.querySmallCurrencyExchange();
        if (!ResponseUtils.hasData(res)) {
            return;
        }
        List<String> currencyIdList = ListUtils.emptyIfNull(res.getData().getExchangeList()).stream()
                .map(SmallExchangeItem::getCurrencyId)
                .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(currencyIdList)) {
            return;
        }
        // 小额兑换
        String currencyIds = String.join(",", currencyIdList);
        Response<SmallExchangeRes> response = mexcWebClient.smallCurrencyExchange(
                currencyIds,
                "V1"
        );
        if (ResponseUtils.hasData(response)) {
            log.info("small exchange,success,res:{}", response);
        } else {
            log.warn("small exchange,fail,res:{}", response);
        }
    }
}
