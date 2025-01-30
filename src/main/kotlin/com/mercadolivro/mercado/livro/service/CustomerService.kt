package com.mercadolivro.mercado.livro.service

import com.mercadolivro.mercado.livro.controller.request.PostCustomerRequest
import com.mercadolivro.mercado.livro.controller.request.PutCustomerRequest
import com.mercadolivro.mercado.livro.model.CustomerModel
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.*

@Service
class CustomerService {

    val customers = mutableListOf<CustomerModel>()

    fun getAll(name: String?): List<CustomerModel> {
        name?.let {
            return customers.filter { it.name.contains(name, true) }
        }
        return customers
    }

    fun create(customer: CustomerModel) {
        val id = if(customers.isEmpty()) {
            1
        } else{
            customers.last().id!!.toInt() + 1
        }

        customer.id = id

        customers.add(customer)
    }

    fun getCustomer(id: Int): CustomerModel {
        return customers.filter { it.id == id }.first()
    }

    fun update(customer: CustomerModel) {
        customers.filter { it.id == customer.id }.first().let {
            it.name = customer.name
            it.email = customer.email
        }
    }

    fun delete(id: Int) {
        customers.removeIf { it.id == id }
    }

}