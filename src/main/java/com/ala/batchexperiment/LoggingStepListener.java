package com.ala.batchexperiment;

import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

@Component
public class LoggingStepListener implements StepExecutionListener {

    private final static Logger log = LoggerFactory.getLogger(LoggingStepListener.class);

    @Autowired
    private ThreadPoolTaskExecutor myThreadPoolTaskExecutor;

    @Override
    public void beforeStep(StepExecution stepExecution) {

    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        myThreadPoolTaskExecutor.shutdown();
        log.info(" step complete");
        return stepExecution.getExitStatus();
    }
}
