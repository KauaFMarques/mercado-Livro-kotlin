package com.mercadolivro.mercado.livro.controller.request

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotEmpty

data class PostCustomerRequest (
    @field:NotEmpty
    var name: String,

    @field:Email
    var email: String
)