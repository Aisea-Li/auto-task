package com.example.task.entity.response;

import lombok.Data;

import java.util.List;

@Data
public class SmallExchangeRes {

    private List<String> successList;

    private List<SmallExchangeFailedItem> failedList;

    private String exchangeAmount;

    private String fee;
}
