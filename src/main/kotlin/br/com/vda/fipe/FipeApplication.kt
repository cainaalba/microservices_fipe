package br.com.vda.fipe

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class FipeApplication

fun main(args: Array<String>) {
	runApplication<FipeApplication>(*args)
}
