package com.mercadolivro.mercado.livro.controller.request

import java.math.BigDecimal

data class PutBookRequest (
    var name:String?,
    var price:BigDecimal?
)

