package com.example.task.run;

import com.example.task.client.MexcWebClient;
import com.example.task.entity.enums.ApplyType;
import com.example.task.entity.enums.LaunchpadStatus;
import com.example.task.entity.request.LaunchpadBatchApplyReq;
import com.example.task.entity.response.Launchpad;
import com.example.task.entity.response.Response;
import com.example.task.utils.ResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Component
public class AutoApplyLaunchpadTask {

    @Autowired
    private MexcWebClient mexcWebClient;

    @Scheduled(fixedRate = 5, timeUnit = TimeUnit.MINUTES)
    public void execute() {
        Response<List<Launchpad>> res = mexcWebClient.queryLaunchpadList("ALL");
        if (!ResponseUtils.hasData(res)) {
            log.warn("query launchpad data fail,res:{}", res);
            return;
        }
        List<String> autoApplyIdList = ListUtils.emptyIfNull(res.getData()).stream()
                .filter(it -> StringUtils.equalsIgnoreCase(LaunchpadStatus.UNDERWAY.getCode(), it.getActivityStatus()))
                .filter(it -> StringUtils.equalsIgnoreCase(ApplyType.UN_APPLY.getCode(), it.getApplyType()))
                .map(Launchpad::getId)
                .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(autoApplyIdList)) {
            log.debug("no new launchpad data");
            return;
        }
        log.info("apply,auto apply id list:{}", autoApplyIdList);
        LaunchpadBatchApplyReq req = LaunchpadBatchApplyReq.builder()
                .activityIds(autoApplyIdList)
                .build();
        Response<List<String>> applyRes = mexcWebClient.applyLaunchpadBatch(req);
        if (ResponseUtils.hasData(applyRes)) {
            log.info("apply success,res:{}", applyRes);
        } else {
            log.warn("apply fail,res:{}", applyRes);
        }
    }
}
