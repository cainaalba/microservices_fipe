package br.com.vda.fipe.model

import com.fasterxml.jackson.annotation.JsonProperty

data class ConsultarValorComTodosParametrosModel(
    @JsonProperty("Valor")
    var valor: String,

    @JsonProperty("Marca")
    var marca: String,

    @JsonProperty("Modelo")
    var modelo: String,

    @JsonProperty("AnoModelo")
    var anoModelo: String,

    @JsonProperty("Combustivel")
    var combustivel: String,

    @JsonProperty("CodigoFipe")
    var codigoFipe: String,

    @JsonProperty("MesReferencia")
    var mesReferencia: String,

    @JsonProperty("Autenticacao")
    var autenticacao: String,

    @JsonProperty("TipoVeiculo")
    var tipoVeiculo: String,

    @JsonProperty("SiglaCombustivel")
    var siglaCombustivel: String,

    @JsonProperty("DataConsulta")
    var dataConsulta: String
)