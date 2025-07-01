package br.com.planejeisofwarehouse.queueflow.Infrastructure.Event.Listener

import br.com.planejeisofwarehouse.queueflow.Domain.Exception.EntidadeNaoEncontradaException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.View
import java.time.LocalDateTime

@ControllerAdvice
class ExceptionHandler(private val error: View) {

    @ExceptionHandler(Exception::class)
    fun handleGenericException (
        ex: Exception,
        request: WebRequest
    ): ResponseEntity<ErrorResponse> {
        val error = ErrorResponse (
            timestamp = LocalDateTime.now(),
            status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
            error = "Erro interno no servidor",
            message = ex.message ?: "Erro Desconhecido",
            path = request.getDescription(false).removePrefix("URI=")

        )

        return ResponseEntity(error, HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @ExceptionHandler(EntidadeNaoEncontradaException::class)
    fun handleEntidadeNaoEncontrada(
        ex: EntidadeNaoEncontradaException,
        request: WebRequest
    ): ResponseEntity<ErrorResponse> {
        val error = ErrorResponse(
            timestamp = LocalDateTime.now(),
            status = HttpStatus.NOT_FOUND.value(),
            error = "Entidade não encontrada",
            message = ex.message ?: "Recurso não encontrado",
            path = request.getDescription(false).removePrefix("uri=")
        )
        return ResponseEntity(error, HttpStatus.NOT_FOUND)
    }
}