package com.personal;

import com.personal.common.config.ProjectProperties;
import com.personal.common.utils.SpringContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Slf4j
@EnableTransactionManagement
@ServletComponentScan
@MapperScan("com.personal.**.dao")
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(com.personal.Application.class, args);
		printProjectConfigs();
	}
	
	private static void printProjectConfigs() {
		DataSourceProperties dataSourceProperties = SpringContextHolder.getApplicationContext().getBean(DataSourceProperties.class);
		ProjectProperties config = SpringContextHolder.getApplicationContext().getBean(ProjectProperties.class);
		log.info("开启演示模式：{}", config.isDemoMode());
		log.info("开启调试模式：{}", config.isDevMode());
		log.info("数据库：{}", dataSourceProperties.getUrl());
	}
	
}