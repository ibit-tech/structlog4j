package org.slf4j.impl;

import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;

/**
 * 日志工厂实现
 *
 * @author IBIT-TECH
 */
public class TestLoggerFactory implements ILoggerFactory {

    @Override
    public Logger getLogger(String name) {
        return new TestLogger(name);
    }

}
