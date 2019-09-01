package com.ala.batchexperiment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class IdProcessor implements ItemProcessor<IdHolder, EnrichedId> {

    private static final Logger log = LoggerFactory.getLogger(IdProcessor.class);

    @Value("${enrichUrl}")
    private String url;

    @Autowired
    private RestTemplate restTemplate;

    private EnrichedId processId(IdHolder id) {
        log.info(url + "/" + id.getId());
        Integer result = restTemplate.getForObject(url + "/" + id.getId(), Integer.class);
        return new EnrichedId(id.getId(), result);
    }

    @Override
    public EnrichedId process(IdHolder item) throws Exception {
        return processId(item);
    }
}
