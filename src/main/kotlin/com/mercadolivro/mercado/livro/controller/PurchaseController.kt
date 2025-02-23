package com.mercadolivro.mercado.livro.controller

import com.mercadolivro.mercado.livro.controller.mapper.PurchaseMapper
import com.mercadolivro.mercado.livro.controller.request.PostPurchaseRequest
import com.mercadolivro.mercado.livro.service.PurchaseService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/purchase")
class PurchaseController (
    private val purchaseService: PurchaseService,
    private val purchaseMapper: PurchaseMapper
){

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun purchase(@RequestBody request: PostPurchaseRequest) {
        purchaseService.create(purchaseMapper.toModel(request))
    }
}
