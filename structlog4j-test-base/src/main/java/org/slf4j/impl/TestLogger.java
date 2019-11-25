package org.slf4j.impl;

import lombok.Value;
import org.slf4j.Logger;
import org.slf4j.Marker;
import org.slf4j.event.Level;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * 测试Logger实现
 *
 * @author IBIT-TECH
 */
@Value
public class TestLogger implements Logger {

    /**
     * 日志名称
     */
    private String name;

    private List<LogEntry> entries = new LinkedList<>();

    @Override
    public boolean isTraceEnabled() {
        return true;
    }

    @Override
    public void trace(String msg) {
        entries.add(new LogEntry(Level.TRACE, msg, Optional.empty()));
    }

    @Override
    public void trace(String format, Object arg) {
        throw new RuntimeException("Not supported");
    }

    @Override
    public void trace(String format, Object arg1, Object arg2) {
        throw new RuntimeException("Not supported");
    }

    @Override
    public void trace(String format, Object... arguments) {
        throw new RuntimeException("Not supported");
    }

    @Override
    public void trace(String msg, Throwable t) {
        entries.add(new LogEntry(Level.WARN, msg, Optional.empty()));
    }

    @Override
    public boolean isTraceEnabled(Marker marker) {
        return false;
    }

    @Override
    public void trace(Marker marker, String msg) {
        throw new RuntimeException("Not supported");
    }

    @Override
    public void trace(Marker marker, String format, Object arg) {
        throw new RuntimeException("Not supported");
    }

    @Override
    public void trace(Marker marker, String format, Object arg1, Object arg2) {
        throw new RuntimeException("Not supported");
    }

    @Override
    public void trace(Marker marker, String format, Object... argArray) {
        throw new RuntimeException("Not supported");
    }

    @Override
    public void trace(Marker marker, String msg, Throwable t) {
        throw new RuntimeException("Not supported");
    }

    @Override
    public boolean isDebugEnabled() {
        return true;
    }

    @Override
    public void debug(String msg) {
        entries.add(new LogEntry(Level.DEBUG, msg, Optional.empty()));
    }

    @Override
    public void debug(String format, Object arg) {
        throw new RuntimeException("Not supported");
    }

    @Override
    public void debug(String format, Object arg1, Object arg2) {
        throw new RuntimeException("Not supported");
    }

    @Override
    public void debug(String format, Object... arguments) {
        throw new RuntimeException("Not supported");
    }

    @Override
    public void debug(String msg, Throwable t) {
        entries.add(new LogEntry(Level.TRACE, msg, Optional.of(t)));
    }

    @Override
    public boolean isDebugEnabled(Marker marker) {
        return false;
    }

    @Override
    public void debug(Marker marker, String msg) {
        throw new RuntimeException("Not supported");
    }

    @Override
    public void debug(Marker marker, String format, Object arg) {
        throw new RuntimeException("Not supported");
    }

    @Override
    public void debug(Marker marker, String format, Object arg1, Object arg2) {
        throw new RuntimeException("Not supported");
    }

    @Override
    public void debug(Marker marker, String format, Object... arguments) {
        throw new RuntimeException("Not supported");
    }

    @Override
    public void debug(Marker marker, String msg, Throwable t) {
        throw new RuntimeException("Not supported");
    }

    @Override
    public boolean isInfoEnabled() {
        return true;
    }

    @Override
    public void info(String msg) {
        entries.add(new LogEntry(Level.INFO, msg, Optional.empty()));
    }

    @Override
    public void info(String format, Object arg) {
        throw new RuntimeException("Not supported");
    }

    @Override
    public void info(String format, Object arg1, Object arg2) {
        throw new RuntimeException("Not supported");
    }

    @Override
    public void info(String format, Object... arguments) {
        throw new RuntimeException("Not supported");
    }

    @Override
    public void info(String msg, Throwable t) {
        entries.add(new LogEntry(Level.INFO, msg, Optional.of(t)));
    }

    @Override
    public boolean isInfoEnabled(Marker marker) {
        return false;
    }

    @Override
    public void info(Marker marker, String msg) {
        throw new RuntimeException("Not supported");
    }

    @Override
    public void info(Marker marker, String format, Object arg) {
        throw new RuntimeException("Not supported");
    }

    @Override
    public void info(Marker marker, String format, Object arg1, Object arg2) {
        throw new RuntimeException("Not supported");
    }

    @Override
    public void info(Marker marker, String format, Object... arguments) {
        throw new RuntimeException("Not supported");
    }

    @Override
    public void info(Marker marker, String msg, Throwable t) {
        throw new RuntimeException("Not supported");
    }

    @Override
    public boolean isWarnEnabled() {
        return true;
    }

    @Override
    public void warn(String msg) {
        entries.add(new LogEntry(Level.WARN, msg, Optional.empty()));
    }

    @Override
    public void warn(String format, Object arg) {
        throw new RuntimeException("Not supported");
    }

    @Override
    public void warn(String format, Object... arguments) {
        throw new RuntimeException("Not supported");
    }

    @Override
    public void warn(String format, Object arg1, Object arg2) {
        throw new RuntimeException("Not supported");
    }

    @Override
    public void warn(String msg, Throwable t) {
        entries.add(new LogEntry(Level.WARN, msg, Optional.of(t)));
    }

    @Override
    public boolean isWarnEnabled(Marker marker) {
        return false;
    }

    @Override
    public void warn(Marker marker, String msg) {
        throw new RuntimeException("Not supported");
    }

    @Override
    public void warn(Marker marker, String format, Object arg) {
        throw new RuntimeException("Not supported");
    }

    @Override
    public void warn(Marker marker, String format, Object arg1, Object arg2) {
        throw new RuntimeException("Not supported");
    }

    @Override
    public void warn(Marker marker, String format, Object... arguments) {
        throw new RuntimeException("Not supported");
    }

    @Override
    public void warn(Marker marker, String msg, Throwable t) {
        throw new RuntimeException("Not supported");
    }

    @Override
    public boolean isErrorEnabled() {
        return true;
    }

    @Override
    public void error(String msg) {
        entries.add(new LogEntry(Level.ERROR, msg, Optional.empty()));
    }

    @Override
    public void error(String format, Object arg) {
        throw new RuntimeException("Not supported");
    }

    @Override
    public void error(String format, Object arg1, Object arg2) {
        throw new RuntimeException("Not supported");
    }

    @Override
    public void error(String format, Object... arguments) {
        throw new RuntimeException("Not supported");
    }

    @Override
    public void error(String msg, Throwable t) {
        entries.add(new LogEntry(Level.ERROR, msg, Optional.of(t)));
    }

    @Override
    public boolean isErrorEnabled(Marker marker) {
        return false;
    }

    @Override
    public void error(Marker marker, String msg) {
        throw new RuntimeException("Not supported");
    }

    @Override
    public void error(Marker marker, String format, Object arg) {
        throw new RuntimeException("Not supported");
    }

    @Override
    public void error(Marker marker, String format, Object arg1, Object arg2) {
        throw new RuntimeException("Not supported");
    }

    @Override
    public void error(Marker marker, String format, Object... arguments) {
        throw new RuntimeException("Not supported");
    }

    @Override
    public void error(Marker marker, String msg, Throwable t) {
        throw new RuntimeException("Not supported");
    }
}
