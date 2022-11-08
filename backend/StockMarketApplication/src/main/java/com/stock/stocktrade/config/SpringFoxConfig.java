package com.stock.stocktrade.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.google.common.base.Predicate;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.regex;
import static com.google.common.base.Predicates.or;

@Configuration
@EnableSwagger2
public class SpringFoxConfig {

    @Bean
    public Docket todoApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("stock-market-api")
                .apiInfo(apiInfo())
                .select()
                .paths(postPaths())
                .build();
    }

    private Predicate<String> postPaths() {
        return or(regex("/market.*"));
    }

    private ApiInfo apiInfo()
    {
        return new ApiInfoBuilder()
                .title("Stock Market Rest APIs")
                .description("API reference for Stock Market Service")
                .contact(new Contact("Thiyagu", "", "thiyagu170@gmail.com"))
                .version("1.0")
                .build();
    }
}
