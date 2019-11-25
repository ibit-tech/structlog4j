package tech.ibit.structlog4j;

import lombok.experimental.UtilityClass;

/**
 * 结构化日志器工厂
 *
 * @author IBIT-TECH
 */
@UtilityClass
public class StructLoggerFactory {

    /**
     * 获取日志器
     *
     * @param name 名称
     * @return 日志器
     */
    public Logger getLogger(String name) {
        return new StructLogger(name);
    }

    /**
     * 获取日志器
     *
     * @param clazz 类
     * @return 日志器
     */
    public Logger getLogger(Class<?> clazz) {
        return new StructLogger(clazz);
    }
}
