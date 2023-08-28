package com.kivi.db.configuration;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
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
	public MybatisPlusInterceptor mybatisPlusInterceptor() {
		MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
		interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
		// 乐观锁
		interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
		return interceptor;
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
