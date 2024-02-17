package com.example.task.utils;

import com.example.task.entity.response.Response;

import java.util.Objects;

public class ResponseUtils {

    public static boolean hasData(Response<?> response) {
        return Objects.nonNull(response) && response.hasData();
    }

    public static boolean isSuccess(Response<?> response) {
        return Objects.nonNull(response) && response.isSuccess();
    }
}
