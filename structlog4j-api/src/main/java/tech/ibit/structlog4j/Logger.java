package tech.ibit.structlog4j;

/**
 * 定义日志对象
 *
 * @author IBIT-TECH
 */
public interface Logger {

    /**
     * ERROR日志
     *
     * @param message 消息
     * @param params  参数
     */
    void error(String message, Object... params);

    /**
     * WARN日志
     *
     * @param message 消息
     * @param params  参数
     */
    void warn(String message, Object... params);

    /**
     * INFO日志
     *
     * @param message 消息
     * @param params  参数
     */
    void info(String message, Object... params);

    /**
     * DEBUG日志
     *
     * @param message 消息
     * @param params  参数
     */
    void debug(String message, Object... params);


    /**
     * TRACE日志
     *
     * @param message 消息
     * @param params  参数
     */
    void trace(String message, Object... params);


    /**
     * ERROR日志
     *
     * @param messages 消息片段
     * @param params   参数
     */
    void error(String[] messages, Object... params);

    /**
     * WARN日志
     *
     * @param messages 消息片段
     * @param params   参数
     */
    void warn(String[] messages, Object... params);

    /**
     * INFO日志
     *
     * @param messages 消息片段
     * @param params   参数
     */
    void info(String[] messages, Object... params);

    /**
     * DEBUG日志
     *
     * @param messages 消息片段
     * @param params   参数
     */
    void debug(String[] messages, Object... params);


    /**
     * TRACE日志
     *
     * @param messages 消息片段
     * @param params   参数
     */
    void trace(String[] messages, Object... params);


    /**
     * 是否为ERROR级别
     *
     * @return 是否为ERROR级别
     */
    boolean isErrorEnabled();

    /**
     * 是否为WARN级别
     *
     * @return 是否为WARN级别
     */
    boolean isWarnEnabled();

    /**
     * 是否为INFO级别
     *
     * @return 是否为INFO级别
     */
    boolean isInfoEnabled();

    /**
     * 是否为DEBUG级别
     *
     * @return 是否为DEBUG级别
     */
    boolean isDebugEnabled();

    /**
     * 是否为TRACE级别
     *
     * @return 是否为TRACE级别
     */
    boolean isTraceEnabled();


}
