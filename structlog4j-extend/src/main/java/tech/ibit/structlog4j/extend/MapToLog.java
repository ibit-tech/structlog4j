package tech.ibit.structlog4j.extend;

import org.apache.commons.beanutils.BeanMap;
import tech.ibit.structlog4j.ToLog;

import java.util.ArrayList;
import java.util.List;

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

        // 查询字段并排序
        int keySize = kvMap.size() - 1;
        List<String> keys = new ArrayList<>(keySize);
        for (Object key : kvMap.keySet()) {
            if (!"class".equals(key)) {
                keys.add(((String) key));
            }
        }
        keys.sort(String::compareTo);

        Object[] objects = new Object[keys.size() * 2];
        int index = 0;
        for (String key : keys) {
            Object value = kvMap.get(key);
            objects[index++] = key;
            objects[index++] = value;

        }
        return objects;
    }
}
