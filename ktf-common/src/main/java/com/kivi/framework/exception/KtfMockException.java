package com.kivi.framework.exception;

import com.kivi.framework.constant.KtfError;

public class KtfMockException extends KtfException {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public KtfMockException() {
        super(KtfError.E_RPC_FAIL, "RPC调用失败");
    }

    public KtfMockException(String tips) {
        super(KtfError.E_RPC_FAIL, tips);
    }

}
