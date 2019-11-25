package tech.ibit.structlog4j;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.event.Level;
import org.slf4j.impl.LogEntry;
import org.slf4j.impl.TestLogger;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static tech.ibit.structlog4j.test.TestUtils.assertMessage;

/**
 * 基础StructLogger测试
 *
 * @author IBIT-TECH
 */
public class ComplexStructLoggerTest {

    private Logger logger;

    private List<LogEntry> entries;

    private ToLog user = () -> new Object[]{"user", "ibit-tech"};

    private String errorMessage = "Something error!";

    private Formatter formatter;

    private Throwable throwable;

    @Before
    public void setUp() {
        // 异常转换
        StructLog4J.setTransStackTrace(true);
        StructLog4J.setGlobalConfig(user);

        logger = StructLoggerFactory.getLogger(ComplexStructLoggerTest.class);
        entries = ((TestLogger) ((StructLogger) logger).slf4jLogger).getEntries();
        formatter = StructLog4J.getFormatter();
        throwable = new RuntimeException("Test Exception");

    }


    @Test
    public void exception() {
        logger.error(errorMessage, throwable);
        assertEquals(1, entries.size());
        assertMessage(entries, 0, Level.ERROR, "_message=Something error!&_errorMessage=Test Exception&_stackTrace=" + formatter.getStackTrace(throwable) + "&user=ibit-tech"
                , false);
    }

    @Test
    public void exceptionRootCause() {
        Throwable rootThrowable = new RuntimeException("Test Root");
        Throwable throwable = new RuntimeException("Text Exception", rootThrowable);
        logger.error(errorMessage, throwable);
        assertEquals(1, entries.size());
        assertMessage(entries, 0, Level.ERROR, "_message=Something error!&_errorMessage=Test Root&_stackTrace=" + formatter.getStackTrace(throwable) + "&user=ibit-tech"
                , false);
    }

    @Test
    public void singlePairWithException() {
        logger.error(errorMessage, "age", 21, throwable);
        assertEquals(1, entries.size());
        assertMessage(entries, 0, Level.ERROR, "_message=Something error!&age=21&_errorMessage=Test Exception&_stackTrace=" + formatter.getStackTrace(throwable) + "&user=ibit-tech"
                , false);

    }

    @Test
    public void singleToLogWithException() {
        logger.error(errorMessage, (ToLog) () -> new Object[]{"age", 21}, throwable);
        assertEquals(1, entries.size());
        assertMessage(entries, 0, Level.ERROR, "_message=Something error!&age=21&_errorMessage=Test Exception&_stackTrace=" + formatter.getStackTrace(throwable) + "&user=ibit-tech"
                , false);
    }


    @Test
    public void mixedPairsAndToLogWithException() {
        logger.error(errorMessage, "timestamp", "20191111 11:11:11", (ToLog) () -> new Object[]{"age", 21}, throwable);
        assertEquals(entries.toString(), 1, entries.size());
        assertMessage(entries, 0, Level.ERROR, "_message=Something error!&timestamp=20191111 11:11:11&age=21&_errorMessage=Test Exception&_stackTrace=" + formatter.getStackTrace(throwable) + "&user=ibit-tech"
                , false);
    }


}