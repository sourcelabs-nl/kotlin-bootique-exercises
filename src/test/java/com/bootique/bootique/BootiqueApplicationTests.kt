package com.bootique.bootique

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.exchange
import org.springframework.boot.test.web.client.postForEntity
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BootiqueApplicationTest {

    @Autowired
    private lateinit var testRestTemplate: TestRestTemplate

    @Test
    fun `get products endpoint should return a list of products`() {
        val products = testRestTemplate.exchange<List<Product>>("/products", HttpMethod.GET, HttpEntity.EMPTY).body!!
        assertThat(products.size).isEqualTo(4)
        assertThat(products.first().title).isEqualTo("iPhone X")
    }

    @Test
    fun `add product to basket results in updated basket`() {
        val httpEntity = HttpEntity(OrderItem(productId = "1", quantity = 2))
        val response = testRestTemplate.postForEntity<Basket>("/baskets/1/items", httpEntity)
        assertThat(response.statusCode.value()).isEqualTo(200)
    }
}