package com.mercadolivro.mercado.livro.controller.response

import com.mercadolivro.mercado.livro.enums.BookStatus
import com.mercadolivro.mercado.livro.model.CustomerModel
import jakarta.persistence.Column
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import java.math.BigDecimal

data class BookResponse (
    var id: Int? = null,

    var name: String,

    var price: BigDecimal,

    var customer: CustomerModel? = null,

    var status: BookStatus?=null
)
