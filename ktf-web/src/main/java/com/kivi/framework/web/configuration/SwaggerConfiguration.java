package com.kivi.framework.web.configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Sets;
import com.kivi.framework.properties.KtfSwaggerProperties;
import com.kivi.framework.util.kit.CollectionKit;
import com.kivi.framework.web.constant.WebConst;

import io.swagger.annotations.ApiOperation;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
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
public class SwaggerConfiguration {

	@Autowired
	private KtfSwaggerProperties ktfSwaggerProperties;

	@Bean
	public Docket createRestApi() {
		return new Docket(DocumentationType.SWAGGER_2).produces(Sets.newHashSet("application/json"))
				.consumes(Sets.newHashSet("application/json")).protocols(Sets.newHashSet("http", "https"))
				.apiInfo(buildApiInfo()).forCodeGeneration(true).useDefaultResponseMessages(false).select()
				.apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class)).paths(pathSelectors()).build()
				.securitySchemes(securitySchemes()).securityContexts(securityContexts());
	}

	private ApiInfo buildApiInfo() {
		return new ApiInfoBuilder().title(ktfSwaggerProperties.getTitle())// 大标题
				.description(ktfSwaggerProperties.getDescription())// 详细描述
				.version(ktfSwaggerProperties.getVersion())// 版本
				.termsOfServiceUrl(ktfSwaggerProperties.getTermsOfServiceUrl())
				.license(ktfSwaggerProperties.getLicense()).licenseUrl(ktfSwaggerProperties.getLicenseUrl()).build();
	}

	private List<ApiKey> securitySchemes() {
		List<ApiKey> apiKeyList = new ArrayList<>();
		// 注意，这里应对应登录token鉴权对应的k-v
		apiKeyList.add(new ApiKey(WebConst.AUTH_TOKEN, WebConst.AUTH_TOKEN, "header"));
		return apiKeyList;
	}

	private List<SecurityContext> securityContexts() {
		List<SecurityContext> securityContexts = new ArrayList<>();
		securityContexts.add(SecurityContext.builder().securityReferences(defaultAuth())
				.forPaths(PathSelectors.regex("^(?!auth).*$")).build());
		return securityContexts;
	}

	List<SecurityReference> defaultAuth() {
		AuthorizationScope		authorizationScope	= new AuthorizationScope("global", "accessEverything");
		AuthorizationScope[]	authorizationScopes	= new AuthorizationScope[1];
		authorizationScopes[0] = authorizationScope;
		List<SecurityReference> securityReferences = new ArrayList<>();
		securityReferences.add(new SecurityReference("Authorization", authorizationScopes));
		return securityReferences;
	}

	private Predicate<String> pathSelectors() {
		if (CollectionKit.isEmpty(ktfSwaggerProperties.getPathFilters()))
			return PathSelectors.any();

		List<Predicate<String>> filters = ktfSwaggerProperties.getPathFilters().stream().map(PathSelectors::regex)
				.collect(Collectors.toList());

		return Predicates.or(filters);
	}

}
