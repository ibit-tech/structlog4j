package tech.ibit.structlog4j;

import org.slf4j.LoggerFactory;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 结构化日志器
 *
 * @author IBIT程序猿
 */
public class StructLogger implements Logger {

    private static final int K_V_SIZE = 2;


    /**
     * slf4j日志器（可用于测试）
     */
    final org.slf4j.Logger slf4jLogger;


    StructLogger(String name) {
        slf4jLogger = LoggerFactory.getLogger(name);
    }

    StructLogger(Class<?> clazz) {
        slf4jLogger = LoggerFactory.getLogger(clazz);
    }

    @Override
    public void error(String message, Object... params) {
        log(isErrorEnabled(), slf4jLogger::error, slf4jLogger::error, message, params);
    }

    @Override
    public void warn(String message, Object... params) {
        log(isWarnEnabled(), slf4jLogger::warn, slf4jLogger::warn, message, params);
    }

    @Override
    public void info(String message, Object... params) {
        log(isInfoEnabled(), slf4jLogger::info, slf4jLogger::info, message, params);
    }

    @Override
    public void debug(String message, Object... params) {
        log(isDebugEnabled(), slf4jLogger::debug, slf4jLogger::debug, message, params);
    }

    @Override
    public void trace(String message, Object... params) {
        log(isTraceEnabled(), slf4jLogger::trace, slf4jLogger::trace, message, params);
    }

    @Override
    public void error(Object[] messages, Object... params) {
        log(isErrorEnabled(), slf4jLogger::error, slf4jLogger::error, messages, params);
    }

    @Override
    public void warn(Object[] messages, Object... params) {
        log(isWarnEnabled(), slf4jLogger::warn, slf4jLogger::warn, messages, params);
    }

    @Override
    public void info(Object[] messages, Object... params) {
        log(isInfoEnabled(), slf4jLogger::info, slf4jLogger::info, messages, params);
    }

    @Override
    public void debug(Object[] messages, Object... params) {
        log(isDebugEnabled(), slf4jLogger::debug, slf4jLogger::debug, messages, params);
    }

    @Override
    public void trace(Object[] messages, Object... params) {
        log(isTraceEnabled(), slf4jLogger::trace, slf4jLogger::trace, messages, params);
    }

    @Override
    public boolean isErrorEnabled() {
        return slf4jLogger.isErrorEnabled();
    }

    @Override
    public boolean isWarnEnabled() {
        return slf4jLogger.isWarnEnabled();
    }

    @Override
    public boolean isInfoEnabled() {
        return slf4jLogger.isInfoEnabled();
    }

    @Override
    public boolean isDebugEnabled() {
        return slf4jLogger.isDebugEnabled();
    }

    @Override
    public boolean isTraceEnabled() {
        return slf4jLogger.isTraceEnabled();
    }


    /**
     * 写入日志
     *
     * @param needLog              判断是否需要打印日志
     * @param logConsumer          日志消费者
     * @param exceptionLogConsumer 异常日志消费者
     * @param messages             消息片段
     * @param params               参数
     */
    private void log(boolean needLog, LogConsumer logConsumer
            , ExceptionLogConsumer exceptionLogConsumer, Object[] messages, Object... params) {
        if (!needLog) {
            return;
        }

        Formatter formatter = StructLog4J.getFormatter();
        String message = formatter.getMessage(messages);
        doLog(formatter, logConsumer, exceptionLogConsumer, message, params);
    }

    /**
     * 写入日志
     *
     * @param needLog              判断是否需要打印日志
     * @param logConsumer          日志消费者
     * @param exceptionLogConsumer 异常日志消费者
     * @param message              消息
     * @param params               参数
     */
    private void log(boolean needLog, LogConsumer logConsumer
            , ExceptionLogConsumer exceptionLogConsumer, String message, Object... params) {
        if (!needLog) {
            return;
        }

        // 将消息改为空串
        if (null == message) {
            message = "";
        }

        Formatter formatter = StructLog4J.getFormatter();
        doLog(formatter, logConsumer, exceptionLogConsumer, message, params);
    }


    /**
     * 写入日志
     *
     * @param formatter            日志格式对象
     * @param logConsumer          日志消费者
     * @param exceptionLogConsumer 异常日志消费者
     * @param message              消息
     * @param params               参数
     */
    private void doLog(Formatter formatter, LogConsumer logConsumer, ExceptionLogConsumer exceptionLogConsumer
            , String message, Object[] params) {
        boolean transStackTrace = StructLog4J.isTransStackTrace();

        // 异常
        Throwable throwable = null;

        try {
            Map<String, Object> kvMap = new LinkedHashMap<>(10);
            kvMap.put(Formatter.KEY_MESSAGE, message);

            // 判断键值对是否正常解析
            boolean processKeyValues = true;

            for (int i = 0; i < params.length; i++) {

                Object param = params[i];

                if (param instanceof ToLog) {

                    // 处理ToLog对象
                    handleToLog(formatter, (ToLog) param, kvMap);

                } else if (param instanceof Throwable) {

                    // 处理异常对象
                    handleThrowable(formatter, transStackTrace, (Throwable) param, kvMap);

                    // 不需要堆栈信息转换
                    if (!transStackTrace) {
                        throwable = (Throwable) param;
                    }

                } else {

                    if (processKeyValues) {
                        // 读取下一个值
                        i++;
                        if (i < params.length) {
                            if (!handleKeyValue(formatter, param, params[i], null, kvMap)) {
                                // 如果存在异常，则下面的字段不在解析
                                processKeyValues = false;
                            }
                        }
                    }
                }
            }

            // 增加公共字段
            Optional<ToLog> mandatory = StructLog4J.getGlobalConfig();
            mandatory.ifPresent(iToLog -> handleToLog(formatter, iToLog, kvMap));

            // 打印日志
            if (null != throwable) {
                exceptionLogConsumer.consume(formatter.format(kvMap), throwable);
            } else {
                logConsumer.consume(formatter.format(kvMap));
            }
        } catch (Exception ex) {

            // 捕获日志格式化错误
            logError(formatter, transStackTrace, "Unexpected logger error", ex);

        }
    }


    /**
     * 处理ToLog日志类
     *
     * @param toLog 日志对象
     * @param kvMap 键-值map
     */
    private void handleToLog(Formatter formatter, ToLog toLog, Map<String, Object> kvMap) {
        Object[] logParams = toLog.toLog();
        Class toLogClazz = toLog.getClass();

        if (logParams == null || logParams.length == 0) {
            logError(formatter,
                    new Object[]{
                            "Null or empty returned from {}.toLog()",
                            toLog.getClass()
                    });

            return;
        } else if (logParams.length % K_V_SIZE != 0) {
            // 不是偶数
            logError(formatter,
                    new Object[]{
                            "Odd number of parameters ({}) returned from {}.toLog()",
                            logParams.length,
                            toLog.getClass()
                    });
            return;
        }

        // 处理键-值对
        for (int i = 0; i < logParams.length; i = i + K_V_SIZE) {
            handleKeyValue(formatter, logParams[i], logParams[i + 1], toLogClazz, kvMap);
        }
    }

    /**
     * 处理键值对
     *
     * @param formatter  格式化对象
     * @param keyObject  键对象
     * @param value      值对象
     * @param toLogClazz 源类型
     * @param kvMap      键值map
     * @return 处理结果
     */
    private boolean handleKeyValue(Formatter formatter, Object keyObject, Object value, Class<?> toLogClazz, Map<String, Object> kvMap) {

        //key必须是字符串
        if (keyObject instanceof String) {

            String key = (String) keyObject;
            if (!key.contains(Formatter.SPACE)) {
                kvMap.put(key, value);
            } else {
                logError(formatter,
                        null == toLogClazz
                                ? new Object[]{"Key with spaces was passed in: '{}'", key}
                                : new Object[]{"Key with spaces was passed in from {}.toLog(): '{}'", toLogClazz, key});
                return false;
            }
        } else {
            logError(formatter,
                    null == toLogClazz
                            ? new Object[]{
                            "Non-String or null key was passed in: {} ({})", keyObject, keyObject != null ? keyObject.getClass() : "null"}
                            : new Object[]{
                            "Non-String or null key was passed in from {}.toLog(): {} ({})", toLogClazz, keyObject, keyObject != null ? keyObject.getClass() : "null"});
            return false;
        }
        return true;
    }

    /**
     * 处理异常
     *
     * @param formatter       格式化对象
     * @param transStackTrace 是否转化异常堆栈信息
     * @param t               异常
     * @param kvMap           键-值map
     */
    private void handleThrowable(Formatter formatter, boolean transStackTrace
            , Throwable t, Map<String, Object> kvMap) {
        kvMap.put(Formatter.KEY_ERROR_MESSAGE, formatter.getErrorMessage(t));
        if (transStackTrace) {
            kvMap.put(Formatter.KEY_STACK_TRACE, formatter.getStackTrace(t));
        }
    }

    /**
     * 日志消费者
     */
    @FunctionalInterface
    interface LogConsumer {

        /**
         * 写入日志
         *
         * @param message 日志
         */
        void consume(String message);
    }

    /**
     * 异常日志消费者
     */
    @FunctionalInterface
    interface ExceptionLogConsumer {

        /**
         * 写入日志
         *
         * @param message 日志
         * @param t       异常信息
         */
        void consume(String message, Throwable t);
    }


    /**
     * 打印错误日志
     *
     * @param formatter       格式化对象
     * @param transStackTrace 是否转换异常堆栈信息
     * @param message         message
     * @param t               异常
     */
    private void logError(Formatter formatter, boolean transStackTrace, String message, Throwable t) {
        if (transStackTrace) {
            slf4jLogger.error(formatter.format(message, t));
        } else {
            slf4jLogger.error(formatter.format(message), t);
        }
    }

    /**
     * 打印错误日志
     *
     * @param formatter 格式化对象
     * @param messages  message片段
     */
    private void logError(Formatter formatter, Object[] messages) {
        slf4jLogger.error(formatter.format(messages));
    }

}
