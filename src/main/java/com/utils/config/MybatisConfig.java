package com.utils.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;


@Configuration
@MapperScan(basePackages = { "com.hy.bj.cn.reseller.mapper" })
public class MybatisConfig {
}
