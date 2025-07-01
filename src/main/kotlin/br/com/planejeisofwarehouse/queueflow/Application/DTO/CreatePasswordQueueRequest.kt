package br.com.planejeisofwarehouse.queueflow.Application.DTO

import java.util.UUID

data class CreatePasswordQueueRequest(
    val empresaId: UUID,
    val tipoSenha: Int,
    val categoriaSenha: Int
)
