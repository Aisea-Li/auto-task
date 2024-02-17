package com.example.task.utils;

import com.example.task.entity.enums.CodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.SetUtils;

import java.util.EnumSet;

@Slf4j
public class EnumUtils extends org.apache.commons.lang3.EnumUtils {

    public static <C, E extends Enum<E> & CodeEnum<C>> E getEnumByCode(C code, Class<E> clazz) {
        return SetUtils.emptyIfNull(EnumSet.allOf(clazz)).stream()
                .filter(item -> code.equals(item.getCode()))
                .findFirst()
                .orElseGet(() -> {
                    log.error("getEnumByCode,fail,code:{},clazz:{}", code, clazz);
                    return null;
                });
    }
}
