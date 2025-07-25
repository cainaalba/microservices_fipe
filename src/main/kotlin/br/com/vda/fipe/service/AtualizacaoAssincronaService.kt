package br.com.vda.fipe.service

import br.com.vda.fipe.dto.ConsultaPorFipeDto
import br.com.vda.fipe.model.VeiculosModel
import br.com.vda.fipe.repo.VeiculosRepo
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import lombok.RequiredArgsConstructor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Async
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.text.NumberFormat
import java.util.*

@Component
@RequiredArgsConstructor
class AtualizacaoAssincronaService {
    @Autowired
    lateinit var veiculosRepo: VeiculosRepo

    @Autowired
    var consultarValorComTodosParametros = ConsultarValorComTodosParametros()

    @Async
//    @Scheduled(cron = "* * * 1 * *")
    @Scheduled(fixedRate = 120000, initialDelay = 3000)
    fun atualizar() = runBlocking {
        println("Iniciando atualização...")

        val veiculos = veiculosRepo.findAll()
        veiculos.forEach {
            val veiculo = veiculosRepo.getReferenceById(it.id)
            println("Código fipe: " + veiculo.codigoFipe)

            val valorFipe = buscaValorFipe(veiculo)
            if (valorFipe != 0.0) {
//                veiculo.atualizaValor(valorFipe)
                println("Valor: $valorFipe")
            }
            delay(3000)
        }
    }

    fun buscaValorFipe(veiculo: VeiculosModel): Double {
        val dadosConsulta = ConsultaPorFipeDto(
            null,
            veiculo.tipoVeiculo,
            veiculo.codigoFipe,
            veiculo.anoModelo
        )

        val resultado = runCatching {
            val fipe = consultarValorComTodosParametros.consultarPorCodigoFipe(dadosConsulta)
            println(fipe.valor)
            formataValor(fipe.valor)
        }

        return resultado.getOrElse {
            println(it.message ?: "Veículo não encontrado...")
            0.0
        }
    }

    private fun formataValor(valorFipe: String): Double {
        return valorFipe
            .replace("R$", "")
            .replace(" ", "")
            .replace(".", "")
            .replace(",", ".")
            .toDouble()
    }
}
