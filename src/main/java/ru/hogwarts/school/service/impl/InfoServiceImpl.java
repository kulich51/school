package ru.hogwarts.school.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.service.InfoService;

import java.util.stream.Stream;

@Service
public class InfoServiceImpl implements InfoService {

    @Value("${server.port}")
    Integer serverPort;

    @Override
    public Integer getPort() {
        return serverPort;
    }

    @Override
    public int sum() {
        Logger logger = LoggerFactory.getLogger(InfoServiceImpl.class);
        long start = System.currentTimeMillis();
        long delta;

        int sum_first_method = Stream.iterate(1, a -> a +1)
                .limit(1_000_000)
                .reduce(0, (a, b) -> a + b );

        delta = System.currentTimeMillis() - start;
        logger.info("Sequence stream - " + delta + " ms");

        start = System.currentTimeMillis();
        int sum_second_method = Stream
                .iterate(1, a -> a +1)
                .limit(1_000_000)
                .parallel()
                .reduce(0, (a, b) -> a + b );
        delta = System.currentTimeMillis() - start;
        logger.info("Parallel stream - " + delta + " ms");

        logger.info("Equality: " + (sum_first_method == sum_second_method));

        return sum_second_method;
    }
}
