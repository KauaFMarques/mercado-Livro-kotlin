package com.mercadolivro.mercado.livro.repository

import com.mercadolivro.mercado.livro.model.PurchaseModel
import org.springframework.data.repository.CrudRepository

interface PurchaseRepository: CrudRepository<PurchaseModel,Int> {

}
