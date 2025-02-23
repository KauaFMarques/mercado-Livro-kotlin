package com.mercadolivro.mercado.livro.controller.mapper

import com.mercadolivro.mercado.livro.controller.request.PostPurchaseRequest
import com.mercadolivro.mercado.livro.model.CustomerModel
import com.mercadolivro.mercado.livro.model.PurchaseModel
import com.mercadolivro.mercado.livro.service.BookService
import com.mercadolivro.mercado.livro.service.CustomerService
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class PurchaseMapper(
    private val bookService: BookService,
    private val customerService: CustomerService
){
    fun toModel(request: PostPurchaseRequest):PurchaseModel{
        val customer=customerService.findById(request.customerId)
        val books=bookService.findAllByIds(request.bookIds)

        return PurchaseModel(
            customer = customer,
            books=books.toMutableList(),
            price=books.sumOf { it.price }
        )
    }
}