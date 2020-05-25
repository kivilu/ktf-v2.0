# 工程ktf-dubbo

## 1、概述

​	ktf-dubbo是KTF框架的的dubbo集成模块项目，在pom文中包含了Spring Boot使用dubbo2.7.0的相关依赖jar包。
本项目封装了dubbo扫描、属性配置的相关操作。

## 2、主要功能列表

1. 注解

   @EnableKtfDubbo：封装了@EnableDubbo注解，本注解必须在Application启动类上使用。

2. 接口

   无

3. Configuration配置

   KtfDubboConfiguration：声明式dubbo的相关配置
