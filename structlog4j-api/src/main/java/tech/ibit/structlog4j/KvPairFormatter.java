package tech.ibit.structlog4j;

import java.util.Map;

/**
 * 键-值对日志格式化类
 *
 * @author IBIT-TECH
 */
public class KvPairFormatter implements Formatter {

    /**
     * 等号
     */
    private static final String EQUAL = "=";

    /**
     * 空字符串
     */
    private static final String EMPTY_STRING = "";

    /**
     * "&" 分隔符
     */
    private static final String STR_AND = "&";

    /**
     * 用"'%26'"替换"&"符号
     */
    private static final String REPLACE_AND = "'%26'";

    /**
     * 做成单例
     */
    private static final KvPairFormatter INSTANCE = new KvPairFormatter();

    /**
     * 获取实例
     *
     * @return 实例
     */
    public static KvPairFormatter getInstance() {
        return INSTANCE;
    }

    @Override
    public String format(Map<String, ?> kvMap) {
        if (null == kvMap || kvMap.isEmpty()) {
            return EMPTY_STRING;
        }
        StringBuilder sb = new StringBuilder();
        kvMap.forEach((key, val) -> {
            String value = formatString(String.valueOf(val)).replace(STR_AND, REPLACE_AND);
            sb.append(STR_AND).append(key).append(EQUAL).append(value);
        });
        return sb.substring(STR_AND.length());
    }

}
