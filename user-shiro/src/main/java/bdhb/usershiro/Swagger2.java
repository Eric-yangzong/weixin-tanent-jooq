package bdhb.usershiro;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import bdhb.usershiro.configuration.AppCommon;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class Swagger2 {
	@Bean
	public Docket createRestApi() {

		// 添加head参数start
		ParameterBuilder tokenPar = new ParameterBuilder();
		List<Parameter> pars = new ArrayList<Parameter>();
		tokenPar.name(AppCommon.TOKEN).defaultValue(
				"eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJwMDAwMDE1IiwidXNlcl9pZCI6InAwMDAwMTUiLCJ0ZW5hbnQiOiJwMDAwMDE1In0.QosbCv56mYVyNrWFwyZwsr7IXoqkuH4wD5t9GvT3FArCDBMO4m33eJUfGTERyhDCwcSYieml8s-2-aIjQb-Npw")
				.description("令牌").modelRef(new ModelRef("string")).parameterType("header").required(false).build();

		ParameterBuilder tenantPar = new ParameterBuilder();

		tenantPar.name(AppCommon.TENANT_ID).defaultValue("tat0004").description("租客id").modelRef(new ModelRef("string"))
				.parameterType("header").required(false).build();

		pars.add(tokenPar.build());
		pars.add(tenantPar.build());

		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
				.apis(RequestHandlerSelectors.basePackage("bdhb.usershiro")).paths(PathSelectors.any()).build()
				.globalOperationParameters(pars);

	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("Spring Boot中使用Swagger2构建RESTful APIs").version("1.0").build();
	}
}