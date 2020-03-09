package com.bootique.bootique

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.support.beans
import org.springframework.web.servlet.function.router
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

/**
 * Spring boot application with Swagger2 enabled.
 */
@SpringBootApplication
@EnableSwagger2
class BootiqueApplication

fun main(args: Array<String>) {
    runApplication<BootiqueApplication>(*args) {
        addInitializers(
                beans {
                    bean<Docket> {
                        Docket(DocumentationType.SWAGGER_2)
                                .select()
                                .apis(RequestHandlerSelectors.basePackage("com.bootique.bootique"))
                                .build()
                    }
                    bean<BootiqueHandler>()
                    bean {
                        router {
                            GET("/products", ref<BootiqueHandler>()::products)
                            GET("/baskets/{id}", ref<BootiqueHandler>()::getBasket)
                            POST("/baskets/{id}/items", ref<BootiqueHandler>()::addToBasket)
                        }
                    }
                }
        )
    }
}