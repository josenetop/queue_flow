package br.com.planejeisofwarehouse.queueflow.Infrastructure.Repository

import br.com.planejeisofwarehouse.queueflow.Domain.Model.TipoAtendimento
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface TipoAtendimentoRepository: JpaRepository<TipoAtendimento, UUID> {
}