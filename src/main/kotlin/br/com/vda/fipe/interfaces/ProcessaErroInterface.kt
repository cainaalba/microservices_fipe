package br.com.vda.fipe.interfaces

import br.com.vda.fipe.infra.exceptionhandler.ValidacaoException
import br.com.vda.fipe.model.NadaEncontradoModel
import com.fasterxml.jackson.databind.ObjectMapper

interface ProcessaErroInterface {
    fun processar(responseBody: String?) {
        val resultado = runCatching {
//            val erro: NadaEncontradoModel = ObjectMapper().readValue(responseBody, NadaEncontradoModel::class.java)
            throw ValidacaoException(responseBody)
        }

        resultado.onFailure {
            val erro: NadaEncontradoModel = ObjectMapper().readValue(responseBody, NadaEncontradoModel::class.java)
            throw ValidacaoException(erro.toString())
        }
    }
}