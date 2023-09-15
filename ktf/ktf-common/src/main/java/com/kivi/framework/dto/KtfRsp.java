package com.kivi.framework.dto;

import java.io.Serializable;

import com.kivi.framework.constant.KtfError;

import lombok.Data;

@Data
public class KtfRsp<T> implements Serializable {
    /**
    * 
    */
    private static final long serialVersionUID = 1L;

    protected Long ktfId;

    protected int code;

    protected T data;

    public KtfRsp() {
        this(KtfError.SUCCESS, null, null);
    }

    public KtfRsp(int code) {
        this(code, -1L, null);
    }

    public KtfRsp(int code, Long id) {
        this(code, id, null);
    }

    public KtfRsp(int code, T data) {
        this(code, -1L, data);
    }

    public KtfRsp(Long id) {
        this(KtfError.SUCCESS, id, null);
    }

    public KtfRsp(Long id, T data) {
        this(KtfError.SUCCESS, id, data);
    }

    public KtfRsp(T data) {
        this(KtfError.SUCCESS, -1L, data);
    }

    public KtfRsp(int code, Long id, T data) {
        this.data = data;
        this.code = code;
        this.ktfId = id;
    }
}
