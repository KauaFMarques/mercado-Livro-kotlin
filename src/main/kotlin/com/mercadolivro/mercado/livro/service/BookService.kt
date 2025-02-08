package com.mercadolivro.mercado.livro.service

import com.mercadolivro.mercado.livro.enums.BookStatus
import com.mercadolivro.mercado.livro.model.BookModel
import com.mercadolivro.mercado.livro.model.CustomerModel
import com.mercadolivro.mercado.livro.repository.BookRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class BookService(
    val bookRepository: BookRepository
) {

    fun create(book: BookModel) {
        bookRepository.save(book)
    }

    fun findAll(pageable: Pageable): Page<BookModel> {
        return bookRepository.findAll(pageable)
    }

    fun findActivies(pageable: Pageable): Page<BookModel> {
        return bookRepository.findByStatus(BookStatus.ATIVO,pageable)
    }

    fun findById(id: Int): BookModel {
        return bookRepository.findById(id).orElseThrow()
    }

    fun delete(id: Int) {
        val book=findById(id)
        book.status=BookStatus.CANCELADO
        update(book)
    }

    fun update(book: BookModel) {
        bookRepository.save(book)
    }

    fun deleteByCustomer(customer: CustomerModel) {
        val books=bookRepository.findByCustomer(customer)
        for (book in books){
            book.status=BookStatus.DELETADO
        }
        bookRepository.saveAll(books)
    }

}
