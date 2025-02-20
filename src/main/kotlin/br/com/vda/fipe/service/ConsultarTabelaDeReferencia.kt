package br.com.vda.fipe.service

import br.com.vda.fipe.interfaces.RequestInterface
import com.fasterxml.jackson.databind.ObjectMapper
import br.com.vda.fipe.interfaces.ProcessaErroInterface
import br.com.vda.fipe.model.ConsultarTabelaDeReferenciaModel
import okhttp3.*
import okhttp3.internal.EMPTY_REQUEST
import tipoVeiculo
import java.util.*

class ConsultarTabelaDeReferencia : RequestInterface, ProcessaErroInterface {
    fun consultar() {
        val scanner = Scanner(System.`in`)

        val endPoint = "http://veiculos.fipe.org.br/api/veiculos/ConsultarTabelaDeReferencia"
        val requestBody: RequestBody = EMPTY_REQUEST
        val responseBody = request(requestBody.toString(), endPoint)

        val resultado = runCatching {
            val mesReferencia: Array<ConsultarTabelaDeReferenciaModel> =
                ObjectMapper().readValue(responseBody, Array<ConsultarTabelaDeReferenciaModel>::class.java)
            for (mes in mesReferencia.indices.reversed()) {
                println(mesReferencia[mes].toString())
            }

            println("\nDigite o código do mês de referência para buscar:")
            val codigoTabelaReferencia = scanner.nextLine().toInt()
            tipoVeiculo(codigoTabelaReferencia)
        }

        resultado.onFailure {
            processar(responseBody)
        }

        scanner.close()
    }
}