package com.mercadolivro.mercado.livro.repository

import com.mercadolivro.mercado.livro.enums.BookStatus
import com.mercadolivro.mercado.livro.model.BookModel
import com.mercadolivro.mercado.livro.model.CustomerModel
import org.springframework.data.repository.CrudRepository

interface BookRepository : CrudRepository<BookModel, Int> {
    fun findByStatus(status: BookStatus): List<BookModel>
    fun findByCustomer(customer: CustomerModel): List<BookModel>
}