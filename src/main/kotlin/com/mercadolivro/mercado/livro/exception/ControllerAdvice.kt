package com.mercadolivro.mercado.livro.exception

import com.mercadolivro.mercado.livro.controller.response.ErrorResponse
import com.mercadolivro.mercado.livro.controller.response.FieldErrorResponse
import com.mercadolivro.mercado.livro.enums.Errors
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest

@ControllerAdvice
class ControllerAdvice {

    @ExceptionHandler(NotFoundException::class)
    fun handleNotFoundExcepton(ex:NotFoundException,request:WebRequest):ResponseEntity<ErrorResponse>{
        val erro=ErrorResponse(
            HttpStatus.NOT_FOUND.value(),
            ex.message,
            ex.ErrorCode,
            null
        )
        return ResponseEntity(erro, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(BadRequestException::class)
    fun handleBadRequestExcepton(ex:BadRequestException,request:WebRequest):ResponseEntity<ErrorResponse>{
        val erro=ErrorResponse(
            HttpStatus.BAD_REQUEST.value(),
            ex.message,
            ex.ErrorCode,
            null
        )
        return ResponseEntity(erro, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumetNotValidException(ex:MethodArgumentNotValidException,request:WebRequest):ResponseEntity<ErrorResponse>{
        val erro=ErrorResponse(
            HttpStatus.UNPROCESSABLE_ENTITY.value(),
            Errors.ML001.message,
            Errors.ML001.code,
            ex.bindingResult.fieldErrors.map { FieldErrorResponse(it.defaultMessage?:"invalid",it.field) }
        )
        // CORRIJA AQUI: estava BAD_REQUEST, deve ser UNPROCESSABLE_ENTITY
        return ResponseEntity(erro, HttpStatus.UNPROCESSABLE_ENTITY)
    }
}