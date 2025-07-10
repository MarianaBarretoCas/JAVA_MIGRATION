package com.example.Indrugs.controllers;

import com.example.Indrugs.DTO.VehiculoDTO;
import com.example.Indrugs.services.VehiculoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping
public class VehiculoController {

    private final VehiculoService vehiculoService;

    public VehiculoController(VehiculoService vehiculoService) {
        this.vehiculoService = vehiculoService;
    }

    @GetMapping("/12.pagina_vehiculos")
    public String mostrarPaginaVehiculos(Model model) {
        List<VehiculoDTO> vehiculos = vehiculoService.read();
        model.addAttribute("vehiculos", vehiculos);
        return "administrador/12.pagina_vehiculos";
    }

    @GetMapping("/agregar_vehiculo")
    public String mostrarFormularioAgregar(Model model) {
        model.addAttribute("vehiculo", new VehiculoDTO());
        return "administrador/agregar_vehiculo";
    }

    @PostMapping("/agregar_vehiculo")
    public String agregarVehiculo(VehiculoDTO vehiculoDTO, RedirectAttributes redirectAttributes) {
        try {
            vehiculoService.crear(vehiculoDTO);
            redirectAttributes.addFlashAttribute("mensaje", "Vehículo agregado correctamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/12.pagina_vehiculos";
    }

    @GetMapping("/actualizar_vehiculo")
    public String mostrarFormularioEdicion(@RequestParam Long idVehiculo, Model model) {
        try {
            VehiculoDTO vehiculo = vehiculoService.findById(idVehiculo);
            model.addAttribute("vehiculo", vehiculo);
            return "administrador/actualizar_vehiculo";
        } catch (Exception e) {
            return "redirect:/12.pagina_vehiculos?error=Vehículo no encontrado";
        }
    }

    @PostMapping("/actualizar_vehiculo")
    public String actualizarVehiculo(@RequestParam Long idVehiculo,
                                     VehiculoDTO vehiculoDTO,
                                     RedirectAttributes redirectAttributes) {
        try {
            vehiculoService.actualizar(idVehiculo, vehiculoDTO);
            redirectAttributes.addFlashAttribute("mensaje", "Vehículo actualizado correctamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/12.pagina_vehiculos";
    }

    @PostMapping("/eliminar_vehiculo")
    public String eliminarVehiculo(@RequestParam Long idVehiculo, RedirectAttributes redirectAttributes) {
        try {
            vehiculoService.eliminar(idVehiculo);
            redirectAttributes.addFlashAttribute("mensaje", "Vehículo eliminado correctamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/12.pagina_vehiculos";
    }
}
