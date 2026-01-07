package com.mercadolivro.mercado.livro.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.jayway.jsonpath.JsonPath
import com.mercadolivro.mercado.livro.controller.request.PostCustomerRequest
import com.mercadolivro.mercado.livro.controller.request.PutCustomerRequest
import com.mercadolivro.mercado.livro.enums.CustomerStatus
import com.mercadolivro.mercado.livro.helper.buildCustomer
import com.mercadolivro.mercado.livro.repository.CustomerRepository
import com.mercadolivro.mercado.livro.repository.CustomerRepositoryTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import kotlin.random.Random
import kotlin.test.Test
import com.mercadolivro.mercado.livro.security.UserCustomDetails

@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration
@ActiveProfiles("test")
@WithMockUser
class CustomerControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var customerRepository: CustomerRepository

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @BeforeEach
    fun setup() = customerRepository.deleteAll()

    @Test
    fun `should return all customers when getAll`() {
        val customer1 = customerRepository.save(buildCustomer())
        val customer2 = customerRepository.save(buildCustomer())

        mockMvc.perform(get("/customer"))
            .andExpect(status().isOk)
            // CORREÇÃO: lenght() -> length()
            .andExpect(jsonPath("$.length()").value(2))
            .andExpect(jsonPath("$[0].id").value(customer1.id))
            .andExpect(jsonPath("$[0].status").value(customer1.status.name))
        // ... restante do código
    }

    @Test
    fun `should filter all customers by name when getAll`() {
        val customer1 = customerRepository.save(buildCustomer(name = "Kauã"))

        mockMvc.perform(get("/customer?name=Ka"))
            .andExpect(status().isOk)
            // CORREÇÃO: lenght() -> length()
            .andExpect(jsonPath("$.length()").value(1))
            .andExpect(jsonPath("$[0].id").value(customer1.id))
            .andExpect(jsonPath("$[0].name").value(customer1.name))
    }

    @Test
    fun `should create customer`() {
        val request = PostCustomerRequest("fakename","${Random.nextInt()}fake@email.com","123456")
        mockMvc.perform(post("/customer").contentType(
            MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated)

        val customers = customerRepository.findAll().toList()
        assertEquals(1,customers.size)
        assertEquals(request.name,customers[0].name)
        assertEquals(request.email,customers[0].email)
    }

    @Test
    fun `should throw error when create customer has invalid information`(){
        val request = PostCustomerRequest("","${Random.nextInt()}fake@email.com","123456")
        mockMvc.perform(post("/customer").contentType(
            MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isUnprocessableEntity)
            .andExpect(jsonPath("$.httpCode").value(422))
            .andExpect(jsonPath("$.message").value("Invalid Request"))
            .andExpect(jsonPath("$.internalCode").value("ML-001"))
    }

    @Test
    fun `should get user by id when user has the same id`(){
        val customer = customerRepository.save(buildCustomer())

        mockMvc.perform(get("/customer/${customer.id}").with(user(UserCustomDetails(customer))))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.length()").value(4)) // Ajuste o valor conforme o número de campos na sua resposta
            .andExpect(jsonPath("$.id").value(customer.id))
            .andExpect(jsonPath("$.name").value(customer.name))
            .andExpect(jsonPath("$.email").value(customer.email))
            .andExpect(jsonPath("$.status").value(customer.status.name))
    }

    @Test
    fun `should return forbidden when user has a different id`() {
        // 1. Criamos o usuário que será o "alvo" da consulta (ID na URL)
        val customer = customerRepository.save(buildCustomer())

        // 2. Criamos um segundo usuário que será o "usuário logado"
        val outroCustomer = customerRepository.save(buildCustomer(email = "outro@email.com"))

        // 3. Tentamos acessar o ID do primeiro, logado como o segundo
        mockMvc.perform(get("/customer/${customer.id}")
            .with(user(UserCustomDetails(outroCustomer)))) // Logado como usuário diferente
            .andExpect(status().isForbidden)
            .andExpect(jsonPath("$.httpCode").value(403))
            .andExpect(jsonPath("$.internalCode").value("ML-000"))
    }

    @Test
    @WithMockUser(roles = ["ADMIN"])
    fun `should get user by id when user is admin`(){
        val customer = customerRepository.save(buildCustomer())

        mockMvc.perform(get("/customer/${customer.id}"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.length()").value(4)) // Ajuste o valor conforme o número de campos na sua resposta
            .andExpect(jsonPath("$.id").value(customer.id))
            .andExpect(jsonPath("$.name").value(customer.name))
            .andExpect(jsonPath("$.email").value(customer.email))
            .andExpect(jsonPath("$.status").value(customer.status.name))
    }

    @Test
    fun `should update customer`(){
        val customer = customerRepository.save(buildCustomer())
        val request = PutCustomerRequest("Kauã", "kaua@email.com")

        mockMvc.perform(put("/customer/${customer.id}").contentType(
            MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isNoContent)

        val customers = customerRepository.findAll().toList()
        assertEquals(1,customers.size)
        assertEquals(request.name,customers[0].name)
        assertEquals(request.email,customers[0].email)
    }

    @Test
    fun `should throw error when update customer has invalid information`(){
        val request = PutCustomerRequest("","${Random.nextInt()}fake@email.com",)
        mockMvc.perform(put("/customer/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isUnprocessableEntity)
            .andExpect(jsonPath("$.httpCode").value(422))
            .andExpect(jsonPath("$.message").value("Invalid Request"))
            .andExpect(jsonPath("$.internalCode").value("ML-001"))
    }

    @Test
    fun `should delete customer`(){
        val customer = customerRepository.save(buildCustomer())
        mockMvc.perform(delete("/customer/${customer.id}"))
            .andExpect(status().isNoContent)

        val customerDeleted = customerRepository.findById(customer.id!!)
        assertEquals(CustomerStatus.INATIVO,customerDeleted.get().status)
    }

    @Test
    fun`should not found when delete customer not exists`(){
        mockMvc.perform(delete("/customer/1"))
            .andExpect(status().isNotFound)
            .andExpect(jsonPath("$.httpCode").value(404))
            .andExpect(jsonPath("$.message").value("Customer [1] not exists"))
            .andExpect(jsonPath("$.internalCode").value("ML-201"))
    }

    @Test
    fun `should not found when update customer not found existing`(){
        val request = PutCustomerRequest("Kauã", "kaua@email.com")

        mockMvc.perform(put("/customer/1").contentType(
            MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isNotFound)
            .andExpect(jsonPath("$.httpCode").value(404))
            .andExpect(jsonPath("$.message").value("Customer [1] not exists"))
            .andExpect(jsonPath("$.internalCode").value("ML-201"))
    }
}