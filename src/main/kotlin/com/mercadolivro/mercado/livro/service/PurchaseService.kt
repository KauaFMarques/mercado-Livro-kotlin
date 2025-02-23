package com.mercadolivro.mercado.livro.service

import com.mercadolivro.mercado.livro.events.PurchaseEvent
import com.mercadolivro.mercado.livro.model.PurchaseModel
import com.mercadolivro.mercado.livro.repository.PurchaseRepository
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service

@Service
class PurchaseService(
    private val purchaseRepository: PurchaseRepository,
    private val applicationEventPublisher:ApplicationEventPublisher
) {
    fun create(purchaseModel: PurchaseModel){
        purchaseRepository.save(purchaseModel)

        applicationEventPublisher.publishEvent(PurchaseEvent(this,purchaseModel))
    }

    fun update(purchaseModel: PurchaseModel) {
        purchaseRepository.save(purchaseModel)
    }
}
