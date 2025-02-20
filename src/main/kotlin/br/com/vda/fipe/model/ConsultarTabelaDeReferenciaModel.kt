package br.com.vda.fipe.model

import com.fasterxml.jackson.annotation.JsonProperty
import lombok.Getter
import lombok.Setter

@Getter
@Setter
class ConsultarTabelaDeReferenciaModel {
    @JsonProperty("Codigo")
    var codigo = 0

    @JsonProperty("Mes")
    var mes = ""

    override fun toString(): String {
        return "Mês: ${mes.trim()} -> Código: $codigo"
    }
}