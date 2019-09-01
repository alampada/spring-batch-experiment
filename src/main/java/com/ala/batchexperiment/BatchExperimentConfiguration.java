package com.ala.batchexperiment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.support.SynchronizedItemStreamReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.client.HttpServerErrorException;

@Configuration
@EnableBatchProcessing
public class BatchExperimentConfiguration {

    private static final Logger log = LoggerFactory.getLogger(BatchExperimentConfiguration.class);

    @Autowired
    public RedisWriter redisWriter;

    @Autowired RedisReader redisReader;

    @Bean
    public FlatFileItemReader<IdHolder> reader() {
        return new FlatFileItemReaderBuilder<IdHolder>()
                .name("idReader")
                .resource(new ClassPathResource("sample-data.csv"))
                .delimited()
                .names(new String[]{"id"})
                .fieldSetMapper(new BeanWrapperFieldSetMapper<IdHolder>() {{
                    setTargetType(IdHolder.class);
                }})
                .build();
    }

    @Bean
    public SynchronizedItemStreamReader synchronizedItemStreamReader() {
        SynchronizedItemStreamReader<IdHolder> idHolderSynchronizedItemStreamReader = new SynchronizedItemStreamReader<>();
        idHolderSynchronizedItemStreamReader.setDelegate(reader());
        return idHolderSynchronizedItemStreamReader;
    }

    @Bean
    public SystemOutWriter<EnrichedId> systemOutWriter() {
        return new SystemOutWriter<>();
    }

    @Bean
    public Job experimentJob(StepBuilderFactory stepBuilderFactory,
                             JobBuilderFactory jobBuilderFactory,
                             Step step,
                             BatchExperimentExecutionListener listener
                             ) {


        return jobBuilderFactory.get("dummyJob")
                .incrementer(new RunIdIncrementer())
                .flow(step)
                .end()
                .build();
    }

    @Bean
    public Step step(IdProcessor idProcessor,
                     StepBuilderFactory stepBuilderFactory,
                     LoggingStepListener loggingStepListener,
                     ThreadPoolTaskExecutor myThreadPoolTaskExecutor) {
        return stepBuilderFactory.get("step").listener(loggingStepListener)
                .<IdHolder, EnrichedId>chunk(10)
                .reader(redisReader)
                .processor(idProcessor)
                .writer(redisWriter)
                .faultTolerant()
                .retryLimit(3)
                .retry(HttpServerErrorException.class)
                .taskExecutor(myThreadPoolTaskExecutor)
                .build();

    }
}
