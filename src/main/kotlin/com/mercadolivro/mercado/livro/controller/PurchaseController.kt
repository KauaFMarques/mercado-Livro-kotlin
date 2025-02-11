package com.mercadolivro.mercado.livro.controller

import com.mercadolivro.mercado.livro.controller.request.PostPurchaseRequest
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController("/purchase")
class PurchaseController {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun purchase(@RequestBody request= PostPurchaseRequest){

    }
}