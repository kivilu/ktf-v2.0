package com.kivi.framework.crypto.domain;

import java.io.Serializable;

import lombok.Data;

@Data
public class KeyPairDO implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    /**
     * 公钥
     */
    protected String          pub_pem;

    /**
     * 私钥
     */
    protected String          pri_pem;
}
