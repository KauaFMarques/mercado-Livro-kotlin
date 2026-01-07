 package com.mercadolivro.mercado.livro.repository

import com.mercadolivro.mercado.livro.helper.buildCustomer
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import kotlin.jvm.Throws
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(MockKExtension::class)
class CustomerRepositoryTest {
    @Autowired
    private lateinit var customerRepository: CustomerRepository

    @BeforeEach
    fun setup() =  customerRepository.deleteAll()

    @Test
    fun should_return_name_containing(){
        val marcos = customerRepository.save(buildCustomer(name = "Marcos"))
        val mateus = customerRepository.save(buildCustomer(name = "Mateus"))
        val kaua = customerRepository.save(buildCustomer(name = "Kauã"))

        val customers = customerRepository.findByNameContaining("Ma")

        Assertions.assertEquals(listOf(marcos,mateus),customers)
    }

    @Nested
    inner class exists_by_email{
        @Test
        fun should_return_true_when_email_exists(){
            val email =  "teste@email.com"
            customerRepository.save(buildCustomer(email = email))

            val exists = customerRepository.existsByEmail(email)

            assertTrue(exists)
        }

        @Test
        fun should_return_false_when_email_do_not_exists(){
            val email =  "naoExisteTeste@email.com"

            val exists = customerRepository.existsByEmail(email)

            assertFalse(exists)
        }

    }

    @Nested
    inner class find_by_email {
        @Test
        fun should_return_customer_when_email_exists(){
            val email = "teste@email.com"
            val customer = customerRepository.save(buildCustomer(email = email))

            val result = customerRepository.findByEmail(email)

            // CORREÇÃO: Não atribua o assertTrue a uma variável
            assertNotNull(result)
            assertEquals(customer.id, result?.id)
            assertEquals(customer.email, result?.email)
        }

        @Test
        fun should_return_null_when_email_do_not_exists() { // Nome ajustado para null
            val email = "naoExisteTeste@email.com"

            val result = customerRepository.findByEmail(email)

            assertNull(result)
        }

    }
}