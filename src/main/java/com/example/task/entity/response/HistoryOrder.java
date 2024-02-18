package com.example.task.entity.response;

import lombok.Data;


@Data
public class HistoryOrder {
    private String id;
    private String amount;
    private String avgPrice;
    private String createTime;
    private String currency;
    private String dealAmount;
    private String dealQuantity;
    private String estPnl;
    private String fee;
    private String market;
    private String ocoId;
    private String orderType;
    private String price;
    private String quantity;
    private String source;

    /**
     * 状态
     * 2-已成交 4-已取消 5-部分成交
     */
    private String state;
    private String symbol;
    private String tradeType;
    private String triggerId;
    private String triggerPrice;
    private String triggerType;
    private String uniqueId;
    private String updateTime;
}
