package com.bootique.bootique

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.*
import org.springframework.test.context.junit4.SpringRunner

/**
 * This a very poorly written app, it has no decent tests :-(
 */
@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BootiqueApplicationTest {

    @Autowired
    private lateinit var testRestTemplate: TestRestTemplate

    @Test
    fun `test bootique get products endpoint`() {
        val products = testRestTemplate.get<List<Product>>("/products")
        assertThat(products[0].title).isEqualTo("iPhone X")
    }

    @Test
    fun `add product to basket`() {
        val productId = "1"
        val quantity = 2

        val response = testRestTemplate.postJson<String>("/baskets/1/items", """
                {
                    "productId":"$productId",
                    "quantity": $quantity
                }
        """)

        assertThat(response.statusCode.value()).isEqualTo(200)
    }
}

// This should also be moved to a different location, but left here for clarity
inline fun <reified T> TestRestTemplate.postJson(url: String, json: String): ResponseEntity<T> {
    val headers = HttpHeaders()
    headers.contentType = MediaType.APPLICATION_JSON
    val entity = HttpEntity(json, headers)
    return this.postForEntity(url, entity, T::class.java)
}

// This should also be moved to a different location, but left here for clarity
inline fun <reified T> TestRestTemplate.get(url: String): T = this.exchange(
        url, HttpMethod.GET, null, object : ParameterizedTypeReference<T>() {}
).body


