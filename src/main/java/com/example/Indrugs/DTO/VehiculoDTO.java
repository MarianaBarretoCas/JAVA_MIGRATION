package com.example.Indrugs.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VehiculoDTO {
    private Long idVehiculo;
    private Long idPropietario;
    private String tipoVehiculo;
    private String placaVehiculo;
    private String marcaVehiculo;
    private String colorVehiculo;
    private String estadoVehiculo;
}