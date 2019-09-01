package com.ala.batchexperiment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

@Component
public class TaskExecutorConfiguration {

    private static final Logger log = LoggerFactory.getLogger(TaskExecutorConfiguration.class);

    @Bean
    public ThreadPoolTaskExecutor myThreadPoolTaskExecutor() {
        log.info("my create");
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(5);
        threadPoolTaskExecutor.setMaxPoolSize(5);
        threadPoolTaskExecutor.setWaitForTasksToCompleteOnShutdown(false);
        return  threadPoolTaskExecutor;
    }
}
