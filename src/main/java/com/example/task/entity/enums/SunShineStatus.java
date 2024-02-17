package com.example.task.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SunShineStatus {
    ACTIVE("ACTIVE", "激活"),
    ;

    private final String code;

    private final String desc;
}
