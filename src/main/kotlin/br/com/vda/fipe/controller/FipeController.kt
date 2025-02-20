package br.com.vda.fipe.controller

import br.com.vda.fipe.dto.ConsultaPorFipeDto
import br.com.vda.fipe.model.ConsultarValorComTodosParametrosModel
import br.com.vda.fipe.service.ConsultarValorComTodosParametros
import lombok.RequiredArgsConstructor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/fipe")
@RequiredArgsConstructor
class FipeController {

    @Autowired
    var consultarValorComTodosParametros = ConsultarValorComTodosParametros();

    @PostMapping("/codigo")
    fun consultarPorCodigoFipe(@RequestBody dados: ConsultaPorFipeDto): ResponseEntity<ConsultarValorComTodosParametrosModel> {
        val fipe = consultarValorComTodosParametros.consultarPorCodigoFipe(dados = dados)
        return ResponseEntity.status(HttpStatus.OK).body(fipe)
    }
}