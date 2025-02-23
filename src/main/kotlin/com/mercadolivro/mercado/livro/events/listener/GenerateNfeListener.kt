package com.mercadolivro.mercado.livro.events.listener

import com.mercadolivro.mercado.livro.events.PurchaseEvent
import com.mercadolivro.mercado.livro.service.PurchaseService
import java.util.UUID
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

@Component
class GenerateNfeListener(
    private val purchaseService: PurchaseService
) {

    @Async
    @EventListener
    fun listener(purchaseEvent: PurchaseEvent){
        val nfe = UUID.randomUUID().toString()
        val purchaseModel = purchaseEvent.purchaseModel.copy(nfe = nfe)
        purchaseService.update(purchaseModel)
    }
}
