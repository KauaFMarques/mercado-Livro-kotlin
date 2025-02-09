package com.mercadolivro.mercado.livro.exception

class BadRequestException(override val message:String, val ErrorCode:String):Exception(

){

}