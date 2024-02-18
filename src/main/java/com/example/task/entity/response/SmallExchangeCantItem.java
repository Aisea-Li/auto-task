package com.example.task.entity.response;

import lombok.Data;

@Data
public class SmallExchangeCantItem {
    private String currency;
    private String currencyId;
    private String available;
    private String usdtAvailable;
    private String mxAvailable;
    private String fee;
    private Integer cantExchangeCode;
    private String cantExchangeReason;
    private String currencyFullName;
    private String icon;
}
