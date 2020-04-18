package com.kivi.framework.web.configuration;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.async.DeferredResult;

import com.google.common.collect.Sets;
import com.kivi.framework.properties.KtfSwaggerProperties;
import com.kivi.framework.util.kit.CollectionKit;
import com.kivi.framework.web.constant.WebConst;

import io.swagger.annotations.ApiOperation;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Parameter;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * SwaggerConfig
 */
@Configuration
@EnableSwagger2
@ConditionalOnProperty(
		prefix = KtfSwaggerProperties.PREFIX,
		name = "enabled",
		havingValue = "true",
		matchIfMissing = false)
public class SwaggerConfiguration {

	@Autowired
	private KtfSwaggerProperties ktfSwaggerProperties;

	/**
	 * 可以定义多个组，比如本类中定义把test和demo区分开了 （访问页面就可以看到效果了）
	 *
	 */
	@ConditionalOnProperty(
			prefix = KtfSwaggerProperties.PREFIX,
			name = "authorization-enabled",
			havingValue = "true",
			matchIfMissing = false)
	@Bean
	public Docket apiWithAuth() {
		ParameterBuilder	tokenPar	= new ParameterBuilder();
		List<Parameter>		pars		= new ArrayList<Parameter>();

		tokenPar.name(WebConst.HTTP_AUTHORIZATION).description("认证令牌，字段名：" + WebConst.HTTP_AUTHORIZATION)
				.modelRef(new ModelRef("string")).parameterType("header").required(false).build();

		pars.add(tokenPar.build());

		return new Docket(DocumentationType.SWAGGER_2)
				// .groupName("后台接口") // 不理解加上以后/v2/api-docs访问不到
				.consumes(
						CollectionKit.newHashSet(MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE))
				.produces(CollectionKit.newHashSet(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.genericModelSubstitutes(DeferredResult.class).genericModelSubstitutes(ResponseEntity.class)
				.globalOperationParameters(pars).useDefaultResponseMessages(false).securitySchemes(securitySchemes())
				.securityContexts(securityContexts()).forCodeGeneration(true).enableUrlTemplating(true).pathMapping("/")// base，最终调用接口后会和paths拼接在一
				.apiInfo(buildApiInfo()).select().apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
				.paths(PathSelectors.any()).build();
	}

	@ConditionalOnProperty(
			prefix = KtfSwaggerProperties.PREFIX,
			name = "authorization-enabled",
			havingValue = "false",
			matchIfMissing = false)
	@Bean
	public Docket api() {

		return new Docket(DocumentationType.SWAGGER_2)
				// .groupName("后台接口") // 不理解加上以后/v2/api-docs访问不到
				.consumes(
						CollectionKit.newHashSet(MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE))
				.produces(CollectionKit.newHashSet(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.protocols(Sets.newHashSet("http", "https")).genericModelSubstitutes(DeferredResult.class)
				.genericModelSubstitutes(ResponseEntity.class).useDefaultResponseMessages(false).forCodeGeneration(true)
				.enableUrlTemplating(true).pathMapping("/")// base，最终调用接口后会和paths拼接在一
				.apiInfo(buildApiInfo()).select().apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
				.paths(PathSelectors.any()).build();
	}

	private ApiInfo buildApiInfo() {
		return new ApiInfoBuilder().title(ktfSwaggerProperties.getTitleUTF8())// 大标题
				.description(ktfSwaggerProperties.getDescriptionUTF8())// 详细描述
				.version(ktfSwaggerProperties.getVersion())// 版本
				.termsOfServiceUrl(ktfSwaggerProperties.getTermsOfServiceUrl())
				.license(ktfSwaggerProperties.getLicense()).licenseUrl(ktfSwaggerProperties.getLicenseUrl()).build();
	}

	private List<ApiKey> securitySchemes() {
		return CollectionKit.newArrayList(new ApiKey("mykey", "api_key", "header"));
	}

	private List<SecurityContext> securityContexts() {

		return CollectionKit.newArrayList(SecurityContext.builder().securityReferences(defaultAuth())
				.forPaths(PathSelectors.regex("/api/*")).build());
	}

	List<SecurityReference> defaultAuth() {
		AuthorizationScope		authorizationScope	= new AuthorizationScope("global", "accessEverything");
		AuthorizationScope[]	authorizationScopes	= new AuthorizationScope[1];
		authorizationScopes[0] = authorizationScope;
		return CollectionKit.newArrayList(new SecurityReference("mykey", authorizationScopes));
	}

}
