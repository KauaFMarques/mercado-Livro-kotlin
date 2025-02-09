package com.mercadolivro.mercado.livro.model

import  com.mercadolivro.mercado.livro.enums.BookStatus
import com.mercadolivro.mercado.livro.enums.Errors
import com.mercadolivro.mercado.livro.exception.BadRequestException
import jakarta.persistence.*
import java.math.BigDecimal

@Entity(name = "book")
data class BookModel(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    @Column
    var name: String,

    @Column
    var price: BigDecimal,


    @ManyToOne
    @JoinColumn(name = "customer_id")
    var customer: CustomerModel? = null

){
    @Column
    @Enumerated(EnumType.STRING)
    var status: BookStatus? = null
        set(value){
            if(field==BookStatus.CANCELADO || field==BookStatus.DELETADO)
                throw BadRequestException(Errors.ML102.message.format(field), Errors.ML102.code)
            field=value
        }

    constructor(id:Int?=null,
        name: String,
        price: BigDecimal,
        customer: CustomerModel?=null,
        status: BookStatus?):this(id,name,price,customer){
            this.status=status
        }
}