package com.example.Indrugs.repositorios;

import com.example.Indrugs.entities.Medicamentos;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicamentoRepository extends JpaRepository<Medicamentos, Long> {
}
