package com.example.Indrugs.services;

import com.example.Indrugs.DTO.VehiculoDTO;
import java.util.List;

public interface VehiculoService {
    List<VehiculoDTO> read();
    void crear(VehiculoDTO vehiculoDTO);
    void actualizar(Long idVehiculo, VehiculoDTO vehiculoDTO);
    void eliminar(Long idVehiculo);
    VehiculoDTO findById(Long idVehiculo);
}
