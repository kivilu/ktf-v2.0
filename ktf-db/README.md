# 工程framework-db

## 1、概述

​	framework-db是kivi框架的的数据库集成模块项目，在pom文中包含了Spring Boot使用数据库的相关依赖jar包。
本项目集成了druid、mybatis、tk.mybatis、pagehelper等组建，并封装了mapper扫描、事务配置、动态数据源、框架错误码实现类以及mybatis-generator文件的编写的使用。

## 2、主要功能列表

1. Mapper扫描注解

   @KtfMapperScan：封装了mapper的扫描路径。

2. 接口

   - IDao：Dao操作接口。
   - IDaoEx：继承于IDao接口，扩展了功能
   - MyMapper<T>：所有mapper接口的继承接口，tk.mybatis工具用过这个接口实现对Mapper的管理。

3. Configuration配置

   TransactionConfiguration：声明式事务定义及配置

4. 注解

   @DataSource：多数据源标识
   @BizLog：业务日志

5. Aspect

   - MultiDataSourceAspect： 多数据源切换的Aspect
   - LogAspect：业务日志Aspect

## 3、Druid配置示例

Druid在springboot框架下有更加好用的Druid Spring Boot Starter，可以省去原本写Druid的一些配置文件或者@Configuration来配置，直接将配置写在application.yml里，看起来更简单一些。

以下配置需要druid-spring-boot-starter:1.1.10版本才能支持

```
datasource:
    # 使用阿里的Druid连接池
    type: com.alibaba.druid.pool.DruidDataSource
    driver-class-name: com.mysql.jdbc.Driver
    # 填写你数据库的url、登录名、密码和数据库名
    url: jdbc:mysql://localhost:3306/databaseName?useSSL=false&characterEncoding=utf8
    username: root
    password: root
    druid:
      # 连接池的配置信息
      # 初始化大小，最小，最大
      initial-size: 5
      min-idle: 5
      maxActive: 20
      # 配置获取连接等待超时的时间
      maxWait: 60000
      # 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
      timeBetweenEvictionRunsMillis: 60000
      # 配置一个连接在池中最小生存的时间，单位是毫秒
      minEvictableIdleTimeMillis: 300000
      validationQuery: SELECT 1 FROM DUAL
      testWhileIdle: true
      testOnBorrow: false
      testOnReturn: false
      # 打开PSCache，并且指定每个连接上PSCache的大小
      poolPreparedStatements: true
      maxPoolPreparedStatementPerConnectionSize: 20
      # 配置监控统计拦截的filters，去掉后监控界面sql无法统计，'wall'用于防火墙
      filters: stat,wall,log4j
      # 通过connectProperties属性来打开mergeSql功能；慢SQL记录
      connectionProperties: druid.stat.mergeSql\=true;druid.stat.slowSqlMillis\=5000
      # 配置DruidStatFilter
      web-stat-filter:
        enabled: true
        url-pattern: "/*"
        exclusions: "*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*"
      # 配置DruidStatViewServlet
      stat-view-servlet:
        url-pattern: "/druid/*"
        # IP白名单(没有配置或者为空，则允许所有访问)
        allow: 127.0.0.1,192.168.163.1
        # IP黑名单 (存在共同时，deny优先于allow)
        deny: 192.168.1.73
        #  禁用HTML页面上的“Reset All”功能
        reset-enable: false
        # 登录名
        login-username: admin
        # 登录密码
        login-password: 123456
```

