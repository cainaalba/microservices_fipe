package br.com.vda.fipe.service

import br.com.vda.fipe.dto.ConsultaPorFipeDto
import br.com.vda.fipe.model.VeiculosModel
import br.com.vda.fipe.repo.VeiculosRepo
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import lombok.RequiredArgsConstructor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
@RequiredArgsConstructor
class AtualizacaoAssincronaService {
    @Autowired
    lateinit var veiculosRepo: VeiculosRepo

    @Autowired
    var consultarValorComTodosParametros = ConsultarValorComTodosParametros()

    @Scheduled(cron = "0 0 1 2 * *")
//    @Scheduled(initialDelay = 3000)
    fun atualizar() = runBlocking {
        println("Iniciando atualizações em ${LocalDateTime.now()}")

        val veiculos = veiculosRepo.findAll()
        veiculos.forEach {
//            val veiculo = veiculosRepo.getReferenceById(it.id)
            println("ID: " + it.id.toString() + " - Código fipe: " + it.codigoFipe)

            val valorFipe = buscaValorFipe(it)
            if (valorFipe != 0.0) {
                println("Valor: $valorFipe")
                it.atualizaValor(valorFipe)
                veiculosRepo.save(it) //USO EXPLÍCITO, SEM @Transactional
                veiculosRepo.flush()
            }
            delay(3000)
        }
//        cancel()
    }

    private fun buscaValorFipe(veiculo: VeiculosModel): Double {
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
            println(it.message ?: "Veículo não encontrado! Verifique o código Fipe...")
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
