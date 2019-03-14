package com.kivi.framework.db.configuration;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class TransactionConfiguration {
	// private static final int TX_METHOD_TIMEOUT = -1;

	// 其中 dataSource 框架会自动为我们注入
	@Bean
	public PlatformTransactionManager txManager(DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}

	@Bean
	public Object testBean(PlatformTransactionManager platformTransactionManager) {
		System.out.println(">>>>>>>>>>" + platformTransactionManager.getClass().getName());
		return new Object();
	}

	/* 事务拦截类型 */
	/*
	 * @Bean( "txSource" )
	 * public TransactionAttributeSource transactionAttributeSource( KtfDbProperties
	 * ktfDbProperties ) {
	 * NameMatchTransactionAttributeSource source = new
	 * NameMatchTransactionAttributeSource();
	 * Map<String, TransactionAttribute> txMap = new HashMap<>();
	 * 
	 * if (StrKit.isNotEmpty(ktfDbProperties.getTxAdviceNotSupported())) {
	 * 只读事务，不做更新操作
	 * RuleBasedTransactionAttribute readOnlyTx = new
	 * RuleBasedTransactionAttribute();
	 * readOnlyTx.setReadOnly(true);
	 * readOnlyTx.setPropagationBehavior(TransactionDefinition.
	 * PROPAGATION_NOT_SUPPORTED);
	 * 
	 * String[] methods = StrKit.split(ktfDbProperties.getTxAdviceNotSupported(),
	 * StrKit.COMMA);
	 * for (String method : methods)
	 * txMap.put(method, readOnlyTx);
	 * }
	 * 
	 * if (StrKit.isNotEmpty(ktfDbProperties.getTxAdviceRequired())) {
	 * 当前存在事务就使用当前事务，当前不存在事务就创建一个新的事务
	 * RuleBasedTransactionAttribute requiredTx = new RuleBasedTransactionAttribute(
	 * TransactionDefinition.PROPAGATION_REQUIRED,
	 * Collections.singletonList(new RollbackRuleAttribute(Exception.class)));
	 * requiredTx.setTimeout(TX_METHOD_TIMEOUT);
	 * 
	 * String[] methods = StrKit.split(ktfDbProperties.getTxAdviceRequired(),
	 * StrKit.COMMA);
	 * for (String method : methods)
	 * txMap.put(method, requiredTx);
	 * }
	 * 
	 * if (StrKit.isNotEmpty(ktfDbProperties.getTxAdviceSupports())) {
	 * 如果当前没有事务存在，就以非事务方式执行；如果有，就使用当前事务
	 * RuleBasedTransactionAttribute supportsTx = new
	 * RuleBasedTransactionAttribute();
	 * supportsTx.setReadOnly(true);
	 * supportsTx.setPropagationBehavior(TransactionDefinition.PROPAGATION_SUPPORTS)
	 * ;
	 * 
	 * String[] methods = StrKit.split(ktfDbProperties.getTxAdviceSupports(),
	 * StrKit.COMMA);
	 * for (String method : methods)
	 * txMap.put(method, supportsTx);
	 * }
	 * 
	 * source.setNameMap(txMap);
	 * 
	 * return source;
	 * }
	 */

	/* 事务拦截器 */
	/*
	 * @Bean( "txInterceptor" )
	 * TransactionInterceptor getTransactionInterceptor( PlatformTransactionManager
	 * tx, KtfDbProperties ktfDbProperties ) {
	 * return new TransactionInterceptor(tx,
	 * transactionAttributeSource(ktfDbProperties));
	 * }
	 */

	/** 切面拦截规则 参数会自动从容器中注入 */
	/*
	 * @Bean
	 * public AspectJExpressionPointcutAdvisor pointcutAdvisor(
	 * TransactionInterceptor txInterceptor,
	 * KtfDbProperties ktfDbProperties ) {
	 * AspectJExpressionPointcutAdvisor pointcutAdvisor = new
	 * AspectJExpressionPointcutAdvisor();
	 * pointcutAdvisor.setAdvice(txInterceptor);
	 * pointcutAdvisor.setExpression(ktfDbProperties.getTxPointcutExpression());
	 * return pointcutAdvisor;
	 * }
	 */
}
