package tech.ibit.structlog4j.test;

import lombok.experimental.UtilityClass;
import org.slf4j.event.Level;
import org.slf4j.impl.LogEntry;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * 测试工具类
 *
 * @author IBIT-TECH
 */
@UtilityClass
public class TestUtils {

    /**
     * 消息断言
     *
     * @param entries                  异常实体
     * @param entryIndex               异常索引
     * @param expectedLevel            期望日志级别
     * @param expectedMessage          期望消息
     * @param expectedThrowablePresent 期望异常是否出现
     */
    public void assertMessage(List<LogEntry> entries, int entryIndex
            , Level expectedLevel, String expectedMessage, boolean expectedThrowablePresent) {
        assertEquals(entries.toString(), expectedLevel, entries.get(entryIndex).getLevel());
        assertEquals(entries.toString(), expectedMessage, entries.get(entryIndex).getMessage());
        assertTrue(entries.toString(), entries.get(entryIndex).getThrowable().isPresent() == expectedThrowablePresent);
    }

}
