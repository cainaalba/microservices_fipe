package br.com.vda.fipe.service

import br.com.vda.fipe.interfaces.MontarJsonInterface
import br.com.vda.fipe.interfaces.RequestInterface
import com.fasterxml.jackson.databind.ObjectMapper
import br.com.vda.fipe.interfaces.ProcessaErroInterface
import br.com.vda.fipe.model.ConsultarModelosPorModeloModel
import java.util.*

class ConsultarModelos : RequestInterface, MontarJsonInterface, ProcessaErroInterface {
    fun consultar(json: Map<String, Any>, codigoMarca: Int) {
        val scanner = Scanner(System.`in`)

        val updaterJson = json.toMutableMap()
        updaterJson["codigoMarca"] = codigoMarca
        val jsonString = montaJson(updaterJson)

        val endPoint = "http://veiculos.fipe.org.br/api/veiculos/ConsultarModelos"
        val responseBody = request(jsonString, endPoint)

        val resultado = runCatching {
            val modelos: ConsultarModelosPorModeloModel = ObjectMapper().readValue(responseBody, ConsultarModelosPorModeloModel::class.java)
            println(modelos.toString())

            println("\nDigite o código do modelo:")
            val codigoModelo = scanner.nextLine().toInt()
            val consultarAnoModelo = ConsultarAnoModelo()
            consultarAnoModelo.consultar(updaterJson, codigoModelo)
        }

        resultado.onFailure {
            processar(responseBody)
        }

        scanner.close()
    }
}