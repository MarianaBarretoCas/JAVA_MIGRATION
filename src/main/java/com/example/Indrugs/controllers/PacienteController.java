
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

@Controller
@RequestMapping
public class PacienteController {

    private final UsuarioService usuarioService;

    public PacienteController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/13.pagina_principal_Paciente")
    public String mostrarPaginaPaciente(Model model) {
        // Puedes agregar datos relevantes para el paciente aquí
        // Ejemplo: medicamentos asignados, citas próximas, etc.
        // model.addAttribute("medicamentosAsignados", ...);
        // model.addAttribute("citasProximas", ...);
        return "pacientes/13.pagina_principal_paciente";
    }

    @GetMapping("/14.pagina_ordenes_Paciente")
    public String mostrarOrdenesPaciente(Model model) {
        // Aquí podrías obtener las órdenes del paciente
        // List<OrdenDTO> ordenes = ordenService.obtenerOrdenesPorPaciente(...);
        // model.addAttribute("ordenes", ordenes);
        return "pacientes/14.pagina_ordenes_paciente";
    }

    @GetMapping("/actualizar_Paciente")
    public String mostrarFormularioEdicion(@RequestParam Long idUsuario, Model model) {
        try {
            UsuarioDTO usuario = usuarioService.findById(idUsuario);
            UsuarioUpdateDTO usuarioUpdate = UsuarioMapper.toUpdateDTO(usuario);
            model.addAttribute("usuario", usuarioUpdate);
            model.addAttribute("estados", List.of("ACTIVO", "INACTIVO"));
            return "pacientes/actualizar_paciente";
        } catch (Exception e) {
            return "redirect:/1.pagina_principal_paciente?error=Usuario no encontrado";
        }
    }

    @PostMapping("/actualizar_Paciente")
    public String actualizarPaciente(@RequestParam Long idUsuario,
                                     UsuarioUpdateDTO userUpdate,
                                     RedirectAttributes redirectAttributes) {
        try {
            usuarioService.actualizar(idUsuario, userUpdate);
            redirectAttributes.addFlashAttribute("mensaje", "Paciente actualizado correctamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/1.pagina_principal_paciente";
    }
}
