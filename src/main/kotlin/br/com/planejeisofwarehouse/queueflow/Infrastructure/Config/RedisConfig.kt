package br.com.planejeisofwarehouse.queueflow.Infrastructure.Config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext
import org.springframework.data.redis.serializer.StringRedisSerializer
import java.time.Duration

@Configuration
class RedisConfig {

    // Bean para o ObjectMapper, necessário para a serialização JSON
    @Bean
    fun objectMapper(): ObjectMapper {
        // Registra o KotlinModule para que o Jackson saiba como serializar/desserializar classes Kotlin
        return ObjectMapper().registerModule(KotlinModule.Builder().build())
        // Você pode adicionar outras configurações ao ObjectMapper aqui, se precisar
        // Por exemplo, para ignorar propriedades desconhecidas: .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    }

    // Bean que configura e fornece o RedisTemplate
    @Bean
    fun redisTemplate(
        redisConnectionFactory: RedisConnectionFactory,
        objectMapper: ObjectMapper // Injeta o ObjectMapper que definimos acima
    ): RedisTemplate<String, Any> { // Chave String, Valor Any (para objetos genéricos)
        val template = RedisTemplate<String, Any>()
        template.setConnectionFactory(redisConnectionFactory)

        // Serializador para as chaves do Redis (geralmente String)
        template.setKeySerializer(StringRedisSerializer())
        template.setHashKeySerializer(StringRedisSerializer())

        // Serializador para os valores do Redis (usando JSON)
        val jsonSerializer = GenericJackson2JsonRedisSerializer(objectMapper) // Usa nosso ObjectMapper
        template.setValueSerializer(jsonSerializer)
        template.setHashValueSerializer(jsonSerializer)

        template.afterPropertiesSet()
        return template
    }

    // Opcional: Configuração do cache do Spring usando Redis (se você usa @EnableCaching)
    @Bean
    fun cacheConfiguration(objectMapper: ObjectMapper): RedisCacheConfiguration {
        return RedisCacheConfiguration.defaultCacheConfig()
            .entryTtl(Duration.ofMinutes(1000)) // Tempo de vida padrão para itens no cache
            .disableCachingNullValues() // Não cacheia valores nulos
            // Serializa os valores do cache para JSON
            .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(GenericJackson2JsonRedisSerializer(objectMapper)))
    }
}