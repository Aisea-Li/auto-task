package com.example.task.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LaunchpadStatus {
    UNDERWAY("UNDERWAY", "正在进行中"),
    ;

    private final String code;

    private final String desc;
}
