package tech.ibit.structlog4j.extend;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import tech.ibit.structlog4j.Formatter;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * JSON格式化类测试用例
 *
 * @author IBIT-TECH
 */
public class JsonFormatterTest {

    private Formatter formatter;

    @Before
    public void setUp() {
        formatter = JsonFormatter.getInstance();
    }

    @Test
    public void format() {

        Assert.assertEquals("{}", formatter.format(Collections.emptyMap()));

        // 普通用例
        Map<String, Object> kvMap = new LinkedHashMap<String, Object>() {{
            put("hostname", "localhost");
            put("ip", "127.0.0.1");
        }};
        Assert.assertEquals("{\"hostname\":\"localhost\",\"ip\":\"127.0.0.1\"}", formatter.format(kvMap));

        // 消息中带空格
        kvMap = new LinkedHashMap<String, Object>() {{
            put("_message", "hello structlog4j");
            put("text", "hahaha");
        }};
        Assert.assertEquals("{\"_message\":\"hello structlog4j\",\"text\":\"hahaha\"}", formatter.format(kvMap));

        // 消息中带有特殊字符
        kvMap = new LinkedHashMap<String, Object>() {{
            put("_message", "hello\t&structlog4j\r\n");
            put("text", "hahaha");
        }};
        Assert.assertEquals("{\"_message\":\"hello\\\\t&structlog4j\\\\r\\\\n\",\"text\":\"hahaha\"}", formatter.format(kvMap));
    }
}