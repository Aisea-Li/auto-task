package com.example.task.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public enum TradeOrderType implements CodeEnum<String> {

    LIMIT_ORDER("LIMIT_ORDER", "限价单"),
    MARKET_ORDER("MARKET_ORDER", "市价单"),
    ;

    private final String code;

    private final String desc;
}
