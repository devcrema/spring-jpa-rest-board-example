package devcrema.spring_jpa_rest_board_example.config;

import devcrema.spring_jpa_rest_board_example.test_fixture.UserFixtureGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .apiInfo(makeApiInfo());
    }

    private ApiInfo makeApiInfo() {
        return new ApiInfoBuilder()
                .title("Spring JPA Rest Post Example API documents")
                .description(
                        "재미삼아 만들어본 스프링 부트 + JPA + rest 게시판 토이 프로젝트." +
                                "<br>테스트 유저 username: " + UserFixtureGenerator.EMAIL +
                                "<br>테스트 유저 password: " + UserFixtureGenerator.PASSWORD +
                                "<br>클라이언트ID: " + Oauth2AuthorizationConfig.CLIENT_ID +
                                "<br>클라이언트Secret: " + Oauth2AuthorizationConfig.CLIENT_SECRET)
                .version("v0.1")
                .contact(new Contact("devcrema", "https://github.com/devcrema", "devcrema@gmail.com"))
                .build();
    }
}
