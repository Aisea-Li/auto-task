package com.example.task.entity.response;

import lombok.Data;

import java.util.List;

@Data
public class SmallExchangeQueryRes {

    private List<SmallExchangeItem> exchangeList;

    private List<SmallExchangeItem> cantExchangeList;

}
