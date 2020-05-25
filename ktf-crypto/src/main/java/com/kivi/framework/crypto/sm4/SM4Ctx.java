/**
 * 
 */
package com.kivi.framework.crypto.sm4;

import com.kivi.framework.crypto.enums.Padding;

/**
 * SM4 Context
 * 
 * @author Eric
 *
 */
public class SM4Ctx {

    public final int     blockSize = 16;
    public final long[]  sk;
    public final Padding padding;
    public int           mode;

    public SM4Ctx() {
        this.mode = 1;
        this.padding = Padding.PKCS7Padding;
        this.sk = new long[32];
    }

    public SM4Ctx( Padding padding ) {
        this.mode = 1;
        this.padding = padding;
        this.sk = new long[32];
    }

}
