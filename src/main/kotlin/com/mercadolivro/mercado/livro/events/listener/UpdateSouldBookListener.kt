package com.mercadolivro.mercado.livro.events.listener

import com.mercadolivro.mercado.livro.events.PurchaseEvent
import com.mercadolivro.mercado.livro.service.BookService
import com.mercadolivro.mercado.livro.service.PurchaseService
import java.util.UUID
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

@Component
class UpdateSouldBookListener(
    private val bookService: BookService
) {

    @Async
    @EventListener
    fun listener(purchaseEvent: PurchaseEvent){
        bookService.purchase(purchaseEvent.purchaseModel.books)
    }
}
