package com.mercadolivro.mercado.livro.validation

import com.mercadolivro.mercado.livro.service.CustomerService
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext

class EmailAvaliableValidator(var customerService: CustomerService): ConstraintValidator<EmailAvaliable,String> {
    override fun isValid(value: String?, p1: ConstraintValidatorContext?): Boolean {
        if (value.isNullOrEmpty()){
            return false
        }
        return customerService.emailAvaliable(value)
    }

}
