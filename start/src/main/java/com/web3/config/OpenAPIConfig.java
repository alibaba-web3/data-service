package com.web3.config;

/**
 * @Author: mianyun.yt
 * @Date: 2023/3/28
 */

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.headers.Header;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {
    @Bean
    public OpenAPI customOpenAPI() {

        Components components = new Components()
            .addSecuritySchemes("basicScheme", new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("basic"))
            .addParameters("myHeader1", new Parameter().in("header").schema(new StringSchema()).name("myHeader1"))
            .addHeaders("myHeader2", new Header().description("myHeader2 header").schema(new StringSchema()));

        License license = new License().name("Apache 2.0").url("https://0x66.io");

        Info info = new Info()
            .title("0x66 website web3 API")
            .version("1")
            .description("0x66 website web3 API includes address, tags, transaction, block, etc. you can fetch blockchain data from this API.")
            //.termsOfService("http://swagger.io/terms/")
            .license(license);

        return new OpenAPI()
            .components(components)
            .info(info);
    }
}


