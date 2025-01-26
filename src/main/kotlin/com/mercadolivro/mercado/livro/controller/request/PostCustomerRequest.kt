package com.mercadolivro.mercado.livro.controller.request

import com.mercadolivro.mercado.livro.model.CustomerModel

data class PostCustomerRequest (
    var name: String,

    var email: String
)
