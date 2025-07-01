package br.com.planejeisofwarehouse.queueflow.Domain.Model

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(name = "categoria_atendimento", schema = "queue_flow")
data class CategoriaAtendimento(

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    val id: UUID? = null,

    // Descrição da categoria de atendimento
    @Column(name = "descricao", nullable = false)
    val descricao: String,

    // Status de ativação da categoria
    @Column(name = "is_ativo", nullable = false)
    val isAtivo: Boolean = false,

    // Relacionamento Many-to-One com Empresa
    // 'joinColumn' especifica a coluna de chave estrangeira no banco de dados
    // 'nullable = false' reflete o 'NOT NULL' do SQL
    @ManyToOne(fetch = FetchType.LAZY) // LAZY para carregamento sob demanda
    @JoinColumn(name = "empresa_id", nullable = false)
    val empresa: Empresa,

    // Relacionamento One-to-Many com TipoAtendimento
    @OneToMany(mappedBy = "categoriaAtendimento", cascade = [CascadeType.ALL], orphanRemoval = true)
    val tiposAtendimento: MutableSet<TipoAtendimento> = mutableSetOf()
)
