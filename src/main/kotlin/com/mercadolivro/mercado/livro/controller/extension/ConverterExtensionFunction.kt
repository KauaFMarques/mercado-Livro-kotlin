package com.mercadolivro.mercado.livro.controller.extension

import com.mercadolivro.mercado.livro.controller.request.PostCustomerRequest
import com.mercadolivro.mercado.livro.controller.request.PutCustomerRequest
import com.mercadolivro.mercado.livro.model.CustomerModel

fun PostCustomerRequest.toCustomerModel(): CustomerModel {
    return CustomerModel(name=this.name, email=this.email)
}

fun PutCustomerRequest.toCustomerModel(id:String): CustomerModel {
    return CustomerModel(id=id,name=this.name, email=this.email)
}