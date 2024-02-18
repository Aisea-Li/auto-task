package com.example.task.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public enum TradeStatus implements CodeEnum<Integer> {

    NEW(1, "未成交"),
    FILLED(2, "已成交"),
    PARTIALLY_FILLED(3, " 部分成交"),
    CANCELED(4, "已撤销"),
    PARTIALLY_CANCELED(5, "部分撤销"),
    ;

    private final Integer code;

    private final String desc;

}
