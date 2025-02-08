package com.mercadolivro.mercado.livro.repository

import com.mercadolivro.mercado.livro.enums.BookStatus
import com.mercadolivro.mercado.livro.model.BookModel
import com.mercadolivro.mercado.livro.model.CustomerModel
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.CrudRepository

interface BookRepository : JpaRepository<BookModel, Int> {
    fun findByStatus(status: BookStatus,pageable: Pageable): Page<BookModel>
    fun findByCustomer(customer: CustomerModel): List<BookModel>

//    fun findAll(pageable: Pageable): Page<BookModel>
}