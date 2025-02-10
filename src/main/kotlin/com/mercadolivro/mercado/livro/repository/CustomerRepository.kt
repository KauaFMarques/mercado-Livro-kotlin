package com.mercadolivro.mercado.livro.repository

import com.mercadolivro.mercado.livro.model.CustomerModel
import org.springframework.data.repository.CrudRepository

interface CustomerRepository : CrudRepository<CustomerModel, Int> {

    fun findByNameContaining(name: String): List<CustomerModel>
    fun existsByEmail(email: String):Boolean

}