package br.com.vda.fipe.service

import br.com.vda.fipe.interfaces.MontarJsonInterface
import br.com.vda.fipe.interfaces.ProcessaErroInterface
import br.com.vda.fipe.interfaces.RequestInterface
import com.fasterxml.jackson.databind.ObjectMapper
import br.com.vda.fipe.model.ConsultarMarcasModel
import java.util.Scanner

class ConsultarMarcas : RequestInterface, MontarJsonInterface, ProcessaErroInterface {
    fun consultar(mesReferenciaEntrada: Int, tipoVeiculoEntrada: Int) {
        val scanner = Scanner(System.`in`)

        val endPoint = "http://veiculos.fipe.org.br/api/veiculos/ConsultarMarcas"

        val json = mapOf(
            "codigoTabelaReferencia" to mesReferenciaEntrada,
            "codigoTipoVeiculo" to tipoVeiculoEntrada
        )
        val jsonString = montaJson(json)
        val responseBody = request(jsonString, endPoint)

        val resultado = runCatching {
            val marcas: Array<ConsultarMarcasModel> = ObjectMapper().readValue(responseBody, Array<ConsultarMarcasModel>::class.java)
            for (marca in marcas) {
                println(marca.toString())
            }

            println("\nDigite o código da marca:")
            val codigoMarca = scanner.nextLine().toInt()

            println("\nPara continuar, selecione uma das opções abaixo:")
            println(
                "1 -> Buscar todos os modelos da marca Marca\n" +
                        "2 -> Buscar os modelos por Ano/Modelo"
            )
            val opcao = scanner.nextLine().toInt()

            when (opcao) {
                1 -> {
                    val consultarModelos = ConsultarModelos()
                    consultarModelos.consultar(json, codigoMarca)
                }

                2 -> {
                    println("\nDigite o ano:")
                    val ano = scanner.nextLine().toInt()
                    val consultarModelosAtravesDoAno = ConsultarModelosAtravesDoAno()
                    consultarModelosAtravesDoAno.consultar(json, codigoMarca, ano)
                }

                else -> {
                    println("\nOpção inválida. Tente novamente")
                    consultar(mesReferenciaEntrada, tipoVeiculoEntrada)
                }
            }
        }

        resultado.onFailure {
            processar(responseBody)
        }

        scanner.close()
    }
}