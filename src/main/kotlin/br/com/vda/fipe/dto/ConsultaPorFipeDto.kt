package br.com.vda.fipe.dto

data class ConsultaPorFipeDto(
    val codigoTabelaReferencia: Int,
    val codigoTipoVeiculo: Int,
    val codigoFipe: String,
    val anoModelo: String
)