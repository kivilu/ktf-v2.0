package com.kivi.framework.web.configuration;

import java.nio.charset.StandardCharsets;
import java.util.Collections;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;

import com.alibaba.fastjson2.JSONReader;
import com.alibaba.fastjson2.JSONWriter;
import com.alibaba.fastjson2.support.config.FastJsonConfig;
import com.alibaba.fastjson2.support.spring.http.converter.FastJsonHttpMessageConverter;

/**
 * fastjson配置类
 *
 */
@Configuration
@ConditionalOnProperty(
		prefix = "spring.http.converters",
		name = "preferred-json-mapper",
		havingValue = "fastjson",
		matchIfMissing = false)
public class FastJsonConverter {
	public static final String BEAN_NAME = "fastJsonHttpMessageConverter";

	protected FastJsonConverter() {

	}

	@Bean(BEAN_NAME)
	public FastJsonHttpMessageConverter fastJsonHttpMessageConverter() {
		FastJsonHttpMessageConverter	fastJsonConverter	= new FastJsonHttpMessageConverter();

		FastJsonHttpMessageConverter	converter			= new FastJsonHttpMessageConverter();
		// 自定义配置...
		FastJsonConfig					config				= new FastJsonConfig();
		config.setDateFormat("yyyy-MM-dd HH:mm:ss");
		config.setReaderFeatures(JSONReader.Feature.FieldBased, JSONReader.Feature.SupportArrayToBean,
				JSONReader.Feature.IgnoreSetNullValue, JSONReader.Feature.UseBigDecimalForDoubles);
		config.setWriterFeatures(JSONWriter.Feature.WriteMapNullValue, JSONWriter.Feature.WriteNullStringAsEmpty,
				JSONWriter.Feature.WriteNullListAsEmpty, JSONWriter.Feature.PrettyFormat);
		converter.setFastJsonConfig(config);
		converter.setDefaultCharset(StandardCharsets.UTF_8);
		converter.setSupportedMediaTypes(Collections.singletonList(MediaType.APPLICATION_JSON));

//		SerializeConfig serializeConfig = SerializeConfig.globalInstance;
//		serializeConfig.put(BigInteger.class, ToStringSerializer.instance);
//		serializeConfig.put(Long.class, ToStringSerializer.instance);
//		serializeConfig.put(Long.TYPE, ToStringSerializer.instance);
//		serializeConfig.put(Map.class, ToStringSerializer.instance);
//		serializeConfig.put(List.class, ToStringSerializer.instance);
//		serializeConfig.put(Json.class, SwaggerJsonSerializer.instance);
//		serializeConfig.put(Date.class, DateCodec.instance);
//
//		fastJsonConfig.setSerializeConfig(serializeConfig);
//
//		ValueFilter valueFilter = new ValueFilter() {
//			@Override
//			public Object process(Object o, String s, Object o1) {
//				if (null == o1) {
//					o1 = "";
//				}
//				return o1;
//			}
//		};
//		fastJsonConfig.setSerializeFilters(valueFilter);
//		fastJsonConverter.setFastJsonConfig(fastJsonConfig);
//
//		List<MediaType> supportedMediaTypes = new ArrayList<>();
//		supportedMediaTypes.add(MediaType.parseMediaType("text/html;charset=UTF-8"));
//		supportedMediaTypes.add(MediaType.APPLICATION_JSON);
//		// supportedMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
//		fastJsonConverter.setSupportedMediaTypes(supportedMediaTypes);

		return fastJsonConverter;
	}
}
