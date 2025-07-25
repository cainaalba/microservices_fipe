package br.com.vda.fipe.dto

data class ConsultaPorFipeDto(
    var codigoTabelaReferencia: Int?,
    val codigoTipoVeiculo: Int,
    val codigoFipe: String,
    val anoModelo: String
)