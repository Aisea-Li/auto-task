package com.example.task.run;

import com.example.task.manager.SmallExchangeManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class AutoSmallExchangeTask {
    @Autowired
    private SmallExchangeManager smallExchangeManager;

    @Scheduled(fixedRate = 6, timeUnit = TimeUnit.HOURS)
    public void execute() {
        smallExchangeManager.smallExchange();
    }
}
