package com.example.Indrugs.repositorios;

import com.example.Indrugs.entities.Inventario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventarioRepository extends JpaRepository<Inventario, Long> {
}
