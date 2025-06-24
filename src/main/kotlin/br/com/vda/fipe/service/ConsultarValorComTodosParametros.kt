package br.com.vda.fipe.service

import br.com.vda.fipe.dto.ConsultaPorFipeDto
import br.com.vda.fipe.dto.TiposVeiculo
import br.com.vda.fipe.infra.exceptionhandler.NaoEncontradoException
import br.com.vda.fipe.infra.exceptionhandler.ValidacaoException
import com.fasterxml.jackson.databind.ObjectMapper
import continuarConsulta
import br.com.vda.fipe.interfaces.MontarJsonInterface
import br.com.vda.fipe.interfaces.ProcessaErroInterface
import br.com.vda.fipe.interfaces.RequestInterface
import br.com.vda.fipe.model.ConsultarValorComTodosParametrosModel
import br.com.vda.fipe.model.NadaEncontradoModel
import org.springframework.stereotype.Service
import java.util.*
import kotlin.collections.ArrayList
import kotlin.reflect.jvm.internal.ReflectProperties.Val

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
        val consultarTabelaDeReferencia = ConsultarTabelaDeReferencia()
        if (!TiposVeiculo.existeCodigo(dados.codigoTipoVeiculo)) {
            throw ValidacaoException(
                "Tipo do veículo inválido. Somente são aceitos os tipos:\n" +
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

        val codigoTabelaDeReferencia = consultarTabelaDeReferencia.consultar()
        val json = mapOf(
            "codigoTabelaReferencia" to codigoTabelaDeReferencia,
            "codigoTipoVeiculo" to dados.codigoTipoVeiculo,
            "anoModelo" to dados.anoModelo.toInt(),
            "modeloCodigoExterno" to dados.codigoFipe,
            "tipoConsulta" to "codigo"
        )

        val jsonString = montaJson(json)
        return processarRequisicao(jsonString)
    }

    private fun processarRequisicao(jsonString: String): ConsultarValorComTodosParametrosModel {
        val endPoint =
            "http://veiculos.fipe.org.br/api/veiculos/ConsultarValorComTodosParametros"
        val responseBody = request(jsonString, endPoint)

        val resultado = runCatching {
            val consultarValorComTodosParametrosModel =
                ObjectMapper().readValue(responseBody, ConsultarValorComTodosParametrosModel::class.java)

            return consultarValorComTodosParametrosModel
        }

        resultado.onFailure {
            throw NaoEncontradoException()
        }

        throw ValidacaoException("Erro inesperado ao buscar. Tente novamente!")
    }
}