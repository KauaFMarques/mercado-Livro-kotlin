package com.mercadolivro.mercado.livro.exception

import com.mercadolivro.mercado.livro.controller.response.ErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest

@ControllerAdvice
class ControllerAdvice {

    @ExceptionHandler(Exception::class)
    fun handleExcepton(ex:Exception,request:WebRequest):ResponseEntity<ErrorResponse>{
        val erro=ErrorResponse(
            400,
            "esse erro n existe",
            "0001",
            null
        )
        return ResponseEntity(erro, HttpStatus.BAD_REQUEST)
    }
}