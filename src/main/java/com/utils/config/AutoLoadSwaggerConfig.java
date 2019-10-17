
package com.utils.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 */
@Configuration
@EnableSwagger2
public class AutoLoadSwaggerConfig implements CommandLineRunner {
    @Value("${server.port}")
    private String serverPort;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Value("${swagger.show}")
    private boolean isShow;

    private static Logger logger = LoggerFactory.getLogger(AutoLoadSwaggerConfig.class);
    @Override
    public void run(String... args){
        Properties props = System.getProperties();
        String property = props.getProperty("os.name");

        if(isShow && property.startsWith("Win")){
            String swaggerUrl = "http://127.0.0.1:" + serverPort+contextPath + "/swagger-ui.html";
            try{
                Runtime.getRuntime().exec("cmd /c start "+ swaggerUrl);
                logger.debug("run command is: cmd /c start " + swaggerUrl);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    /**
     * 自定义文档
     * @return
     */
    @Bean
    public Docket customDocket() {
        if (isShow) {
            List<Parameter> pars = new ArrayList<>();
            return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiOfflineInfo()).select()
                    .apis(RequestHandlerSelectors.basePackage("com.utils.controller"))
                    .paths(PathSelectors.any()).build().globalOperationParameters(pars);
        } else {
            return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiOnlineInfo()).select()
                    .paths(PathSelectors.none()).build();
        }
    }

    private ApiInfo apiOfflineInfo() {
        return new ApiInfoBuilder().title("UtilsApi").description("UtilsApi文档")
                .license("Utils2019").version("1.0.0").build();
    }

    private ApiInfo apiOnlineInfo() {
        return new ApiInfoBuilder().title("Api").description("Api").license("Utils  2019")
                .version("1.0.0").build();
    }
}

