package com.example.task.entity.response;

import lombok.Data;

@Data
public class SpotBalance {
    private String currency;
    private String coinId;
    private String total;
    private String frozen;
    private String available;
}
