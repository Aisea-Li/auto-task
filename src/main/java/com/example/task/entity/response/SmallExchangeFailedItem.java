package com.example.task.entity.response;

import lombok.Data;

@Data
public class SmallExchangeFailedItem {
    private String currency;
    private String currencyId;
    private Integer errorCode;
    private String errorMessage;
}
