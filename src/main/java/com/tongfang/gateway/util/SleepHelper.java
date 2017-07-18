package com.tongfang.gateway.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SleepHelper implements AutoCloseable {
    private static final Logger LOGGER = LoggerFactory.getLogger(SleepHelper.class);
    private final long start;
    private final long last;

    public SleepHelper(long last) {
        this.start = System.currentTimeMillis();
        this.last = last;
        LOGGER.trace("task start: {}", start);
    }

    @Override
    public void close() throws Exception {
        long wait = last - (System.currentTimeMillis() - start);
        LOGGER.trace("task wait: {}", wait);
        if (wait > 0)
            Thread.sleep(wait);
    }
}
