package br.com.vda.fipe.infra.exceptionhandler

class ValidacaoException(message: String?) : RuntimeException(message) {
    override fun toString(): String {
        val mensagem = message

        return if (mensagem != null) {
            message!!
        } else {
            super.toString()
        }
    }
}
