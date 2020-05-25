package com.kivi.framework.crypto.sm2;

import lombok.Data;

@Data
public class Sm2Result {
    /**
     * ECC密钥
     */
    private byte[] c1;

    /**
     * 真正的密文
     */
    private byte[] c2;

    /**
     * 对（c1+c2）的SM3-HASH值
     */
    private byte[] c3;

    /**
     * SM2标准的密文，即（c1+c2+c3）
     */
    private byte[] cipherText;

}
