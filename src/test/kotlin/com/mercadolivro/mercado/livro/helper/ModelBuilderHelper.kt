package com.mercadolivro.mercado.livro.helper

import com.mercadolivro.mercado.livro.enums.CustomerStatus
import com.mercadolivro.mercado.livro.model.BookModel
import com.mercadolivro.mercado.livro.model.CustomerModel
import com.mercadolivro.mercado.livro.model.PurchaseModel
import org.assertj.core.internal.BigDecimals
import java.math.BigDecimal
import java.util.UUID

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

fun buildPurchase(
    id: Int? = null,
    customer: CustomerModel = buildCustomer(),
    books: MutableList<BookModel> =mutableListOf(),
    nfe: String = UUID.randomUUID().toString(),
    price: BigDecimal = BigDecimal.TEN
)= PurchaseModel(
    id =id,
    customer =customer,
    books = books,
    nfe = nfe,
    price = price
)