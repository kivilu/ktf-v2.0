package com.kivi.framework.web.properties;

import java.io.File;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.kivi.framework.properties.IKtfProperties;
import com.kivi.framework.util.kit.StrKit;

import lombok.Data;

@ConditionalOnProperty(prefix = KtfWebProperties.PREFIX, name = "enabled", havingValue = "true", matchIfMissing = true)
@Configuration(KtfWebProperties.BEAN_NAME)
@ConfigurationProperties(prefix = KtfWebProperties.PREFIX)
@Data
public class KtfWebProperties implements IKtfProperties {
	public static final String	BEAN_NAME			= "ktfWebProperties";
	public static final String	PREFIX				= "ktf.web";

	private Boolean				enabled				= true;
	private Boolean				kaptchaOpen			= false;
	/**
	 * Web请求默认超时时间
	 */
	private Long				webRequestTimeout	= 30000L;
	private String				fileUploadPath		= "/app/upload";

	public String getFileUploadPath() {
		if (!StrKit.endWith(fileUploadPath, "/", true))
			fileUploadPath += "/";

		File dir = new File(fileUploadPath).getAbsoluteFile();
		if (!dir.exists())
			dir.mkdirs();

		return fileUploadPath;
	}

	@Override
	public String prefix() {
		return PREFIX;
	}

	@Override
	public String beanName() {
		return BEAN_NAME;
	}

}
