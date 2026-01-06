package com.mercadolivro.mercado.livro.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.mercadolivro.mercado.livro.helper.buildCustomer
import com.mercadolivro.mercado.livro.repository.CustomerRepository
import com.mercadolivro.mercado.livro.repository.CustomerRepositoryTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import kotlin.test.Test

@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration
@ActiveProfiles("test")
@WithMockUser
class CustomerControllerTest {
    @Autowired
    private  lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var customerRepository: CustomerRepository

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @BeforeEach
    fun setup() = customerRepository.deleteAll()

    @Test
    fun `should return all customers`(){
        val customer1 = customerRepository.save(buildCustomer())
        val customer2 = customerRepository.save(buildCustomer())

        mockMvc.perform(get("/customer"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$[0].id").value(customer1.id))
            .andExpect(jsonPath("$[0].name").value(customer1.name))
            .andExpect(jsonPath("$[0].email").value(customer1.email))
            // CORREÇÃO: Use .name para comparar como String
            .andExpect(jsonPath("$[0].status").value(customer1.status.name))

            .andExpect(jsonPath("$[1].id").value(customer2.id))
            .andExpect(jsonPath("$[1].name").value(customer2.name))
            .andExpect(jsonPath("$[1].email").value(customer2.email))
            // CORREÇÃO: Use .name para comparar como String
            .andExpect(jsonPath("$[1].status").value(customer2.status.name))
    }

}