package com.mercadolivro.mercado.livro.service

import com.mercadolivro.mercado.livro.events.PurchaseEvent
import com.mercadolivro.mercado.livro.helper.buildPurchase
import com.mercadolivro.mercado.livro.model.CustomerModel
import com.mercadolivro.mercado.livro.model.PurchaseModel
import com.mercadolivro.mercado.livro.repository.PurchaseRepository
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.context.ApplicationEventPublisher
import io.mockk.impl.annotations.MockK
import io.mockk.just
import io.mockk.runs
import io.mockk.slot
import io.mockk.verify
import kotlin.test.Test

@ExtendWith(MockKExtension::class)

class PurchaseServiceTest {
    @MockK
    private lateinit var purchaseRepository: PurchaseRepository

    @MockK
    private lateinit var applicationEventPublisher:ApplicationEventPublisher

    @InjectMockKs
    private lateinit var purchaseService: PurchaseService

    val purchaseEventSlot = slot<PurchaseEvent>()

    @Test
    fun should_create_purchase_and_publish_evennt(){
        val purchase = buildPurchase()

        every { purchaseRepository.save(purchase) } returns purchase
        every { applicationEventPublisher.publishEvent(any()) } just runs

        purchaseService.create(purchase)

        verify(exactly = 1) { purchaseRepository.save(purchase) }
        verify(exactly = 1) { applicationEventPublisher.publishEvent(capture(purchaseEventSlot)) }

        assertEquals(purchase, purchaseEventSlot.captured.purchaseModel)
    }

    @Test
    fun should_update_purchase(){
        val purchase = buildPurchase()

        every { purchaseRepository.save(purchase) } returns purchase

        purchaseService.update(purchase)
        verify(exactly = 1) { purchaseRepository.save(purchase) }
    }
}