package br.com.vda.fipe.dto

enum class TiposVeiculo(val codigo: Int) {
    CARROS_UTILITARIOS(1),
    MOTOS(2),
    CAMINHOES_MICRO_ONIBUS(3);

    companion object {
        fun existeCodigo(codigo: Int): Boolean {
            return entries.any{it.codigo == codigo}
        }
    }
}