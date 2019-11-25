package tech.ibit.structlog4j;

/**
 * 自定义日志对象
 *
 * @author IBIT-TECH
 */
@FunctionalInterface
public interface ToLog {

    /**
     * 日志的键-值对数组，Object[] {"key1", value1, "key2", value2}
     *
     * @return 键-值对数组
     */
    Object[] toLog();
}
