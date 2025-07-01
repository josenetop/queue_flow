package br.com.planejeisofwarehouse.queueflow.Infrastructure.Controller

import br.com.planejeisofwarehouse.queueflow.Application.DTO.TicketRequest
import br.com.planejeisofwarehouse.queueflow.Infrastructure.Service.QueueService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/queue-flow")
class QueueController (
    val queueService: QueueService
){

    @PostMapping("/ticket")
    fun createPassword (
        @RequestBody request: TicketRequest,
    ): ResponseEntity<String> {

        val ticketResponse = queueService.adicionarNaFila(request)

        return ResponseEntity.ok("$ticketResponse")

    }
}