package br.com.vda.fipe.service

import br.com.vda.fipe.interfaces.MontarJsonInterface
import br.com.vda.fipe.interfaces.RequestInterface
import com.fasterxml.jackson.databind.ObjectMapper
import br.com.vda.fipe.interfaces.ProcessaErroInterface
import br.com.vda.fipe.model.ConsultarAnoModeloModel
import java.util.*

class ConsultarAnoModelo : RequestInterface, MontarJsonInterface, ProcessaErroInterface {
    fun consultar(json: Map<String, Any>, codigoModelo: Int) {
        val scanner = Scanner(System.`in`)

        val updaterJson = json.toMutableMap()
        updaterJson["codigoModelo"] = codigoModelo
        val jsonString = montaJson(updaterJson)

        val endPoint = "http://veiculos.fipe.org.br/api/veiculos/ConsultarAnoModelo"
        val responseBody = request(jsonString, endPoint)

        val resultado = runCatching {
            val anosCombustivel: Array<ConsultarAnoModeloModel> =
                ObjectMapper().readValue(responseBody, Array<ConsultarAnoModeloModel>::class.java)

            for (anoCombustivel in anosCombustivel) {
                println(anoCombustivel.toString())
            }

            println("\nDigite o ano-combustivel (Ex: 2010-1, 2011-2) para buscar:")
            val anoCombustivel = scanner.nextLine()

            val anoCombustivelEncontrado = buscarAnoModelo(anosCombustivel, anoCombustivel)
            if (anoCombustivelEncontrado != null) {
                val consultarValorComTodosParametros = ConsultarValorComTodosParametros()
                consultarValorComTodosParametros.consultar(updaterJson, anoCombustivelEncontrado.anoCombustivel)
            } else {
                println("Ano-Combustível informado inválido. Tente novamente!\n")
                consultar(json, codigoModelo)
            }
        }

        resultado.onFailure {
            processar(responseBody)
        }

        scanner.close()
    }

    private fun buscarAnoModelo(
        anosModeloModels: Array<ConsultarAnoModeloModel>,
        anoCombustivel: String
    ): ConsultarAnoModeloModel? {
        return anosModeloModels.find { it.anoCombustivel == anoCombustivel }
    }
}