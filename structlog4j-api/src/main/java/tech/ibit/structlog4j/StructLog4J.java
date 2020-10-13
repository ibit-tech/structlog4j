package tech.ibit.structlog4j;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Optional;
import java.util.Properties;

/**
 * 全局配置
 *
 * @author IBIT程序猿
 */
@UtilityClass
@Slf4j
public class StructLog4J {


    /**
     * 格式化对象
     */
    private Formatter formatter;

    /**
     * 是否转换异常的堆栈信息
     */
    private boolean transStackTrace;

    /**
     * 系统配置
     */
    private Optional<ToLog> globalConfig = Optional.empty();

    static {

        // 支持配置的方式替换formatter
        try (InputStream in = Thread.currentThread()
                .getContextClassLoader().getResourceAsStream(StructLog4jProperty.PROPERTY_FILE_NAME)) {
            if (null != in) {
                Properties properties = new Properties();
                properties.load(in);

                // 动态定义路径
                String formatterPath = properties.getProperty(StructLog4jProperty.KEY_FORMATTER);

                if (formatterPath != null) {
                    String[] paths = formatterPath.split(StructLog4jProperty.FORMATTER_METHOD_SEPARATOR);
                    Method method = Class.forName(paths[0]).getDeclaredMethod(paths[1]);
                    formatter = (Formatter) method.invoke(null);
                } else {
                    formatter = KvPairFormatter.getInstance();
                }
                transStackTrace = Boolean.valueOf(properties.getProperty(StructLog4jProperty.KEY_TRANS_STACK_TRACE, "true"));

            } else {
                formatter = KvPairFormatter.getInstance();
                transStackTrace = true;
            }
        } catch (Exception e) {

            // 正确配置的话，这个错误不应该出现
            log.error("Error to load {}", StructLog4jProperty.PROPERTY_FILE_NAME, e);

            // 配置文件不存在的时候，选择默认
            formatter = KvPairFormatter.getInstance();
            transStackTrace = true;
        }
    }

    /**
     * 设置日志格式化对象
     *
     * @param formatter 日志格式化对象
     */
    public void setFormatter(Formatter formatter) {
        StructLog4J.formatter = formatter;
    }


    /**
     * 全局日志设置，字段全都会被打印
     *
     * @param globalConfig 全局日志设置
     */
    public void setGlobalConfig(ToLog globalConfig) {
        StructLog4J.globalConfig = Optional.of(globalConfig);
    }

    /**
     * 设置是否转化异常日志
     *
     * @param transStackTrace 是否转化
     */
    public void setTransStackTrace(boolean transStackTrace) {
        StructLog4J.transStackTrace = transStackTrace;
    }

    /**
     * 获取日志格式化对象
     *
     * @return 格式化对象
     */
    public Formatter getFormatter() {
        return formatter;
    }

    /**
     * 获取全局设置
     *
     * @return 全局设置
     */
    public Optional<ToLog> getGlobalConfig() {
        return globalConfig;
    }

    /**
     * 是否转化异常日志
     *
     * @return 是否转化异常日志
     */
    public boolean isTransStackTrace() {
        return transStackTrace;
    }

    /**
     * 重置全局设置
     */
    public void resetGlobalConfig() {
        globalConfig = Optional.empty();
    }


}