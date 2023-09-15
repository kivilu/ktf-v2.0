package com.kivi.framework.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class KtfReq<T> implements Serializable {
    /**
    * 
    */
    private static final long serialVersionUID = 1L;

    protected Long ktfId;

    protected T data;

    public KtfReq() {
        this(null, null);
    }

    public KtfReq(Long id) {
        this(id, null);
    }

    public KtfReq(T data) {
        this(null, data);
    }

    public KtfReq(Long id, T data) {
        this.ktfId = id;
        this.data = data;
    }
}
