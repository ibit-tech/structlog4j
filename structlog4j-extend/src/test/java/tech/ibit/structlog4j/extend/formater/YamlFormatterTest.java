package tech.ibit.structlog4j.extend.formater;

import org.junit.Test;
import org.slf4j.LoggerFactory;
import tech.ibit.structlog4j.Logger;
import tech.ibit.structlog4j.StructLoggerFactory;

import static org.junit.Assert.*;

/**
 * @author IBIT-TECH
 */
public class YamlFormatterTest {

    Logger logger = StructLoggerFactory.getLogger(YamlFormatterTest.class);

    @Test
    public void format() {
        logger.error("Error to access server", "ip", "127.0.0.1", new RuntimeException("Test runtime exception"));
    }
}