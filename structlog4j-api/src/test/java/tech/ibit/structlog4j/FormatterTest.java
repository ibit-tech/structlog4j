package tech.ibit.structlog4j;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


/**
 * Formatter默认方法的测试用例
 *
 * @author IBIT-TECH
 */
public class FormatterTest {

    private Formatter formatter;

    private Throwable throwable;

    private String errorMessage = "Test runtime!";

    @Before
    public void setUp() {
        formatter = Object::toString;
        errorMessage = "Test runtime!";
        throwable = new RuntimeException(errorMessage);
    }

    @Test
    public void format() {
        String message = "hello structlog4j";

        // message
        assertMap(new LinkedHashMap<String, Object>() {{
                         put(Formatter.KEY_MESSAGE, message);
                     }},
                formatter.format(message));

        // message+异常
        assertMap(new LinkedHashMap<String, Object>() {{
                         put(Formatter.KEY_MESSAGE, message);
                         put(Formatter.KEY_ERROR_MESSAGE, formatter.getErrorMessage(throwable));
                         put(Formatter.KEY_STACK_TRACE, formatter.getStackTrace(throwable));
                     }},
                formatter.format(message, throwable));


        Object[] messages = new Object[]{"hello {}", "structlog4j"};

        // message片段
        assertMap(new LinkedHashMap<String, Object>() {{
                         put(Formatter.KEY_MESSAGE, formatter.getMessage(messages));
                     }},
                formatter.format(messages));

        // message片段+异常
        assertMap(new LinkedHashMap<String, Object>() {{
                         put(Formatter.KEY_MESSAGE, formatter.getMessage(messages));
                         put(Formatter.KEY_ERROR_MESSAGE, formatter.getErrorMessage(throwable));
                         put(Formatter.KEY_STACK_TRACE, formatter.getStackTrace(throwable));
                     }},
                formatter.format(messages, throwable));
    }

    @Test
    public void getMessage() {
        assertEquals("", formatter.getMessage(null));
        assertEquals("", formatter.getMessage(new Object[0]));
        assertEquals("Hello structlog4j!"
                , formatter.getMessage(new Object[]{"Hello structlog4j!"}));
        assertEquals("Access server, ip: 127.0.0.1, port: 8080"
                , formatter.getMessage(new Object[]{"Access server, ip: {}, port: {}", "127.0.0.1", 8080}));
    }

    @Test
    public void getErrorMessage() {
        assertEquals(errorMessage, formatter.getErrorMessage(throwable));

        Throwable rootThrowable = new RuntimeException("Root throwable");
        Throwable t = new Throwable(errorMessage, rootThrowable);
        assertEquals("Root throwable", formatter.getErrorMessage(t));
    }

    @Test
    public void formatString() {
        // 没有特殊字符，保持不变
        assertEquals("Hello structlog4j", formatter.formatString("Hello structlog4j"));

        // 存在\r或\n或\t
        assertEquals("Hello\\tstructlog4j\\r\\n", formatter.formatString("Hello\tstructlog4j\r\n"));
    }

    @Test
    public void getStackTrace() {
        assertEquals(formatter.formatString(ExceptionUtils.getStackTrace(throwable))
                , formatter.getStackTrace(throwable));
    }

    /**
     * 断言正确
     *
     * @param expectedMap   预测map
     * @param actualMessage 实际message
     */
    private void assertMap(Map<String, Object> expectedMap, String actualMessage) {
        assertEquals(formatter.format(expectedMap), actualMessage);
    }
}