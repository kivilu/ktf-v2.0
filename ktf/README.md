# ktf-v2.0

## 简介

Ktf-v2.0是基于Spring Boot 2.x封装的一个Java开发框架，本框架既包括了：pom文件、配置文件、Freemarker模板、AOP、Mvc、Swagger2、RESTful API、事务配置等Spring Boot的基本功能，同时也包括了：Druid、MyBatis、Dubbo、Redis、Ehcache、RabbitMq、Shiro、Spring-Batch等常用组件的集成。

## 工程结构

```
├── ktf-actuator      Actuator功能封装模块
├── ktf-aop           AOP功能封装模块
├── ktf-cache         缓存功能封装模块，集成Redis和Ehcache
├── ktf-common	 	    工具类以及自定义配置文件属性
├── ktf-db            数据库功能封装模块，采用Druid和MyBatis
├── ktf-disruptor     Disruptor并发框架封装模块
├── ktf-dubbo         Dubbo功能封装模块
├── ktf-mq            MQ功能封装模块，支持RabbitMQ和RocketMq(即将实现)
├── ktf-web           Web基础功能封装模块，包括：MVC、Swagger2、RESTful
└── pom.xml                      ktf的parent pom文件
```

