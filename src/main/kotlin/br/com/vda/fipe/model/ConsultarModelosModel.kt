package br.com.vda.fipe.model

import com.fasterxml.jackson.annotation.JsonProperty

class ConsultarModelosModel {
    @JsonProperty("Label")
    var modelo = ""

    @JsonProperty("Value")
    var codigo = ""

    override fun toString(): String {
        return "Modelo: $modelo -> Código: $codigo"
    }
}