package com.mercadolivro.mercado.livro.controller.response

import com.mercadolivro.mercado.livro.enums.CustomerStatus
import jakarta.persistence.*

data class CustomerResponse (
    var id: Int? = null,

    var name: String,

    var email: String,

    var status: CustomerStatus
)
