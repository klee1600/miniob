package com.nju.edu.erp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket createRestApi(Environment environment) {
        Profiles profiles = Profiles.of("dev");
        boolean flag = environment.acceptsProfiles(profiles);


/*
        todo 这里的身份验证完全绕不开了 只能先绕过身份切面验证了
        ParameterBuilder ticketPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<>();
        ticketPar.name("Authorization").description("加在请求头部的用户类型认证")//Token
                .modelRef(new ModelRef("string")).parameterType("header")
                .defaultValue("ADMIN")
                *//*
                INVENTORY_MANAGER, //库存管理人员
                SALE_STAFF, // 进货销售人员
                FINANCIAL_STAFF, // 财务人员
                SALE_MANAGER, //销售经理
                HR, // 人力资源人员
                GM, // 总经理
                ADMIN // 最高权限
                *//*
                .required(false).build(); //header中的token参数非必填，传空也可以
        pars.add(ticketPar.build());
*/


        return new Docket(DocumentationType.SWAGGER_2)
                .pathMapping("/")
                .enable(flag)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.nju.edu.erp.web.controller"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(new ApiInfoBuilder()
                        .title("软工二大作业后端接口-Swagger")
                        .description("erp后端接口")
                        .build())
                /*.globalOperationParameters(pars)*/;
    }
}
