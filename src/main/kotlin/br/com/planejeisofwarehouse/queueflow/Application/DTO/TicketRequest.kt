package br.com.planejeisofwarehouse.queueflow.Application.DTO

import java.util.UUID

data class TicketRequest(
    val empresaId: UUID,
    val categoriaAtendimentoId: UUID,
    val tipoAtendimentoId: UUID
)

data class TicketResponse(
    val ticketId: String,
    val ticket: String,
    val empresaId: UUID,
    val categoriaAtendimento: String,
    val tipoAtendimento: String,
    val posicaoNaFila: Long? = null,
)