package br.com.vda.fipe.infra.exceptionhandler

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.util.*

@RestControllerAdvice("br.com.vda.fipe.controller")
class ControllerAdvice {
    @ResponseBody
    @ExceptionHandler(
        ValidacaoException::class
    )
    fun validacaoException(notFound: ValidacaoException): ResponseEntity<MessageExceptionHandler> {
        val error = MessageExceptionHandler(Date(), HttpStatus.BAD_REQUEST.value(), notFound.message.toString())
        return ResponseEntity(error, HttpStatus.BAD_REQUEST)
    }

    @ResponseBody
    @ExceptionHandler(
        NaoEncontradoException::class
    )
    fun naoEncontradoException(notFound: NaoEncontradoException): ResponseEntity<MessageExceptionHandler> {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}