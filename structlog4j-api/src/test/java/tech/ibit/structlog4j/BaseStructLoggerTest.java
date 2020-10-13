package tech.ibit.structlog4j;

import lombok.Value;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.event.Level;
import org.slf4j.impl.LogEntry;
import org.slf4j.impl.TestLogger;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static tech.ibit.structlog4j.test.TestUtils.assertMessage;

/**
 * 基础StructLogger测试
 *
 * @author IBIT程序猿
 */
public class BaseStructLoggerTest {

    private Logger logger;

    private List<LogEntry> entries;

    private ToLog user = () -> new Object[]{"user", "ibit-tech"};

    private String errorMessage = "Something error!";

    private Object[] errorMessages = new Object[] {"Something error, {}: '{}'", "user", "ibit-tech"};

    @Before
    public void setUp() {
        // 默认设置不转换异常
        StructLog4J.setTransStackTrace(false);
        logger = StructLoggerFactory.getLogger(BaseStructLoggerTest.class);
        entries = ((TestLogger) ((StructLogger) logger).slf4jLogger).getEntries();

    }

    @Test
    public void baseTest() {
        logger.error(errorMessage);
        assertEquals(1, entries.size());
        assertMessage(entries, 0, Level.ERROR, "_message=Something error!", false);
    }

    @Test
    public void baseMessagesTest() {
        logger.error(errorMessages);
        assertEquals(1, entries.size());
        assertMessage(entries, 0, Level.ERROR, "_message=Something error, user: 'ibit-tech'", false);
    }

    @Test
    public void singlePair() {
        logger.error(errorMessage, "user", "ibit-tech");
        assertEquals(1, entries.size());
        assertMessage(entries, 0, Level.ERROR, "_message=Something error!&user=ibit-tech", false);

    }

    @Test
    public void singlePairWithNullValue() {
        logger.error(errorMessage, "user", null);
        assertEquals(1, entries.size());
        assertMessage(entries, 0, Level.ERROR, "_message=Something error!&user=null", false);
    }

    @Test
    public void singlePairWithSpecialCharacters() {
        logger.error("Something\terror\r\n", "user", "ibit\ttech");
        assertEquals(1, entries.size());
        assertMessage(entries, 0, Level.ERROR, "_message=Something\\terror\\r\\n&user=ibit\\ttech", false);
    }

    @Test
    public void singlePairWithKeyContainsSpace() {
        logger.error(errorMessage, " user", null);
        assertEquals(2, entries.size());
        assertMessage(entries, 0, Level.ERROR, "_message=Key with spaces was passed in: ' user'", false);
        assertMessage(entries, 1, Level.ERROR, "_message=Something error!", false);
    }

    @Test
    public void singlePairWithNullKey() {
        logger.error(errorMessage, null, null);
        assertEquals(2, entries.size());
        assertMessage(entries, 0, Level.ERROR, "_message=Non-String or null key was passed in: null (null)", false);
        assertMessage(entries, 1, Level.ERROR, "_message=Something error!", false);
    }

    @Test
    public void singlePairWithNonStringKey() {
        logger.error(errorMessage, Collections.singletonList("user"), null);
        assertEquals(2, entries.size());
        assertMessage(entries, 0, Level.ERROR, "_message=Non-String or null key was passed in: [user] (class java.util.Collections$SingletonList)", false);
        assertMessage(entries, 1, Level.ERROR, "_message=Something error!", false);
    }


    @Test
    public void multiplePairs() {
        logger.error(errorMessage, "user", "ibit-tech", "age", 21);
        assertEquals(1, entries.size());
        assertMessage(entries, 0, Level.ERROR, "_message=Something error!&user=ibit-tech&age=21", false);

    }

    @Test
    public void multiplePairsWithNullValue() {
        logger.error(errorMessage, "user", null, "age", 21);
        assertEquals(1, entries.size());
        assertMessage(entries, 0, Level.ERROR, "_message=Something error!&user=null&age=21", false);
    }

    @Test
    public void multiplePairsWithSpecialCharacters() {
        logger.error("Something\terror\r\n", "user", "ibit\ttech", "age", 21);
        assertEquals(1, entries.size());
        assertMessage(entries, 0, Level.ERROR, "_message=Something\\terror\\r\\n&user=ibit\\ttech&age=21", false);
    }

    @Test
    public void multiplePairsWithKeyContainsSpace() {
        logger.error(errorMessage, " user", null, "age", 21);
        assertEquals(2, entries.size());
        assertMessage(entries, 0, Level.ERROR, "_message=Key with spaces was passed in: ' user'", false);
        assertMessage(entries, 1, Level.ERROR, "_message=Something error!", false);
    }

    @Test
    public void exception() {
        Throwable throwable = new RuntimeException("Test Exception");
        logger.error(errorMessage, throwable);
        assertEquals(1, entries.size());
        assertMessage(entries, 0, Level.ERROR, "_message=Something error!&_errorMessage=Test Exception", true);
    }

    @Test
    public void exceptionMessages() {
        Throwable throwable = new RuntimeException("Test Exception");
        logger.error(errorMessages, throwable);
        assertEquals(1, entries.size());
        assertMessage(entries, 0, Level.ERROR, "_message=Something error, user: 'ibit-tech'&_errorMessage=Test Exception", true);
    }

    @Test
    public void exceptionRootCause() {
        Throwable rootThrowable = new RuntimeException("Test Root");
        Throwable throwable = new RuntimeException("Text Exception", rootThrowable);
        logger.error(errorMessage, throwable);
        assertEquals(1, entries.size());
        assertMessage(entries, 0, Level.ERROR, "_message=Something error!&_errorMessage=Test Root", true);
    }

    @Test
    public void singlePairWithException() {
        Throwable throwable = new RuntimeException("Test Exception");
        logger.error(errorMessage, "user", "ibit-tech", throwable);
        assertEquals(1, entries.size());
        assertMessage(entries, 0, Level.ERROR, "_message=Something error!&user=ibit-tech&_errorMessage=Test Exception", true);

    }

    @Test
    public void singleToLog() {
        logger.error(errorMessage, user);
        assertEquals(1, entries.size());
        assertMessage(entries, 0, Level.ERROR, "_message=Something error!&user=ibit-tech", false);
    }


    @Test
    public void singleToLogWithException() {
        Throwable throwable = new RuntimeException("Test Exception");
        logger.error(errorMessage, user, throwable);
        assertEquals(1, entries.size());
        assertMessage(entries, 0, Level.ERROR, "_message=Something error!&user=ibit-tech&_errorMessage=Test Exception", true);
    }

    @Test
    public void multipleToLog() {
        ToLog age = () -> new Object[]{"age", 21};
        logger.error(errorMessage, user, age);
        assertMessage(entries, 0, Level.ERROR, "_message=Something error!&user=ibit-tech&age=21", false);
    }


    @Test
    public void singleToLogWithNullKey() {
        logger.error(errorMessage, new ErrorAge(21));
        assertEquals(2, entries.size());
        assertMessage(entries, 0, Level.ERROR, "_message=Key with spaces was passed in from class tech.ibit.structlog4j.BaseStructLoggerTest$ErrorAge.toLog(): ' age'", false);
        assertMessage(entries, 1, Level.ERROR, "_message=Something error!", false);
    }

    @Test
    public void singleToLogWithNonStringKey() {
        logger.error(errorMessage, new ErrorAge2(21));
        assertEquals(2, entries.size());
        assertMessage(entries, 0, Level.ERROR, "_message=Non-String or null key was passed in from class tech.ibit.structlog4j.BaseStructLoggerTest$ErrorAge2.toLog(): null (null)", false);
        assertMessage(entries, 1, Level.ERROR, "_message=Something error!", false);
    }

    @Test
    public void singleToLogWithNullToLog() {
        logger.error(errorMessage, new ErrorAge3());
        assertEquals(2, entries.size());
        assertMessage(entries, 0, Level.ERROR, "_message=Null or empty returned from class tech.ibit.structlog4j.BaseStructLoggerTest$ErrorAge3.toLog()", false);
        assertMessage(entries, 1, Level.ERROR, "_message=Something error!", false);
    }


    @Test
    public void singleToLogWithEmptyToLog() {
        logger.error(errorMessage, new ErrorAge4());
        assertEquals(2, entries.size());
        assertMessage(entries, 0, Level.ERROR, "_message=Null or empty returned from class tech.ibit.structlog4j.BaseStructLoggerTest$ErrorAge4.toLog()", false);
        assertMessage(entries, 1, Level.ERROR, "_message=Something error!", false);
    }

    @Test
    public void singleToLogWithOddToLog() {
        logger.error(errorMessage, new OddToLog(21));
        assertEquals(2, entries.size());
        assertMessage(entries, 0, Level.ERROR, "_message=Odd number of parameters (3) returned from class tech.ibit.structlog4j.BaseStructLoggerTest$OddToLog.toLog()", false);
        assertMessage(entries, 1, Level.ERROR, "_message=Something error!", false);
    }


    @Test
    public void allLevel() {
        logger.error("Error", user);
        logger.warn("Warn", user);
        logger.info("Info", user);
        logger.debug("Debug", user);
        logger.trace("Trace", user);

        assertEquals(entries.toString(), 5, entries.size());
        assertMessage(entries, 0, Level.ERROR, "_message=Error&user=ibit-tech", false);
        assertMessage(entries, 1, Level.WARN, "_message=Warn&user=ibit-tech", false);
        assertMessage(entries, 2, Level.INFO, "_message=Info&user=ibit-tech", false);
        assertMessage(entries, 3, Level.DEBUG, "_message=Debug&user=ibit-tech", false);
        assertMessage(entries, 4, Level.TRACE, "_message=Trace&user=ibit-tech", false);
    }

    @Test
    public void allLevel2() {
        logger.error(new String[] {"Error, {}={}", "user", "ibit-tech"});
        logger.warn(new String[] {"Warn, {}={}", "user", "ibit-tech"});
        logger.info(new String[] {"Info, {}={}", "user", "ibit-tech"});
        logger.debug(new String[] {"Debug, {}={}", "user", "ibit-tech"});
        logger.trace(new String[] {"Trace, {}={}", "user", "ibit-tech"});

        assertEquals(entries.toString(), 5, entries.size());
        assertMessage(entries, 0, Level.ERROR, "_message=Error, user=ibit-tech", false);
        assertMessage(entries, 1, Level.WARN, "_message=Warn, user=ibit-tech", false);
        assertMessage(entries, 2, Level.INFO, "_message=Info, user=ibit-tech", false);
        assertMessage(entries, 3, Level.DEBUG, "_message=Debug, user=ibit-tech", false);
        assertMessage(entries, 4, Level.TRACE, "_message=Trace, user=ibit-tech", false);
    }

    @Test
    public void mixedPairsAndToLog() {
        logger.error(errorMessage, user, "timestamp", "20191111 11:11:11", (ToLog) () -> new Object[]{"age", 21});
        logger.error(errorMessage, "timestamp", "20191111 11:11:11", user, (ToLog) () -> new Object[]{"age", 22});
        assertEquals(entries.toString(), 2, entries.size());
        assertMessage(entries, 0, Level.ERROR, "_message=Something error!&user=ibit-tech&timestamp=20191111 11:11:11&age=21", false);
        assertMessage(entries, 1, Level.ERROR, "_message=Something error!&timestamp=20191111 11:11:11&user=ibit-tech&age=22", false);
    }


    @Test
    public void mixedPairsAndToLogWithException() {
        Throwable throwable = new RuntimeException("Test Exception");
        logger.error(errorMessage, user, "timestamp", "20191111 11:11:11", (ToLog) () -> new Object[]{"age", 21}, throwable);
        logger.error(errorMessage, "timestamp", "20191111 11:11:11", user, (ToLog) () -> new Object[]{"age", 22}, throwable);
        assertEquals(entries.toString(), 2, entries.size());
        assertMessage(entries, 0, Level.ERROR, "_message=Something error!&user=ibit-tech&timestamp=20191111 11:11:11&age=21&_errorMessage=Test Exception"
                , true);
        assertMessage(entries, 1, Level.ERROR, "_message=Something error!&timestamp=20191111 11:11:11&user=ibit-tech&age=22&_errorMessage=Test Exception"
                , true);
    }

    @Value
    public class ErrorAge implements ToLog {

        private Integer age;

        @Override
        public Object[] toLog() {
            return new Object[]{" age", 21};
        }
    }


    @Value
    public class ErrorAge2 implements ToLog {

        private Integer age;

        @Override
        public Object[] toLog() {
            return new Object[]{null, 21};
        }
    }

    @Value
    public class ErrorAge3 implements ToLog {

        @Override
        public Object[] toLog() {
            return null;
        }
    }


    @Value
    public class ErrorAge4 implements ToLog {

        @Override
        public Object[] toLog() {
            return new Object[0];
        }
    }

    @Value
    public class OddToLog implements ToLog {

        private Integer age;

        @Override
        public Object[] toLog() {
            return new Object[]{"age", 21, "name"};
        }
    }


}