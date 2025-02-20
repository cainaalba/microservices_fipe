import br.com.vda.fipe.dto.ConsultaPorFipeDto
import br.com.vda.fipe.service.ConsultarMarcas
import br.com.vda.fipe.service.ConsultarTabelaDeReferencia
import br.com.vda.fipe.service.ConsultarValorComTodosParametros
import java.util.*
import kotlin.collections.List
import kotlin.system.exitProcess

fun tipoVeiculo(codigoTabelaReferencia: Int) {
    val scanner = Scanner(System.`in`)
    when (val codigoTipoVeiculo = informarTipoVeiculo()) {
        1, 2, 3 -> {
            println("\nDeseja buscar por código FIPE? (S/N)")
            val pergunta = scanner.nextLine()

            if (pergunta.equals("s", true)) {
                consultarPorCodigoFipe(
                    codigoTabelaReferencia,
                    codigoTipoVeiculo
                )
            } else {
                val consultarMarcas = ConsultarMarcas()
                consultarMarcas.consultar(
                    codigoTabelaReferencia,
                    codigoTipoVeiculo
                )
            }
        }

        else -> {
            println("\nTipo de veículo inválido!")
            tipoVeiculo(codigoTabelaReferencia)
        }
    }
    scanner.close()
}

private fun informarTipoVeiculo(): Int {
    val scanner = Scanner(System.`in`)

    println("\nDigite o tipo do veículo que deseja buscar:")
    println(
        " 1 -> Carros e Utilitários\n" +
                " 2 -> Motos\n" +
                " 3 -> Caminhões e Micro-Ônibus"
    )
    val codigoTipoVeiculo = scanner.nextLine()
    return codigoTipoVeiculo.toInt()
}

fun consultarPorCodigoFipe(codigoTabelaReferencia: Int, codigoTipoVeiculo: Int) {
    val consultarPorCodigoFipe = ConsultarValorComTodosParametros()
    consultarPorCodigoFipe.consultarPorCodigoFipe(
        ConsultaPorFipeDto(
            codigoTabelaReferencia,
            codigoTipoVeiculo,
            "",
            ""
        )
    )
}

fun continuarConsulta() {
    val scanner = Scanner(System.`in`)

    println("\nConsultar outro veículo? (S/N)")
    val entrada = scanner.nextLine()
    if (entrada.equals("S", true)) {
        val consultarTabelaDeReferencia = ConsultarTabelaDeReferencia()
        consultarTabelaDeReferencia.consultar()
    } else {
        exitProcess(0)
    }
    scanner.close()
}
