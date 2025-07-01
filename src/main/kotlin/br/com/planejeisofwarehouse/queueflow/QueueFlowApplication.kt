package br.com.planejeisofwarehouse.queueflow

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class QueueFlowApplication

fun main(args: Array<String>) {
    runApplication<QueueFlowApplication>(*args)
}
