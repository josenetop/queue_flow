package br.com.planejeisofwarehouse.queueflow.Infrastructure.Repository

import br.com.planejeisofwarehouse.queueflow.Domain.Model.Empresa
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional
import java.util.UUID

@Repository
interface EmpresaRepository: JpaRepository<Empresa, UUID> {
    fun findByCnpj(cnpj: String): Optional<Empresa>?
}