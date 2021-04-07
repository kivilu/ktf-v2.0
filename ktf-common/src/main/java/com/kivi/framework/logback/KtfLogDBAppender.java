package com.kivi.framework.logback;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import ch.qos.logback.classic.spi.CallerData;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.db.DBAppenderBase;

public class KtfLogDBAppender extends DBAppenderBase<ILoggingEvent> {
    protected static final Method GET_GENERATED_KEYS_METHOD;
    // 插入sql
    protected String insertSQL;

    private static final int APP_NAME_INDEX = 1;
    private static final int TIME_INDEX = 2;
    private static final int MESSAGE_INDEX = 3;
    private static final int LEVEL_STRING_INDEX = 4;
    private static final int LOGGER_NAME_INDEX = 5;
    private static final int THREAD_NAME_INDEX = 6;
    private static final int CALLER_FILENAME_INDEX = 7;
    private static final int CALLER_CLASS_INDEX = 8;
    private static final int CALLER_METHOD_INDEX = 9;
    private static final int CALLER_LINE_INDEX = 10;

    static final StackTraceElement EMPTY_CALLER_DATA = CallerData.naInstance();

    static {
        // PreparedStatement.getGeneratedKeys() method was added in JDK 1.4
        Method getGeneratedKeysMethod;
        try {
            // the
            getGeneratedKeysMethod = PreparedStatement.class.getMethod("getGeneratedKeys", (Class[])null);
        } catch (Exception ex) {
            getGeneratedKeysMethod = null;
        }
        GET_GENERATED_KEYS_METHOD = getGeneratedKeysMethod;
    }

    @Override
    public void start() {
        // 将写好的sql语句赋值给insertSQL
        insertSQL = buildInsertSQL();
        super.start();
    }

    // 自己写新增sql语句
    private static String buildInsertSQL() {
        StringBuilder sqlBuilder = new StringBuilder("INSERT INTO ktf_sys_log_biz ");
        sqlBuilder.append("(app_name, time, message, level_string, logger_name, thread_name,")
            .append("caller_filename, caller_class, caller_method, caller_line)")
            .append("VALUES (?, ?, ?, ? ,?, ?, ?, ?, ?, ?)");
        return sqlBuilder.toString();
    }

    private void bindLoggingEventWithInsertStatement(PreparedStatement stmt, ILoggingEvent event) throws SQLException {
        // event.getFormattedMessage() 日志打印内容
        String message = event.getFormattedMessage();
        String appName = this.getContext().getProperty("APP_NAME");
        stmt.setString(APP_NAME_INDEX, appName);
        stmt.setTimestamp(TIME_INDEX, new Timestamp(event.getTimeStamp()));
        stmt.setString(MESSAGE_INDEX, message.replaceFirst("!!", ""));
        stmt.setString(LEVEL_STRING_INDEX, event.getLevel().toString());
        stmt.setString(LOGGER_NAME_INDEX, event.getLoggerName());
        stmt.setString(THREAD_NAME_INDEX, event.getThreadName());

    }

    private void bindCallerDataWithPreparedStatement(PreparedStatement stmt, StackTraceElement[] callerDataArray)
        throws SQLException {
        StackTraceElement caller = extractFirstCaller(callerDataArray);
        stmt.setString(CALLER_FILENAME_INDEX, caller.getFileName());
        stmt.setString(CALLER_CLASS_INDEX, caller.getClassName());
        stmt.setString(CALLER_METHOD_INDEX, caller.getMethodName());
        stmt.setString(CALLER_LINE_INDEX, Integer.toString(caller.getLineNumber()));
    }

    @Override
    protected void subAppend(ILoggingEvent event, Connection connection, PreparedStatement insertStatement)
        throws Throwable {
        // 日志消息前两个母为 !! 的日志，记录到数据库表
        bindLoggingEventWithInsertStatement(insertStatement, event);

        // This is expensive... should we do it every time?
        bindCallerDataWithPreparedStatement(insertStatement, event.getCallerData());

        int updateCount = insertStatement.executeUpdate();
        if (updateCount != 1) {
            addWarn("Failed to insert loggingEvent");
        }
    }

    private StackTraceElement extractFirstCaller(StackTraceElement[] callerDataArray) {
        StackTraceElement caller = EMPTY_CALLER_DATA;
        if (hasAtLeastOneNonNullElement(callerDataArray))
            caller = callerDataArray[0];
        return caller;
    }

    private boolean hasAtLeastOneNonNullElement(StackTraceElement[] callerDataArray) {
        return callerDataArray != null && callerDataArray.length > 0 && callerDataArray[0] != null;
    }

    @Override
    protected Method getGeneratedKeysMethod() {
        return GET_GENERATED_KEYS_METHOD;
    }

    @Override
    protected String getInsertSQL() {
        return insertSQL;
    }

    @Override
    protected void secondarySubAppend(ILoggingEvent event, Connection connection, long eventId) throws Throwable {}

}
