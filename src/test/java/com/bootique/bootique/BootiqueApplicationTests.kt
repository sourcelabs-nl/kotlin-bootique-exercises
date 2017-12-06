package com.bootique.bootique

import org.assertj.core.api.Assertions.*
import org.junit.*
import org.junit.runner.*
import org.springframework.beans.factory.annotation.*
import org.springframework.boot.test.context.*
import org.springframework.boot.test.web.client.*
import org.springframework.core.*
import org.springframework.http.*
import org.springframework.test.context.junit4.*

/**
 * This a very poorly written app, it has no decent tests :-(
 */
@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BootiqueApplicationTest {

    @Autowired
    private lateinit var testRestTemplate: TestRestTemplate

    @Test
    fun `test get products endpoint`() {
        val products = testRestTemplate.get<List<Product>>("/products")
        assertThat(products.size).isEqualTo(4)
    }
}

// This should also be moved to a different location, but left here for clarity
inline fun <reified T> TestRestTemplate.get(url: String): T = this.exchange(
        url, HttpMethod.GET, null, ParameterizedTypeReference.forType<T>(T::class.java)
).body

