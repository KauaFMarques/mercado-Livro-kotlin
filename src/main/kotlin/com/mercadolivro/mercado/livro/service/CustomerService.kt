package com.mercadolivro.mercado.livro.service

import com.mercadolivro.mercado.livro.enums.CustomerStatus
import com.mercadolivro.mercado.livro.enums.Errors
import com.mercadolivro.mercado.livro.exception.NotFoundException
import com.mercadolivro.mercado.livro.model.CustomerModel
import com.mercadolivro.mercado.livro.repository.CustomerRepository
import org.springframework.stereotype.Service
import java.lang.Exception

@Service
class CustomerService(
    val customerRepository: CustomerRepository,
    val bookService: BookService
) {

    fun getAll(name: String?): List<CustomerModel> {
        name?.let {
            return customerRepository.findByNameContaining(it)
        }
        return customerRepository.findAll().toList()
    }

    fun create(customer: CustomerModel) {
        customerRepository.save(customer)
    }

    fun findById(id: Int): CustomerModel {
        return customerRepository.findById(id).orElseThrow { NotFoundException(Errors.ML201.message,Errors.ML101.code) }
    }

    fun update(customer: CustomerModel) {
        if(!customerRepository.existsById(customer.id!!)){
            throw Exception()
        }

        customerRepository.save(customer)
    }

    fun delete(id: Int) {
        val customer=findById(id)
        bookService.deleteByCustomer(customer)
        customer.status=CustomerStatus.INATIVO
        customerRepository.save(customer)
    }

}