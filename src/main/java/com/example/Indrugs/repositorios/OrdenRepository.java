package com.example.Indrugs.repositorios;

import com.example.Indrugs.entities.Orden;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrdenRepository extends JpaRepository<Orden, Long> {

        List<Orden> findByEstadoOrden(String estadoOrden);
        List<Orden> findTop4ByOrderByIdOrdenDesc();
        long countByEstadoOrden(String estadoOrden);
}
