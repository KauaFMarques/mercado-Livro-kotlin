package com.mercadolivro.mercado.livro.events.listener

import com.mercadolivro.mercado.livro.events.PurchaseEvent
import com.mercadolivro.mercado.livro.helper.buildPurchase
import com.mercadolivro.mercado.livro.service.PurchaseService
import com.mercadolivro.mercado.livro.events.listener.GenerateNfeListener
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.just
import io.mockk.mockkStatic
import io.mockk.runs
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.extension.ExtendWith
import java.util.UUID
import kotlin.test.Test

@ExtendWith(MockKExtension::class)
class GenerateNfeListenerTest {

    @MockK
    private lateinit var purchaseService: PurchaseService

    @InjectMockKs
    private lateinit var generateNfeListener: GenerateNfeListener

    @Test
    fun should_generate_nfe() {
        val purchase = buildPurchase(nfe = null)
        val fakeNfe = UUID.randomUUID()
        val purchaseExpect = purchase.copy(nfe = fakeNfe.toString())

        mockkStatic(UUID::class)
        every { UUID.randomUUID() } returns fakeNfe
        every { purchaseService.update(any()) } just runs

                // Agora o Kotlin encontrará o metodo .listen() na classe GenerateNfeListener
                generateNfeListener.listener(PurchaseEvent(this, purchase))

        verify(exactly = 1) { purchaseService.update(purchaseExpect) }
    }
}