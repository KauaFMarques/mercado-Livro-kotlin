package com.mercadolivro.mercado.livro.enums

enum class Errors(val code:String,val message:String) {
    ML101("ML-1001","Book [%s] not exists"),
    ML102("ML_102","Cannot update book with status[%s]"),
    ML201("ML-1102","Customer [%s] not exists")
}