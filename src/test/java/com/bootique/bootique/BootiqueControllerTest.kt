package com.bootique.bootique

import org.junit.*
import org.junit.runner.*
import org.mockito.*
import org.mockito.junit.*
import org.assertj.core.api.Assertions.assertThat
import org.mockito.Mockito.*
import org.mockito.stubbing.*

@RunWith(MockitoJUnitRunner::class)
class BootiqueControllerTest {

    @InjectMocks
    private lateinit var bootiqueController: BootiqueController

    @Mock
    private lateinit var mockBasketRepository: BasketRepository

    @Mock
    private lateinit var mockProductRepository: ProductRepository

    @Test
    fun `test retrieving basket functionality`() {
        val basketId = "BasketId"
        val basket = Basket()
        whenever(mockBasketRepository.getBasketById(basketId)).thenReturn(basket)
        assertThat(bootiqueController.getBasket(basketId)).isEqualTo(basket)
        verify(mockBasketRepository).getBasketById(basketId)
    }
}

// Move this to a utility class under normal circumstances, but left here for convenience.
fun <T> whenever(methodCall: T): OngoingStubbing<T> {
    return Mockito.`when`(methodCall)
}
