package br.com.vda.fipe.interfaces

import com.fasterxml.jackson.databind.ObjectMapper

interface MontarJsonInterface {
    fun montaJson(json: Map<String, Any>): String {
        val objectMapper = ObjectMapper()
        return objectMapper.writeValueAsString(json)
    }
}