package com.kivi.framework.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * @Description KTF异常的封装
 */
@Setter
@Getter
public class KtfException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    // 友好提示的code码
    private Integer           code;

    // 友好提示
    private String            tips;

    public KtfException() {
        super();
    }

    public KtfException( int code, String tips ) {
        super(tips);
        this.tips = tips;
        this.code = code;
    }

    public KtfException( int code, Throwable cause ) {
        super(cause);
        this.code = code;
    }

    public KtfException( Integer code, String tips, Throwable cause ) {
        super(tips, cause);
        this.code = code;
        this.tips = tips;
    }

    public KtfException( Integer code, String tips, String message, Throwable cause ) {
        super(message, cause);
        this.code = code;
        this.tips = tips;
    }

    public KtfException( String tips, Throwable cause ) {
        super(tips, cause);
        this.tips = tips;
    }

    public KtfException( String tips ) {
        super(tips);
        this.tips = tips;
    }

    public KtfException( Throwable cause ) {
        super(cause);
    }

}
