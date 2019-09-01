package com.ala.batchexperiment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Service;

@Service
public class BatchExperimentExecutionListener implements JobExecutionListener {

    private static Logger log = LoggerFactory.getLogger(BatchExperimentExecutionListener.class);

    @Override
    public void beforeJob(JobExecution jobExecution) {
        log.info(jobExecution.getJobId() + " starting");

    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        log.info("Status: " + jobExecution.getStatus());
        log.info("Exceptions: " + jobExecution.getAllFailureExceptions().toString());

    }
}
