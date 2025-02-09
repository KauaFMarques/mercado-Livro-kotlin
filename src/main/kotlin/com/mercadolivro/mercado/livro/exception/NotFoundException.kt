package com.mercadolivro.mercado.livro.exception

class NotFoundException(override val message:String, val ErrorCode:String):Exception(

){

}