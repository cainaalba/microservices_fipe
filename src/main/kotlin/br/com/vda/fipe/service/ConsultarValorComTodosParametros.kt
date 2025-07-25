package br.com.vda.fipe.service

import br.com.vda.fipe.dto.ConsultaPorFipeDto
import br.com.vda.fipe.dto.TiposVeiculo
import br.com.vda.fipe.infra.exceptionhandler.NaoEncontradoException
import br.com.vda.fipe.infra.exceptionhandler.ValidacaoException
import com.fasterxml.jackson.databind.ObjectMapper
import br.com.vda.fipe.interfaces.MontarJsonInterface
import br.com.vda.fipe.interfaces.ProcessaErroInterface
import br.com.vda.fipe.interfaces.RequestInterface
import br.com.vda.fipe.model.ConsultarValorComTodosParametrosModel
import org.springframework.stereotype.Service
import java.util.*

@Service
class ConsultarValorComTodosParametros : RequestInterface, MontarJsonInterface, ProcessaErroInterface {
    fun consultar(
        json: Map<String, Any>,
        codigoAnoCombustivel: String
    ) {
        val scanner = Scanner(System.`in`)

        val anoCombustivelParse = codigoAnoCombustivel.split("-")
        val ano = anoCombustivelParse[0]
        val codigoCombustivel = anoCombustivelParse[1]

        val updaterJson = json.toMutableMap()
        updaterJson["anoModelo"] = ano
        updaterJson["ano"] = codigoAnoCombustivel
        updaterJson["codigoTipoCombustivel"] = codigoCombustivel
        updaterJson["tipoConsulta"] = "tradicional"
        val jsonString = montaJson(updaterJson)
        processarRequisicao(jsonString)
        scanner.close()
    }

    fun consultarPorCodigoFipe(
        dados: ConsultaPorFipeDto
    ): ConsultarValorComTodosParametrosModel {
        val tipoVeiculo = converteTipoVeiculo(dados.codigoTipoVeiculo)
        if (!TiposVeiculo.existeCodigo(tipoVeiculo)) {
            throw ValidacaoException(
                "Tipo do veículo [" + tipoVeiculo + "] inválido. Somente são aceitos os tipos:\n" +
                        "1 -> Carros e Utilitários\n" +
                        "2 -> Motos\n" +
                        "3 -> Caminhões e Micro-Ônibus"
            )
        }

        if (dados.codigoFipe.isBlank()) {
            throw ValidacaoException("Informe o código FIPE!")
        }

        if (dados.anoModelo.isBlank()) {
            throw ValidacaoException("Informe o ano do veículo!")
        }

        val consultarTabelaDeReferencia = ConsultarTabelaDeReferencia()
        val codigoTabelaDeReferencia = consultarTabelaDeReferencia.consultar()
        val json = mapOf(
            "codigoTabelaReferencia" to codigoTabelaDeReferencia,
            "codigoTipoVeiculo" to tipoVeiculo,
            "anoModelo" to dados.anoModelo.toInt(),
            "modeloCodigoExterno" to formataCodigoFipe(dados.codigoFipe),
            "tipoConsulta" to "codigo"
        )

        val jsonString = montaJson(json)
        println(jsonString)
        return processarRequisicao(jsonString)
    }

    private fun formataCodigoFipe(codigoFipe: String): String {
        if (codigoFipe.length < 2 || codigoFipe.contains("-")) return codigoFipe;

        val parte1 = codigoFipe.dropLast(1)
        val parte2 = codigoFipe.takeLast(1)

        return "$parte1-$parte2"
    }

    private fun converteTipoVeiculo(tipoVeiculo: Int): Int {
        return when (tipoVeiculo) {
            6, 7, 8, 11, 13, 27 -> 1 //CARROS
            else -> {
                3 //CAMINHOES E RESTO?
            }
        }
    }

    private fun processarRequisicao(jsonString: String): ConsultarValorComTodosParametrosModel {
        val endPoint =
            "http://veiculos.fipe.org.br/api/veiculos/ConsultarValorComTodosParametros"
        val responseBody = request(jsonString, endPoint)

        val resultado = runCatching {
            val consultarValorComTodosParametrosModel =
                ObjectMapper().readValue(responseBody, ConsultarValorComTodosParametrosModel::class.java)
            consultarValorComTodosParametrosModel
        }

        return resultado.getOrElse {
            throw NaoEncontradoException()
        }
    }
}