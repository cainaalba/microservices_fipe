package br.com.vda.fipe.repo

import br.com.vda.fipe.model.VeiculosModel
import org.springframework.data.jpa.repository.JpaRepository

interface VeiculosRepo : JpaRepository<VeiculosModel, Int>
