package com.mercadolivro.mercado.livro.controller.request

import com.mercadolivro.mercado.livro.validation.EmailAvaliable
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotEmpty

data class PutCustomerRequest (
    @field:NotEmpty(message = "nome deve ser informado")
    var name: String,

    @field:Email(message = "email deve ser válido")
    @EmailAvaliable
    var email: String
)