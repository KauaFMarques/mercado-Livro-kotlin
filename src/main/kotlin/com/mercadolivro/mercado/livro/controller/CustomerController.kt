package com.mercadolivro.mercado.livro.controller

import com.mercadolivro.mercado.livro.model.CustomerModel
import com.mercadolivro.mercado.livro.request.PostCustomerRequest
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("customer")
class CustomerController {
    @GetMapping
    fun getCustomer():CustomerModel{
        return CustomerModel("1","kaua","kaua.email.com");
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody customer:PostCustomerRequest){
        println(customer)
    }
}