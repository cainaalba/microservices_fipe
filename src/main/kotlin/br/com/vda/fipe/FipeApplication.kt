package br.com.vda.fipe

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class FipeApplication

fun main(args: Array<String>) {
    runApplication<FipeApplication>(*args)
}
