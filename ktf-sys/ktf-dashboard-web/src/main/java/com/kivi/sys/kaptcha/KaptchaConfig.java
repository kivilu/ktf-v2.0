package com.kivi.sys.kaptcha;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.util.Config;

/**
 * @Description Kaptcha验证码配置
 */
@Component
public class KaptchaConfig {

	private final static int	width	= 200;
	private final static int	height	= 45;

	@Bean
	public Producer KaptchaProducer() {
		Properties kaptchaProperties = new Properties();
		kaptchaProperties.put("kaptcha.border", "no");
		kaptchaProperties.put("kaptcha.textproducer.char.length", "4");
		kaptchaProperties.put("kaptcha.textproducer.char.space", "6");
		kaptchaProperties.put("kaptcha.image.height", height);
		kaptchaProperties.put("kaptcha.image.width", width);
		kaptchaProperties.put("kaptcha.background.impl", "com.kivi.dashboard.kaptcha.TranslucentBackground");
		kaptchaProperties.put("kaptcha.obscurificator.impl", "com.google.code.kaptcha.impl.WaterRipple");
		kaptchaProperties.put("kaptcha.textproducer.font.color", "orange");
		kaptchaProperties.put("kaptcha.textproducer.font.size", "35");
		kaptchaProperties.put("kaptcha.noise.color", "blue");
		kaptchaProperties.put("kaptcha.noise.impl", "com.google.code.kaptcha.impl.DefaultNoise");
		kaptchaProperties.put("kaptcha.textproducer.char.string", "123456789ABCDEFGHIJKLMOPQRSTUVWXYZ");

		Config config = new Config(kaptchaProperties);
		return config.getProducerImpl();
	}
}
