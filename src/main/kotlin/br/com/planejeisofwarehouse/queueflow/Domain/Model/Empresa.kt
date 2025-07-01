package br.com.planejeisofwarehouse.queueflow.Domain.Model

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "empresa", schema = "queue_flow")
data class Empresa(

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    val id: UUID? = null,

    @Column(name = "nome", nullable = false, length = 255)
    val nome: String,

    @Column(name = "cnpj", nullable = false, unique = true, length = 14)
    val cnpj: String,

    @Column(name = "data_criacao", nullable = false)
    val dataCriacao: LocalDateTime = LocalDateTime.now(),

    // Relacionamento One-to-Many com CategoriaAtendimento
    // 'mappedBy' indica o campo na entidade CategoriaAtendimento que possui o relacionamento
    // 'cascade = CascadeType.ALL' propaga operações (persistencia, remoção) para as categorias
    // 'orphanRemoval = true' garante que categorias órfãs (desvinculadas da empresa) sejam removidas
    @OneToMany(mappedBy = "empresa", cascade = [CascadeType.ALL], orphanRemoval = true)
    val categoriasAtendimento: MutableSet<CategoriaAtendimento> = mutableSetOf(),

// Relacionamento One-to-Many com TipoAtendimento
// Similar ao relacionamento com CategoriaAtendimento
    @OneToMany(mappedBy = "empresa", cascade = [CascadeType.ALL], orphanRemoval = true)
    val tiposAtendimento: MutableSet<TipoAtendimento> = mutableSetOf()
)
