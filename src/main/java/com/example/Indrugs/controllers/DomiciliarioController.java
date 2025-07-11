package com.example.Indrugs.controllers;

import com.example.Indrugs.DTO.DomicilioDTO;
import com.example.Indrugs.DTO.Usuario.UsuarioDTO;
import com.example.Indrugs.DTO.Usuario.UsuarioUpdateDTO;
import com.example.Indrugs.entities.Usuario;
import com.example.Indrugs.mapper.UsuarioMapper;
import com.example.Indrugs.services.DomicilioService;
import com.example.Indrugs.services.UsuarioService;
import com.example.Indrugs.services.VehiculoService;
import com.example.Indrugs.services.VehiculoServiceImpl;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping
public class DomiciliarioController {

    private final UsuarioService usuarioService;
    private final DomicilioService domicilioService;
    private final VehiculoService vehiculoService;
    public DomiciliarioController(UsuarioService usuarioService, DomicilioService domicilioService,VehiculoService vehiculoService) {
        this.usuarioService = usuarioService;
        this.domicilioService = domicilioService;
        this.vehiculoService = vehiculoService;
    }

    @GetMapping("/11.pagina_principal_domiciliario")
    public String mostrarPaginaDomiciliario(HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");

        if (usuario == null) {
            return "redirect:/login"; // si no está logueado
        } Map<String, Object> dashboard = domicilioService.ObtenerResumen();

        model.addAttribute("totalDomiciliosActivos", dashboard.get("totalDomiciliosActivos"));
        model.addAttribute("domiciliosRecientes", dashboard.get("domiciliosRecientes"));

        return "domiciliario/11.pagina_principal_domiciliario";
    }

    @GetMapping("/12.pagina_ordenes_domiciliario")
    public String mostrarOrdenesDomiciliario(HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");

        if (usuario == null) {
            return "redirect:/login"; // si no está logueado
        }
        return "domiciliario/12.pagina_ordenes_domiciliario";
    }

    @GetMapping("/actualizar/domicilio/{idDomicilio}")
    public String cambiarEstado(@PathVariable Long idDomicilio) {
            domicilioService.actualizar(idDomicilio);
            return "redirect:/15.pagina_domicilio_domi";
    }
    @GetMapping("/15.pagina_domicilio_domi")
    public String Mostrartabla(HttpSession session,
                               Model model){
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");

        if (usuario == null) {
            return "redirect:/login"; // si no está logueado
        }
        List<DomicilioDTO> domicilio= domicilioService.read();
        model.addAttribute("domicilios", domicilio);
        return "domiciliario/15.pagina_domicilio_domi";
    }

//    @PostMapping("/actualizar/domicilio/{id}")
//    public String actualizarDomiciliario(@RequestParam Long idDomicilio,
//                                         RedirectAttributes redirectAttributes) {
//        try {
//            domicilioService.actualizar(idDomicilio);
//            redirectAttributes.addFlashAttribute("mensaje", "Domiciliario actualizado correctamente");
//        } catch (Exception e) {
//            redirectAttributes.addFlashAttribute("error", e.getMessage());
//        }
//        return "redirect:/15.pagina_domicilio_domi";
//    }
}
