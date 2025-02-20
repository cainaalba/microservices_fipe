package br.com.vda.fipe.model

import com.fasterxml.jackson.annotation.JsonProperty

class ConsultarMarcasModel {
    @JsonProperty("Label")
    var marca = ""

    @JsonProperty("Value")
    var codigo = 0

    override fun toString(): String {
        return "Marca: $marca -> Código: $codigo"
    }
}
