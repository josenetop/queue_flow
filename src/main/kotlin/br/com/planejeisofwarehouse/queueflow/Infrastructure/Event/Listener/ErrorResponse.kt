package br.com.planejeisofwarehouse.queueflow.Infrastructure.Event.Listener

import java.time.LocalDateTime

data class ErrorResponse(
    val timestamp: LocalDateTime,
    val status: Int,
    val error: String,
    val message: String,
    val path: String,
)
