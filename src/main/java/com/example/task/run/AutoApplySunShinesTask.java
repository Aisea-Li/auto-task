package com.example.task.run;

import com.example.task.client.MexcWebClient;
import com.example.task.entity.enums.ApplyType;
import com.example.task.entity.enums.SunShineStatus;
import com.example.task.entity.response.Response;
import com.example.task.entity.response.SunShines;
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
public class AutoApplySunShinesTask {

    @Autowired
    private MexcWebClient mexcWebClient;

    @Scheduled(fixedRate = 5, timeUnit = TimeUnit.MINUTES)
    public void execute() {
        Response<List<SunShines>> res = mexcWebClient.querySunShinesList();
        if (!ResponseUtils.hasData(res)) {
            log.warn("query sun shines data fail,res:{}", res);
            return;
        }
        List<String> autoApplyIdList = ListUtils.emptyIfNull(res.getData()).stream()
                .filter(it -> StringUtils.equalsIgnoreCase(SunShineStatus.ACTIVE.getCode(), it.getSunshineStatus()))
                .filter(it -> StringUtils.equalsIgnoreCase(ApplyType.UN_APPLY.getCode(), it.getApplyType()))
                .map(SunShines::getId)
                .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(autoApplyIdList)) {
            log.debug("no new sun shines data");
            return;
        }

        String poolId = StringUtils.join(autoApplyIdList, ",");
        log.info("apply,pool id:{}", poolId);
        Response<List<String>> applyRes = mexcWebClient.applySunShinesBatch(poolId);
        if (ResponseUtils.hasData(applyRes)) {
            log.info("apply success,res:{}", applyRes);
        } else {
            log.warn("apply fail,res:{}", applyRes);
        }
    }
}
