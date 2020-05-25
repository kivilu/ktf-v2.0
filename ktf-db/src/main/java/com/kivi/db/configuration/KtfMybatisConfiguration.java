package com.kivi.db.configuration;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.kivi.db.fill.CommonMetaObjectHandler;
import com.kivi.db.properties.KtfDbProperties;

/**
 * MybatisPlus 配置
 *
 */
@EnableTransactionManagement
@Configuration
@MapperScan(value = KtfDbProperties.MAPPER_SCAN)
public class KtfMybatisConfiguration {

	/**
	 * 分页
	 *
	 * @return
	 */
	@Bean
	public PaginationInterceptor paginationInterceptor() {
		return new PaginationInterceptor();
	}

	/**
	 * 乐观锁
	 *
	 * @return
	 */
	@Bean
	public OptimisticLockerInterceptor optimisticLockerInterceptor() {
		return new OptimisticLockerInterceptor();
	}

	/**
	 * 自动填充
	 *
	 * @return
	 */
	@Bean
	public CommonMetaObjectHandler commonMetaObjectHandler() {
		return new CommonMetaObjectHandler();
	}

	/**
	 * 自定义注入语句
	 *
	 * @return
	 */
	/*
	 * @Bean public MybatisPlusSqlInjector mybatisPlusSqlInjector() { return new
	 * MybatisPlusSqlInjector(); }
	 */
}
