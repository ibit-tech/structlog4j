package tech.ibit.structlog4j.extend.formater;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import tech.ibit.structlog4j.Formatter;

import java.util.Map;

/**
 * Json对日志格式化类
 *
 * @author IBIT-TECH
 */
public class JsonFormatter implements Formatter {


    /**
     * 做成单例
     */
    private static final JsonFormatter INSTANCE = new JsonFormatter();

    /**
     * 获取实例
     *
     * @return 实例
     */
    public static JsonFormatter getInstance() {
        return INSTANCE;
    }

    @Override
    public String format(Map<String, ?> kvMap) {
        if (null == kvMap || kvMap.isEmpty()) {
            return JSON.toJSONString(kvMap);
        }

        // 需要对string类型进行格式化
        JSONObject json = new JSONObject();
        kvMap.forEach((k, v) -> {
            if (v instanceof String) {
                json.put(k, formatString((String) v));
            } else {
                json.put(k, v);
            }
        });
        return json.toJSONString();
    }
}
