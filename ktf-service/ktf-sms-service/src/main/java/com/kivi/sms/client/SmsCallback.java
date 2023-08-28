package com.kivi.sms.client;

/**
 * 短信通知回调
 *
 * @param <T>
 *            the return type from the callback
 */
@FunctionalInterface
public interface SmsCallback<T> {

    /**
     * 当收到短信通知时回调
     * 
     * @param notify
     */
    void onNotify( T notify );

    /**
     * Called whenever an error occurs.
     *
     * @param cause
     *            the cause of the failure
     */
    default void onFailure( Throwable cause ) {
    }
}
