package com.mercadolivro.mercado.livro.events

import com.mercadolivro.mercado.livro.model.PurchaseModel
import org.springframework.context.ApplicationEvent

class PurchaseEvent (
    source:Any,
    val purchaseModel: PurchaseModel
):ApplicationEvent(source)