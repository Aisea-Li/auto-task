package com.example.task.entity.response;

import lombok.Data;

import java.util.List;

@Data
public class SpotAssetRes {

    private List<SpotBalance> balances;

    private List<SpotAsset> assets;

}
