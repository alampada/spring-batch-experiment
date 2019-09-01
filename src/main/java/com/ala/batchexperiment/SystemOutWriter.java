package com.ala.batchexperiment;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

public class SystemOutWriter<T> implements ItemWriter<T> {

    private static Logger log = LoggerFactory.getLogger(SystemOutWriter.class);

    @Override
    public void write(List<? extends T> items) throws Exception {
        items.stream().forEach(i -> log.info("item: " + i));
    }
}
