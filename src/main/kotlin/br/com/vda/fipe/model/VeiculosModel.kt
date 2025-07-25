package br.com.vda.fipe.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import lombok.AllArgsConstructor
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter
import org.hibernate.annotations.SQLRestriction
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Entity
@Table(name = "FRO_VEICULOS")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SQLRestriction(
    "STATUS = 'A' " +
            "AND FIPE_CODIGO NOT IN ('0','1','2')"
)
class VeiculosModel {
    @Id
    @Column(name = "ID")
    var id: Int = 0

    @Column(name = "PLACA")
    var placa: String = ""

    @Column(name = "PLACA2")
    var placa2: String = ""

    @Column(name = "FIPE_CODIGO")
    var codigoFipe: String = ""

    @Column(name = "FIPE_DT_ATUALIZACAO")
    var dataAtualizacaoFipe: String? = null

    @Column(name = "TIPO_COMBUSTIVEL")
    var tipoCombustivel: Int = 0

    @Column(name = "FIPE_VALOR")
    var valorFipe: Double = 0.0

    @Column(name = "TIPO_VEICULO")
    var tipoVeiculo: Int = 0

    @Column(name = "ANO_MODELO")
    var anoModelo: String = ""

    fun atualizaValor(valorFipe: Double) {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

        this.valorFipe = valorFipe
        this.dataAtualizacaoFipe = LocalDate.now().format(formatter)
    }
}
