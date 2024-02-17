package com.example.task.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ApplyType {

    UN_APPLY("UN_APPLY", "未投入"),
    APPLY("APPLY", "已投入"),
    ;

    private final String code;

    private final String desc;
}
