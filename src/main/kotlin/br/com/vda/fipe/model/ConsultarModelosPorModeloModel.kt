package br.com.vda.fipe.model

import com.fasterxml.jackson.annotation.JsonProperty

class ConsultarModelosPorModeloModel(
    @JsonProperty("Modelos") val modelos: List<ConsultarModelosModel>,
    @JsonProperty("Anos") val anosModelos: List<ConsultarModelosModel>
) {
    override fun toString(): String {
        return modelos.toString()
            .replace(", ","\n")
            .replace("[", "")
            .replace("]", "")

    }
}