package org.slf4j.impl;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author IBIT程序猿
 */
public class TestLoggerTest {

    Logger logger = LoggerFactory.getLogger("TestLoggerTest");

    @Test
    public void info() {
        logger.info("HaHaHaHaHa!");
    }
}