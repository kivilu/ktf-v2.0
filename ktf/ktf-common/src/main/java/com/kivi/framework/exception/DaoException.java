package com.kivi.framework.exception;

/**
 * @author Eric
 *
 */
public class DaoException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    // 友好提示的code码
    private Integer           code;

    private String            errorCode;

    public DaoException() {
        super();
    }

    public DaoException( String message ) {
        this(null, message, null);
    }

    public DaoException( String errorCode, String message ) {
        this(errorCode, message, null);
    }

    public DaoException( int errorCode, String message ) {
        this(errorCode, message, null);
    }

    public DaoException( Throwable cause ) {
        this(null, null, cause);
    }

    public DaoException( int errorCode, Throwable cause ) {
        this(errorCode, null, cause);
    }

    public DaoException( String message, Throwable cause ) {
        this(null, message, cause);
    }

    public DaoException( String errorCode, String message, Throwable cause ) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public DaoException( int errorCode, String message, Throwable cause ) {
        super(message, cause);
        this.code = errorCode;
    }

    public String getErrorCode() {
        return errorCode == null ? this.getMessage() : errorCode;
    }

    public void setErrorCode( String errorCode ) {
        this.errorCode = errorCode;
    }

    public Integer getCode() {
        return code;
    }

}
