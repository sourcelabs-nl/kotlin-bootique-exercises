package com.bootique.bootique

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExtendWith(MockitoExtension::class)
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