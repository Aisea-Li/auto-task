package com.example.task.run;

import com.example.task.manager.SmallCurrencyExchangeManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class AutoSmallExchangeTask {
    @Autowired
    private SmallCurrencyExchangeManager smallCurrencyExchangeManager;

    @Scheduled(fixedRate = 6, timeUnit = TimeUnit.HOURS)
    public void execute() {
        smallCurrencyExchangeManager.smallCurrencyExchange();
    }
}
