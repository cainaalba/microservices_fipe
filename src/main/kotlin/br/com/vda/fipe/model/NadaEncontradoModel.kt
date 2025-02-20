package br.com.vda.fipe.model

import com.fasterxml.jackson.annotation.JsonProperty

data class NadaEncontradoModel(
    @JsonProperty("codigo")
    var codigo: String,

    @JsonProperty("erro")
    var erro: String
) {
    override fun toString(): String {
        return erro
    }
}
