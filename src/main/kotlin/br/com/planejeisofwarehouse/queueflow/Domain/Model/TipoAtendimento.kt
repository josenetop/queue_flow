package br.com.planejeisofwarehouse.queueflow.Domain.Model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(name = "tipo_atendimento", schema = "queue_flow")
data class TipoAtendimento(

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    val id: UUID? = null,

    // Descrição do tipo de atendimento
    @Column(name = "descricao", nullable = false)
    val descricao: String,

    // Status de ativação do tipo de atendimento
    @Column(name = "is_ativo", nullable = false)
    val isAtivo: Boolean = false,

    // Relacionamento Many-to-One com Empresa
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "empresa_id", nullable = false)
    val empresa: Empresa,

    // Relacionamento Many-to-One com CategoriaAtendimento
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_atendimento_id", nullable = false)
    val categoriaAtendimento: CategoriaAtendimento
)
