package com.demo.configuration;

import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * SwaggerConfig
 */
//@Configuration
//@EnableOpenApi
public class SwaggerConfiguration extends WebMvcConfigurationSupport {

	/*
	 * @Override public void addResourceHandlers(ResourceHandlerRegistry registry) {
	 * registry.addResourceHandler("/swagger-ui/**") .addResourceLocations(
	 * "classpath:/META-INF/resources/webjars/springfox-swagger-ui/")
	 * .resourceChain(false);
	 * 
	 * }
	 */

	/**
	 * 没有配置ViewResolver会导致如下错误： Could not resolve view with name
	 * 'forward:/oauth/confirm_access' in servlet with name 'dispatcherServlet'
	 */
	/*
	 * @Override protected void configureViewResolvers(ViewResolverRegistry
	 * registry) { registry.viewResolver(new InternalResourceViewResolver()); }
	 */

	/*
	 * @Override public void addViewControllers(ViewControllerRegistry registry) {
	 * registry.addViewController("/swagger-ui/").setViewName(
	 * "forward:/swagger-ui/index.html"); super.addViewControllers(registry); }
	 */

//	@Bean
//	public Docket createDemoApi() {
//		return new Docket(DocumentationType.OAS_30).apiInfo(apiInfo()).enable(true).select()
//				// 添加swagger接口提取范围,修改成指向你的controller包
//				.apis(RequestHandlerSelectors.basePackage("com.demo")).paths(PathSelectors.any()).build();
//	}
//
//	private ApiInfo apiInfo() {
//		return new ApiInfoBuilder().title("图书管理系统接口文档").description("这是一个图书管理系统")
//				.contact(new Contact("码上言", "www.baidu.com", "作者Email")).version("1.0").build();
//	}

}
