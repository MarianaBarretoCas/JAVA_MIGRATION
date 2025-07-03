package com.example.Indrugs.repositorios;


import com.example.Indrugs.entities.Orden;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdenRepository extends JpaRepository<Orden, Long> {
}
