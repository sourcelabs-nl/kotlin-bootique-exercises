package com.bootique.bootique

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class BootiqueControllerTest {

    private val mockBasketRepository = mock<BasketRepository>()
    private val mockProductRepository = mock<ProductRepository>()
    private val bootiqueController = BootiqueController(mockProductRepository, mockBasketRepository)

    @Test
    fun `test retrieving basket functionality`() {
        val basketId = "BasketId"
        val basket = Basket()

        whenever(mockBasketRepository.getBasketById(basketId)).thenReturn(basket)

        assertThat(bootiqueController.getBasket(basketId)).isEqualTo(basket)
    }
}