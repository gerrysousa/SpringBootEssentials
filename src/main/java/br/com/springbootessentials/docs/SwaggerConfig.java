package br.com.springbootessentials.docs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import java.util.Collections;
import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

  @Bean
  public Docket apiDoc () {
    return new Docket(DocumentationType.SWAGGER_2)
        .select()
        .apis(RequestHandlerSelectors.basePackage("br.com.springbootessentials.endpoint"))
        .paths(regex("/v1.*"))
        .build()
        .globalOperationParameters(Collections.singletonList(new ParameterBuilder()
            .name("Authorization")
            .description("Bearer YOUR_TOKEN")
            .modelRef(new ModelRef("String"))
            .parameterType("header")
            .required(true)
            .build()))
        .apiInfo(metaData());
  }

  private ApiInfo metaData () {
    return new ApiInfoBuilder()
        .title("Swagger api tests")
        .description("Descricão da api aqui")
        .version("1.0")
        .contact(new Contact("Gerry Sousa", "https://github.com/gerrysousa", "tests@email.com"))
        .license("Apache version x.x.x")
        .licenseUrl("http://apache.com/license-xxx")
        .build();
  }

}
