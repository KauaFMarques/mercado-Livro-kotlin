package com.mercadolivro.mercado.livro.service

import com.mercadolivro.mercado.livro.enums.CustomerStatus
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
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import java.util.UUID // MANTIDO: Para usar UUID.randomUUID()

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
        val fakeCustomers = listOf(build_customer(), build_customer())

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
        val fakeCustomers = listOf(build_customer(), build_customer())

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

    fun build_customer(
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