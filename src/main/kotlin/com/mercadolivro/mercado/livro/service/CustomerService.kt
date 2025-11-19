package com.mercadolivro.mercado.livro.service

import com.mercadolivro.mercado.livro.enums.CustomerStatus
import com.mercadolivro.mercado.livro.enums.Errors
import com.mercadolivro.mercado.livro.exception.NotFoundException
import com.mercadolivro.mercado.livro.model.CustomerModel
import com.mercadolivro.mercado.livro.repository.CustomerRepository
import org.springframework.stereotype.Service
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Service
class CustomerService(
    val customerRepository: CustomerRepository,
    val bookService: BookService,
    private val bCrypt: BCryptPasswordEncoder
) {

    fun getAll(name: String?): List<CustomerModel> {
        name?.let {
            return customerRepository.findByNameContaining(it)
        }
        return customerRepository.findAll().toList()
    }

    fun create(customer: CustomerModel) {
        // Cria uma cópia do customer com a senha criptografada
        val customerCopy = customer.copy(
            password = bCrypt.encode(customer.password)
        )
        // Salva a cópia com a senha criptografada
        customerRepository.save(customerCopy)
    }

    fun findById(id: Int): CustomerModel {
        return customerRepository.findById(id).orElseThrow {
            NotFoundException(Errors.ML201.message, Errors.ML201.code)
        }
    }

    fun update(customer: CustomerModel) {
        if(!customerRepository.existsById(customer.id!!)){
            throw NotFoundException(Errors.ML201.message, Errors.ML201.code)
        }
        customerRepository.save(customer)
    }

    fun delete(id: Int) {
        val customer = findById(id)
        bookService.deleteByCustomer(customer)

        // Cria uma cópia com status INATIVO
        val updatedCustomer = customer.copy(status = CustomerStatus.INATIVO)
        customerRepository.save(updatedCustomer)
    }

    fun emailAvaliable(email: String): Boolean {
        return !customerRepository.existsByEmail(email)
    }
}