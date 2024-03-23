package com.example.task.entity.request;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class LaunchpadBatchApplyReq {

    private List<String> activityIds;

}
