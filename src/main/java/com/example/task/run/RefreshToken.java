package com.example.task.run;

import com.example.task.client.MexcWebClient;
import com.example.task.entity.response.Response;
import com.example.task.utils.ResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class RefreshToken {


    @Autowired
    private MexcWebClient mexcWebClient;

    @Scheduled(fixedRate = 1, timeUnit = TimeUnit.HOURS)
    public void execute() {
        Response<?> res = mexcWebClient.refreshToken();
        if (ResponseUtils.isSuccess(res)) {
            log.info("refresh token success");
        } else {
            log.warn("refresh token fail,res:{}", res);
        }
    }
}
