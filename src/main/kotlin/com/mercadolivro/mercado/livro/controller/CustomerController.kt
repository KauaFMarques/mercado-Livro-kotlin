package com.mercadolivro.mercado.livro.controller

import com.mercadolivro.mercado.livro.model.CustomerModel
import com.mercadolivro.mercado.livro.controller.request.PostCustomerRequest
import com.mercadolivro.mercado.livro.controller.request.PutCustomerRequest
import com.mercadolivro.mercado.livro.controller.response.CustomerResponse
import com.mercadolivro.mercado.livro.extension.toCustomerModel
import com.mercadolivro.mercado.livro.extension.toResponse
import com.mercadolivro.mercado.livro.service.CustomerService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("customer")
class CustomerController(
    val customerService : CustomerService
) {

    @GetMapping
    fun getAll(@RequestParam name: String?): List<CustomerResponse> {
        return customerService.getAll(name).map { it.toResponse() }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody @Valid customer: PostCustomerRequest) {
        customerService.create(customer.toCustomerModel())
    }

    @GetMapping("/{id}")
// Se o ID da URL for igual ao ID dentro do UserCustomDetails, permite o acesso
    @PreAuthorize("hasRole('ROLE_ADMIN') or #id == principal.id")
    fun getCustomer(@PathVariable id: Int): CustomerResponse {
        return customerService.findById(id).toResponse()
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun update(@PathVariable id: Int, @RequestBody @Valid customer: PutCustomerRequest) {
        val customerSaved=customerService.findById(id)
        customerService.update(customer.toCustomerModel(customerSaved))
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: Int) {
        customerService.delete(id)
    }

}