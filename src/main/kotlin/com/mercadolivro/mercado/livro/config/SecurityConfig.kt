package com.mercadolivro.mercado.livro.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.mercadolivro.mercado.livro.controller.response.ErrorResponse
import com.mercadolivro.mercado.livro.enums.Errors
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.access.AccessDeniedHandler

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
class SecurityConfig {

    @Bean
    fun bCryptPasswordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .authorizeHttpRequests { auth ->
                auth
                    .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                    .requestMatchers("/customer/**").permitAll()
                    .requestMatchers("/books/**").permitAll()
                    .anyRequest().authenticated()
            }
            .csrf { it.disable() }
            .httpBasic { }
            .exceptionHandling { exceptionHandling ->
                exceptionHandling.accessDeniedHandler(customAccessDeniedHandler())
            }

        return http.build()
    }

    @Bean
    fun customAccessDeniedHandler(): AccessDeniedHandler {
        return AccessDeniedHandler { request: HttpServletRequest,
                                     response: HttpServletResponse,
                                     accessDeniedException: AccessDeniedException ->

            val errorResponse = ErrorResponse(
                httpCode = HttpStatus.FORBIDDEN.value(),
                message = Errors.ML000.message,
                internalCode = Errors.ML000.code,
                errors = null
            )

            response.status = HttpStatus.FORBIDDEN.value()
            response.contentType = "application/json"
            response.characterEncoding = "UTF-8"

            val objectMapper = ObjectMapper()
            response.writer.write(objectMapper.writeValueAsString(errorResponse))
        }
    }
}