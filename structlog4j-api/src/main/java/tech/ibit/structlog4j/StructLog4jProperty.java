package tech.ibit.structlog4j;

/**
 * 日志配置文件
 *
 * @author IBIT程序猿
 */
public interface StructLog4jProperty {

    /**
     * 文件名称
     */
    String PROPERTY_FILE_NAME = "structlog4j.properties";

    /**
     * 字段: formatter(格式化类）
     */
    String KEY_FORMATTER = "formatter";


    /**
     * formatter方法分隔符
     */
    String FORMATTER_METHOD_SEPARATOR = "#";


    /**
     * 字段：transStackTrace(是否转换异常日志)
     */
    String KEY_TRANS_STACK_TRACE = "transStackTrace";


}
