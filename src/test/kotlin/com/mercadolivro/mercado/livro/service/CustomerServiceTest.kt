package com.mercadolivro.mercado.livro.service

import com.mercadolivro.mercado.livro.enums.CustomerStatus
import com.mercadolivro.mercado.livro.exception.NotFoundException
import com.mercadolivro.mercado.livro.model.CustomerModel
import com.mercadolivro.mercado.livro.repository.CustomerRepository
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
// REMOVIDO: import org.hibernate.validator.constraints.UUID (Causa o conflito)
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import java.util.Optional
import java.util.UUID // MANTIDO: Para usar UUID.randomUUID()
import kotlin.random.Random

@ExtendWith(MockKExtension::class)
class CustomerServiceTest {

    @MockK
    lateinit var customerRepository: CustomerRepository

    @MockK
    lateinit var bookService: BookService

    @MockK
    lateinit var bCrypt: BCryptPasswordEncoder

    @InjectMockKs
    private lateinit var customerService: CustomerService

    @Test
    fun should_return_all_customers() {
        val fakeCustomers = listOf(buildCustomer(), buildCustomer())

        every { customerRepository.findAll() } returns fakeCustomers

        val customers = customerService.getAll(null)

        assertEquals(fakeCustomers, customers)
        verify(exactly = 1) { customerRepository.findAll() }
        verify(exactly = 0) { customerRepository.findByNameContaining(any()) }
        verify (exactly = 0){customerRepository.existsByEmail(any()) }
    }

    @Test
    fun should_return_when_name_is_informed() {
        val name = UUID.randomUUID().toString()
        val fakeCustomers = listOf(buildCustomer(), buildCustomer())

        // 1. CORREÇÃO: Mockar o findByNameContaining, pois é ele que será chamado
        every { customerRepository.findByNameContaining(name) } returns fakeCustomers

        // 2. CORREÇÃO: Chamar o metodo getAll passando o nome
        val customers = customerService.getAll(name)

        assertEquals(fakeCustomers, customers)

        // 3. CORREÇÃO: Inverter as verificações (Verify)
        // Quando passamos nome, o findAll NÃO deve ser chamado
        verify(exactly = 0) { customerRepository.findAll() }

        // E o findByNameContaining deve ser chamado exatamente 1 vez com o nome correto
        verify(exactly = 1) { customerRepository.findByNameContaining(name) }

        verify(exactly = 0) { customerRepository.existsByEmail(any()) }
    }

    @Test
    fun should_customer_and_encrypt_password(){
        val initialPassword = Math.random().toString()
        val fakeCustomer = buildCustomer(password = initialPassword)
        val fakePassword = UUID.randomUUID().toString()
        val fakeCustomerEncrypted = fakeCustomer.copy(password = fakePassword)

        every { customerRepository.save(fakeCustomerEncrypted) } returns fakeCustomer
        every { bCrypt.encode(initialPassword) }

        customerService.create(fakeCustomer)

        verify(exactly = 1) { customerRepository.save(any()) }
        verify(exactly = 1) { bCrypt.encode(any()) }
    }

    @Test
    fun should_return_customer_by_id(){
        val id = Random.nextInt()
        val fakeCustomer = buildCustomer(id)
        every { customerRepository.findById(id) }returns Optional.of(fakeCustomer)

        val customer = customerService.findById(id)

        assertEquals(fakeCustomer, customer)
        verify(exactly = 1) { customerRepository.findById(id) }
    }

    @Test
    fun should_throw_error_when_not_found() {
        val id = Random.nextInt()
        every { customerRepository.findById(id) } returns Optional.empty()

        val error = assertThrows<NotFoundException> {
            customerService.findById(id)
        }

        // Agora o Service vai retornar o ID no lugar do %s, então o assertEquals vai bater!
        assertEquals("Customer [${id}] not exists", error.message)

        // CORREÇÃO: Use o código que está no seu Enum (ML-1102) e a propriedade .code (minúsculo)
        assertEquals("ML-1102", error.ErrorCode)

        verify(exactly = 1) { customerRepository.findById(id) }
    }

    fun buildCustomer(
        id: Int? = null,
        name: String = "customerName",
        email: String = "${UUID.randomUUID()}@email.com",
        password: String = "password"
    ) = CustomerModel(
        id = id,
        name = name,
        email = email,
        status = CustomerStatus.ATIVO,
        password = password,
        // CORREÇÃO: Usando mutableSetOf e o tipo Profile conforme o erro da IDE
        roles = mutableSetOf(com.mercadolivro.mercado.livro.enums.Profile.CUSTOMER)
    )
}