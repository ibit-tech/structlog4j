package tech.ibit.structlog4j.extend;

import org.apache.commons.beanutils.BeanMap;
import tech.ibit.structlog4j.ToLog;

/**
 * MapToLog实现
 *
 * @author IBIT-TECH
 */
public interface MapToLog extends ToLog {

    /**
     * 日志的键-值对数组，Object[] {"key1", value1, "key2", value2}
     *
     * @return 键-值对数组
     */
    @Override
    default Object[] toLog() {
        BeanMap kvMap = new BeanMap(this);
        Object[] objects = new Object[(kvMap.size() - 1) * 2];
        int index = 0;
        for (Object key : kvMap.keySet()) {
            if (!"class".equals(key)) {
                Object value = kvMap.get(key);
                objects[index++] = key;
                objects[index++] = value;
            }
        }
        return objects;
    }
}
