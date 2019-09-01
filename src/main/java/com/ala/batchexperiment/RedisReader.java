package com.ala.batchexperiment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

@Service
public class RedisReader implements ItemReader<IdHolder> {

    private static Logger logger = LoggerFactory.getLogger(RedisReader.class);

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private ConcurrentLinkedQueue<IdHolder> queue;

    @PostConstruct
    private void getIds() {
        Collection<String> ids = redisTemplate.keys("id*");
        logger.info("allids: " + ids);
        queue = new ConcurrentLinkedQueue(ids.stream().map( i-> {
            return new IdHolder(i.substring(2));
        }).collect(Collectors.toList()));
    }



    @Override
    public IdHolder read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        return queue.poll();
    }
}
