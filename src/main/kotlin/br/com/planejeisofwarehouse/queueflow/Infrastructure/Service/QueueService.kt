package br.com.planejeisofwarehouse.queueflow.Infrastructure.Service

import br.com.planejeisofwarehouse.queueflow.Application.DTO.TicketRequest
import br.com.planejeisofwarehouse.queueflow.Application.DTO.TicketResponse
import br.com.planejeisofwarehouse.queueflow.Domain.Exception.EntidadeNaoEncontradaException
import br.com.planejeisofwarehouse.queueflow.Infrastructure.Repository.CategoriaAtendimentoRepository
import br.com.planejeisofwarehouse.queueflow.Infrastructure.Repository.EmpresaRepository
import br.com.planejeisofwarehouse.queueflow.Infrastructure.Repository.TipoAtendimentoRepository
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.persistence.Id
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.UUID

@Service
class QueueService (
    private val redisTemplate: RedisTemplate<String, Any>,
    private val objectMapper: ObjectMapper,
    private val categoriaRepository: CategoriaAtendimentoRepository,
    private val empresaRepository: EmpresaRepository,
    ) {

    private fun getQueueKey(empresaId: UUID): String {
        return "fila_espera:empresa:${empresaId}"
    }

    private fun getAttendedQueueKey(empresaId: UUID): String {
        return "fila_atendidos:empresa:$empresaId"
    }

    private fun queueKey(): String {
        return ""
    }

    private fun getTicketCounterKey(empresaId: UUID): String {
        return "ticket_counter:empresa:$empresaId"
    }

    fun adicionarNaFila(request: TicketRequest): TicketResponse {
        val queueKey = getQueueKey(request.empresaId)
        val ticketCounterKey = getTicketCounterKey(request.empresaId)

        val result = categoriaRepository.findCategoriaAtendimentoByEmpresaId(
            request.categoriaAtendimentoId,
            request.empresaId,
            request.tipoAtendimentoId
        )

        if (result.isEmpty) {
            throw EntidadeNaoEncontradaException("Não foi possível gerar a senha para a ${empresaRepository.findById(request.categoriaAtendimentoId).get().nome}")
        }

        val resultado = result.get()

        val nextTicketNumber = redisTemplate.opsForValue().increment(ticketCounterKey, 1) ?: 1L

        val tipoAtendimentoPrefix = resultado.tipoDescricao.firstOrNull()?.toUpperCase() ?: 'X'

        val formattedTicketNumber = String.format("%04d", nextTicketNumber)

        val ticket = "$tipoAtendimentoPrefix$formattedTicketNumber"

        val ticketId = UUID.randomUUID().toString()
        val ticketData = mapOf(
            "ticketId" to ticketId,
            "tipoAtendimento" to resultado.tipoDescricao,
            "categoriaAtendimento" to resultado.categoria.descricao,
            "empresaId" to request.empresaId.toString(),
            "dataHoraEntrada" to LocalDateTime.now().toString(),
            "status" to "AGUARDANDO"
        )

        val ticketJson = objectMapper.writeValueAsString(ticketData)

        val tamanhoAtualFila = redisTemplate.opsForList().rightPush(queueKey, ticketJson)

        return TicketResponse(
            ticketId = ticketId,
            tipoAtendimento = resultado.tipoDescricao,
            empresaId = request.empresaId,
            posicaoNaFila = tamanhoAtualFila,
            categoriaAtendimento = resultado.categoria.descricao,
            ticket = ticket,
        )
    }
}