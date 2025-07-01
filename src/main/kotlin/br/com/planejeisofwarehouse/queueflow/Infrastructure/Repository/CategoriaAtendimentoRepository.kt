package br.com.planejeisofwarehouse.queueflow.Infrastructure.Repository

import br.com.planejeisofwarehouse.queueflow.Domain.Model.CategoriaAtendimento
import br.com.planejeisofwarehouse.queueflow.Infrastructure.Repository.Projection.CategoriaAtendimentoProjection
import io.lettuce.core.dynamic.annotation.Param
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.Optional
import java.util.UUID

@Repository
interface CategoriaAtendimentoRepository: JpaRepository<CategoriaAtendimento, UUID> {

    @Query("SELECT ca, tipo.descricao FROM CategoriaAtendimento ca " +
            "JOIN ca.tiposAtendimento tipo " +
            "WHERE ca.empresa.id = :empresaId " +
            "AND ca.isAtivo = true " +
            "AND tipo.id = :tipoAtendimentoId " +
            "AND ca.id = :id"
    )
    fun findCategoriaAtendimentoByEmpresaId(
        @Param("id") id: UUID,
        @Param("empresaId") empresaId: UUID,
        @Param("tipoAtendimentoId") tipoAtendimentoId: UUID
    ): Optional<CategoriaAtendimentoProjection>
}