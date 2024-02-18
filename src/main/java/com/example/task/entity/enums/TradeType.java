package com.example.task.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public enum TradeType implements CodeEnum<Integer> {

    BUY(1, "买入"),
    SELL(2, "买入");

    private final Integer code;

    private final String desc;
}
