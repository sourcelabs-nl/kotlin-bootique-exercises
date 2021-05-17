package com.bootique.bootique

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.exchange
import org.springframework.boot.test.web.client.postForEntity
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.support.GenericApplicationContext
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.test.context.ContextConfiguration

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = [BootiqueApplicationTest::class])
class BootiqueApplicationTest : ApplicationContextInitializer<GenericApplicationContext> {

    @Autowired
    private lateinit var testRestTemplate: TestRestTemplate

    override fun initialize(ctx: GenericApplicationContext) {
        handlers().initialize(ctx) // workaround for initializing the bean definition dsl.
    }

    @Test
    fun `get products endpoint should return a list of products`() {
        val response = testRestTemplate.exchange<List<Product>>("/products", HttpMethod.GET, HttpEntity.EMPTY)
        assertThat(response.statusCode.value()).isEqualTo(200)

        val products = response.body!!
        assertThat(products.size).isEqualTo(4)
        assertThat(products.first().title).isEqualTo("iPhone XX")
    }

    @Test
    fun `add product to basket results in updated basket`() {
        val productId = "1"
        val quantity = 2
        val body = """
            {
                "productId":"$productId",
                "quantity":$quantity
            }
        """
        val httpEntity = HttpEntity(body, HttpHeaders().apply { contentType = MediaType.APPLICATION_JSON })
        val response = testRestTemplate.postForEntity<Basket>("/baskets/1/items", httpEntity)
        assertThat(response.statusCode.value()).isEqualTo(200)
    }
}