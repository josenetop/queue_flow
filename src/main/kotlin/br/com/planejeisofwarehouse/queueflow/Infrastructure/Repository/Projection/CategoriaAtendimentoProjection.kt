package br.com.planejeisofwarehouse.queueflow.Infrastructure.Repository.Projection

import br.com.planejeisofwarehouse.queueflow.Domain.Model.CategoriaAtendimento

data class CategoriaAtendimentoProjection(
    val categoria: CategoriaAtendimento,
    val tipoDescricao: String
)
