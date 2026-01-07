package com.mercadolivro.mercado.livro.security

import com.mercadolivro.mercado.livro.model.CustomerModel
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class UserCustomDetails(val customerModel: CustomerModel): UserDetails {
    val id: Int = customerModel.id!!

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> =
        mutableListOf(SimpleGrantedAuthority("ROLE_USER"))

    override fun getPassword(): String = customerModel.password
    override fun getUsername(): String = customerModel.email
    override fun isAccountNonExpired(): Boolean = true
    override fun isAccountNonLocked(): Boolean = true
    override fun isCredentialsNonExpired(): Boolean = true
    override fun isEnabled(): Boolean = true
}