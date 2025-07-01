package br.com.planejeisofwarehouse.queueflow.Infrastructure.Service

import br.com.planejeisofwarehouse.queueflow.Application.DTO.CreatePasswordQueueRequest
import br.com.planejeisofwarehouse.queueflow.Infrastructure.Repository.EmpresaRepository
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class EmpresaApplicationService (
    private val empresaRepository: EmpresaRepository
){
    fun createQueuePasswordFromFlow (request: CreatePasswordQueueRequest
    ): ResponseEntity<String> {

        val empresa = empresaRepository.findById(request.empresaId)


        return ResponseEntity.ok("Empresa: $empresa")
    }
}