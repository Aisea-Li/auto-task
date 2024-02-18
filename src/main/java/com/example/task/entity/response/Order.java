package com.example.task.entity.response;


import lombok.Data;

import java.util.List;

@Data
public class Order {
    private String orderId;
    private String symbol;
    private String orderType;
    private String triggerType;
    private String tradeType;
    private String triggerTradeType;
    private String price;
    private String quantity;
    private String amount;
    private String dealQuantity;
    private String dealAmount;
    private String triggerPrice;
    private String state;
    private String triggerState;
    private Long createTime;
    private String avgPrice;
    private List<OrderDetail> deals;
}
