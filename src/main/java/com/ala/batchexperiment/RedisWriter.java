package com.ala.batchexperiment;


import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RedisWriter implements ItemWriter<EnrichedId> {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public void write(List<? extends EnrichedId> items) throws Exception {
        items.forEach(i -> redisTemplate.opsForValue().set("id" + String.valueOf(i.getId()), String.valueOf(i.getValue())));
    }
}
