package com.my.learning.slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Slf4jTest {
    private static final Logger logger = LoggerFactory.getLogger(Slf4jTest.class);

    public static void main(String[] args) {
        logger.info("Hello, SLF4J");
    }
}
