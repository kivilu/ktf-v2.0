package com.kivi.framework.properties;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.kivi.framework.util.kit.CollectionKit;

import lombok.Data;

@Data
@Configuration(KtfSwaggerProperties.BEAN_NAME)
@ConfigurationProperties(prefix = KtfSwaggerProperties.PREFIX)
public class KtfSwaggerProperties implements IKtfProperties {
	public static final String	BEAN_NAME				= "ktfSwaggerProperties";
	public static final String	PREFIX					= "ktf.swagger";

	private Boolean				enabled					= true;
	private String				title					= "系统API服务";
	private String				description				= "系统API接口文档简要描述";
	private String				version					= "1.0.0";
	private String				termsOfServiceUrl;
	private Boolean				authorizationEnabled	= true;
	private String				license					= "MIT 协议";
	private String				licenseUrl				= "http://www.opensource.org/licenses/MIT";
	private Boolean				enableOrgApi			= false;
	private Boolean				enableSysApi			= false;
	private Boolean				enableDicApi			= false;
	private Boolean				enablePermissionApi		= false;
	private Boolean				enableRegionApi			= false;

	/**
	 * 禁用swagger时的重定向地址
	 */
	private String				redirectUri				= "/";

	/**
	 * 开放接口的列表
	 */
	private List<String>		pathFilters				= CollectionKit.newArrayList();

	public String getTitleUTF8() {
		String utf8 = title;

		try {
			utf8 = new String(title.getBytes("iso-8859-1"), "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return utf8;
	}

	public String getDescriptionUTF8() {
		String utf8 = description;

		try {
			utf8 = new String(description.getBytes("iso-8859-1"), "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return utf8;
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
