package com.hujiya.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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

import java.util.ArrayList;
import java.util.List;

/**
 * @author nicolasli
 * @version 1.0
 * @classname SwaggerConfig
 * @description SwaggerConfig ACCESS URL : http://server:port/swagger-ui.html#/
 * @date 2020/3/27 下午5:00
 **/
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket createRestApi() {
        // 在swagger里显示header
        ParameterBuilder userGid = new ParameterBuilder();
        userGid.parameterType("header")
                .name("oooo")
                .description("userGid")
                .required(false)
                .modelRef(new ModelRef("string"));
        List<Parameter> pars = new ArrayList<>();
        pars.add(userGid.build());

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .globalOperationParameters(pars)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.newstar.scorpio"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("api")
                .description("api manger")
                .version("1.0")
                .build();
    }

}

