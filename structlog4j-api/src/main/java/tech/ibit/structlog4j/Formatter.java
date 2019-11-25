package tech.ibit.structlog4j;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 日志格式化类
 *
 * @author IBIT-TECH
 */
public interface Formatter {

    /**
     * message字段（系统字段）
     */
    String KEY_MESSAGE = "_message";

    /**
     * message字段（系统字段）
     */
    String KEY_ERROR_MESSAGE = "_errorMessage";

    /**
     * stackTrace字段（系统字段）
     */
    String KEY_STACK_TRACE = "_stackTrace";

    /**
     * '\t'符号
     */
    String STR_TAB = "\t";

    /**
     * '\r'符号
     */
    String STR_R = "\r";

    /**
     * '\n'符号
     */
    String STR_WRAP = "\n";
    /**
     * '\t'替换符号
     */
    String REPLACE_TAB = "\\t";

    /**
     * '\r'替换符号
     */
    String REPLACE_R = "\\r";

    /**
     * '\n'替换符号
     */
    String REPLACE_WRAP = "\\n";

    /**
     * 空格
     */
    String SPACE = " ";

    /**
     * 格式化（待实现）
     *
     * @param kvMap 兼职map
     * @return 格式化后的文本
     */
    String format(Map<String, ?> kvMap);


    /**
     * 格式化
     *
     * @param message message
     * @return 格式化后的文本
     */
    default String format(String message) {
        return format(message, null);
    }

    /**
     * 格式化
     *
     * @param message message
     * @param t       异常
     * @return 格式化后的文本
     */
    default String format(String message, Throwable t) {
        Map<String, String> kvMap = new LinkedHashMap<>(3);
        kvMap.put(KEY_MESSAGE, message);
        if (null != t) {
            kvMap.put(KEY_ERROR_MESSAGE, getErrorMessage(t));
            kvMap.put(KEY_STACK_TRACE, getStackTrace(t));
        }
        return format(kvMap);
    }

    /**
     * 格式化
     *
     * @param messages message片段，需要格式化String.format(messages[0], message[1]..)
     * @return 格式化后的文本
     */
    default String format(Object[] messages) {
        return format(getMessage(messages));
    }


    /**
     * 格式化
     *
     * @param messages message片段，需要格式化String.format(messages[0], message[1]..)
     * @param t        异常
     * @return 格式化后的文本
     */
    default String format(Object[] messages, Throwable t) {
        return format(getMessage(messages), t);
    }

    /**
     * 根据message片段构造message
     *
     * @param messages message片段
     * @return message
     */
    default String getMessage(Object[] messages) {
        // 空数据片段
        if (null == messages || 0 == messages.length) {
            return "";
        }

        // 只有一个
        if (1 == messages.length) {
            return String.valueOf(messages[0]);
        }

        return String.format(
                String.valueOf(messages[0]).replace("{}", "%s"),
                Arrays.copyOfRange(messages, 1, messages.length)
        );

    }

    /**
     * 格式化字符字符串
     * "\t" 替换为 "\\t",  "\r"替换为"\\r", "\n"替换为"\\n"
     *
     * @param originalStr 原始字符串
     * @return 格式化后的字符串
     */
    default String formatString(String originalStr) {
        if (null == originalStr) {
            return null;

        }
        return originalStr
                .replace(STR_TAB, REPLACE_TAB)
                .replace(STR_R, REPLACE_R)
                .replace(STR_WRAP, REPLACE_WRAP);

    }

    /**
     * 获取异常信息
     *
     * @param t 异常
     * @return 异常信息
     */
    default String getErrorMessage(Throwable t) {
        if (t.getCause() == null) {
            return t.getMessage();
        } else {
            return getErrorMessage(t.getCause());
        }
    }

    /**
     * 获取异常堆栈信息
     *
     * @param throwable 异常
     * @return 堆栈信息
     */
    default String getStackTrace(Throwable throwable) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw, true);
        throwable.printStackTrace(pw);
        return formatString(sw.getBuffer().toString());
    }

}
