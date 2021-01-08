package com.qr.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 *  mysql数据源配置
 * @Author wd
 * @Description //TODO
 * @Date 15:15 2020/8/27
 **/
@Configuration
public class HikariDataSourceConfig {

	@Value("${data.mysql.jdbcUrl}")
	private String jdbcUrl;
	@Value("${data.mysql.driverClassName}")
	private String driverClassName;
	@Value("${data.mysql.userName}")
	private String userName;
	@Value("${data.mysql.password}")
	private String password;
	@Value("${data.mysql.maxLifetime}")
	private Long maxLifetime;

	@Bean
	@Primary
	HikariDataSource hikariDataSource(){
		HikariConfig config = new HikariConfig();
		config.setJdbcUrl(jdbcUrl);
		config.setDriverClassName(driverClassName);
		config.setUsername(userName);
		config.setPassword(password);
		config.addDataSourceProperty("cachePrepStmts", "true");
		config.addDataSourceProperty("prepStmtCacheSize", "250");
		config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
		config.setMaxLifetime(maxLifetime);

		return new HikariDataSource(config);
	}
}
