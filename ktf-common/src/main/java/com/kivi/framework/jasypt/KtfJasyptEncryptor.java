package com.kivi.framework.jasypt;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.util.text.BasicTextEncryptor;

public class KtfJasyptEncryptor implements StringEncryptor {
    private final String password;

    public KtfJasyptEncryptor() {
        this.password = System.getProperty("jasypt.encryptor.password");;
    }

    @Override
    public String encrypt(String message) {
        BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
        textEncryptor.setPassword(this.password);
        return textEncryptor.encrypt(message);
    }

    @Override
    public String decrypt(String encryptedMessage) {
        BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
        textEncryptor.setPassword(this.password);
        return textEncryptor.decrypt(encryptedMessage);
    }

}
