package com.example.Indrugs.controllers;

import com.example.Indrugs.DTO.Usuario.UsuarioDTO;
import com.example.Indrugs.DTO.Usuario.UsuarioUpdateDTO;
import com.example.Indrugs.mapper.UsuarioMapper;
import com.example.Indrugs.services.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping
public class DomiciliarioController {

    private final UsuarioService usuarioService;

    public DomiciliarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/11.pagina_principal_domiciliario")
    public String mostrarPaginaDomiciliario(Model model) {
        // Puedes agregar estadísticas o datos relevantes para el domiciliario aquí
        // Ejemplo: pedidos asignados, entregas realizadas, etc.
        // model.addAttribute("pedidosAsignados", ...);
        // model.addAttribute("entregasRealizadas", ...);
        return "domiciliario/11.pagina_principal_domiciliario";
    }

    @GetMapping("/12.pagina_ordenes_domiciliario")
    public String mostrarOrdenesDomiciliario(Model model) {
        // Aquí podrías obtener las órdenes asignadas al domiciliario
        // List<OrdenDTO> ordenes = ordenService.obtenerOrdenesPorDomiciliario(...);
        // model.addAttribute("ordenes", ordenes);
        return "domiciliario/12.pagina_ordenes_domiciliario";
    }

    @GetMapping("/actualizar_domiciliario")
    public String mostrarFormularioEdicion(@RequestParam Long idUsuario, Model model) {
        try {
            UsuarioDTO usuario = usuarioService.findById(idUsuario);
            UsuarioUpdateDTO usuarioUpdate = UsuarioMapper.toUpdateDTO(usuario);
            model.addAttribute("usuario", usuarioUpdate);
            model.addAttribute("estados", List.of("ACTIVO", "INACTIVO"));
            return "domiciliario/actualizar_domiciliario";
        } catch (Exception e) {
            return "redirect:/11.pagina_principal_domiciliario?error=Usuario no encontrado";
        }
    }

    @PostMapping("/actualizar_domiciliario")
    public String actualizarDomiciliario(@RequestParam Long idUsuario,
                                         UsuarioUpdateDTO userUpdate,
                                         RedirectAttributes redirectAttributes) {
        try {
            usuarioService.actualizar(idUsuario, userUpdate);
            redirectAttributes.addFlashAttribute("mensaje", "Domiciliario actualizado correctamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/11.pagina_principal_domiciliario";
    }
}
