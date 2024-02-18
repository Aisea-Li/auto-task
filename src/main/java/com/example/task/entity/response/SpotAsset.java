package com.example.task.entity.response;

import lombok.Data;

import java.util.List;

@Data
public class SpotAsset {
    private String coinId;
    private String currency;
    private String total;
    private String usdtTotal;
    private String available;
    private String usdtAvailable;
    private String frozen;
    private String usdtFrozen;
    private Integer sort;
    private String currencyFullName;
    private String currencyDisplayName;
    private String icon;
    private String marketType;
    private Boolean enableWithdraw;
    private Boolean enableDeposit;
    private String posId;
    private Integer scale;
    private String loopName;
    private Boolean enableLoop;
    private List<String> markets;
    private String disableWithdrawReasonCN;
    private String disableWithdrawReasonEN;
    private String disableWithdrawReason;
    private String disableDepositReasonCN;
    private String disableDepositReasonEN;
    private String disableDepositReason;
    private Boolean hiddenSmall;
    private Long openTime;
    private Boolean offline;
    private String currencyStatusEnum;
}
