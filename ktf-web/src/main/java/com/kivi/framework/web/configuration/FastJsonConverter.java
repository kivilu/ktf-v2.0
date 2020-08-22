package com.kivi.framework.web.configuration;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;

import com.alibaba.fastjson.serializer.DateCodec;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.alibaba.fastjson.serializer.ValueFilter;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.alibaba.fastjson.support.springfox.SwaggerJsonSerializer;

import springfox.documentation.spring.web.json.Json;

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

		FastJsonConfig					fastJsonConfig		= new FastJsonConfig();
		fastJsonConfig.setCharset(Charset.forName("UTF-8"));
		fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss");

		fastJsonConfig.setSerializerFeatures(SerializerFeature.QuoteFieldNames, SerializerFeature.PrettyFormat,
				SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty,
				SerializerFeature.WriteNullListAsEmpty, SerializerFeature.DisableCircularReferenceDetect);
		SerializeConfig serializeConfig = SerializeConfig.globalInstance;
		serializeConfig.put(BigInteger.class, ToStringSerializer.instance);
		serializeConfig.put(Long.class, ToStringSerializer.instance);
		serializeConfig.put(Long.TYPE, ToStringSerializer.instance);
		serializeConfig.put(Map.class, ToStringSerializer.instance);
		serializeConfig.put(List.class, ToStringSerializer.instance);
		serializeConfig.put(Json.class, SwaggerJsonSerializer.instance);
		serializeConfig.put(Date.class, DateCodec.instance);

		fastJsonConfig.setSerializeConfig(serializeConfig);

		ValueFilter valueFilter = new ValueFilter() {
			@Override
			public Object process(Object o, String s, Object o1) {
				if (null == o1) {
					o1 = "";
				}
				return o1;
			}
		};
		fastJsonConfig.setSerializeFilters(valueFilter);
		fastJsonConverter.setFastJsonConfig(fastJsonConfig);

		List<MediaType> supportedMediaTypes = new ArrayList<>();
		supportedMediaTypes.add(MediaType.parseMediaType("text/html;charset=UTF-8"));
		supportedMediaTypes.add(MediaType.APPLICATION_JSON);
		// supportedMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
		fastJsonConverter.setSupportedMediaTypes(supportedMediaTypes);

		return fastJsonConverter;
	}
}
