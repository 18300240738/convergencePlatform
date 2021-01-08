package com.qr;

import com.qr.config.BaseSwaggerConfig;
import com.qr.domain.SwaggerProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 *  swagger2配置类
 * @Author wd
 * @since 10:54 2020/9/25
 **/
@Configuration
@EnableSwagger2
public class Swagger2Config extends BaseSwaggerConfig implements WebMvcConfigurer {

	@Override
	public SwaggerProperties swaggerProperties() {
		return SwaggerProperties.builder()
				.apiBasePackage("com.qr")
				.title("数据分发平台")
				.description("数据分发平台管理端接口文档")
				.contactName("wd")
				.version("1.0")
				.enableSecurity(false)
				.build();
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/**")
				.addResourceLocations("classpath:/static/");

		registry.addResourceHandler("swagger-ui.html")
				.addResourceLocations("classpath:/META-INF/resources/");

		registry.addResourceHandler("/webjars/**")
				.addResourceLocations("classpath:/META-INF/resources/webjars/");
	}
}
