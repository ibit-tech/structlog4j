package org.slf4j.impl;

import org.slf4j.ILoggerFactory;

/**
 * 日志绑定器
 *
 * @author IBIT程序猿
 */
public class StaticLoggerBinder {

    private static final StaticLoggerBinder SINGLETON = new StaticLoggerBinder();

    public static final StaticLoggerBinder getSingleton() {
        return SINGLETON;
    }

    public static String REQUESTED_API_VERSION = "1.7";

    public ILoggerFactory getLoggerFactory() {
        return new TestLoggerFactory();
    }

    public String getLoggerFactoryClassStr() {
        return TestLoggerFactory.class.getName();
    }
}
