package com.example.task.entity.request;

import lombok.Data;


@Data
public class CurrentOrder {
    private String id;
    private String createTime;
    private String updateTime;
    private String currency;
    private String market;
    private String symbol;
    private String tradeType;
    private String orderType;
    private String price;
    private String triggerPrice;
    private String quantity;
    private String amount;
    private String dealQuantity;
    private String dealAmount;
    private String avgPrice;
    private String state;
    private String source;
    private String triggerType;
    private String fee;
    private String uniqueId;
    private String triggerId;
    private String estPnl;
    private String ocoId;
}
