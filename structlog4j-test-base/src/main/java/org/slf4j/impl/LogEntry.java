package org.slf4j.impl;

import lombok.Value;
import org.slf4j.event.Level;

import java.util.Optional;

/**
 * 日志对象
 *
 * @author IBIT-TECH
 */
@Value
public class LogEntry {

    /**
     * 日志级别
     */
    private Level level;

    /**
     * 日志信息
     */
    private String message;


    /**
     * 异常信息
     */
    private Optional<Throwable> throwable;

}
