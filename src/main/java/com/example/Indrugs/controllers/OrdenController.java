package com.example.Indrugs.controllers;

import com.example.Indrugs.DTO.MedicamentoDTO;
import com.example.Indrugs.DTO.OrdenDTO;
import com.example.Indrugs.entities.Medicamentos;
import com.example.Indrugs.entities.Orden;
import com.example.Indrugs.entities.Usuario;
import com.example.Indrugs.services.ArchivosService;
import com.example.Indrugs.services.MedicamentosService;
import com.example.Indrugs.services.OrdenService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Controller
public class OrdenController {

    @Autowired
    private OrdenService ordenService;
    @Autowired
    private MedicamentosService medicamentosService;
    @Autowired
    private ArchivosService archivosService;
    // Ruta adicional para acceso directo (redirecciona a domiciliario)
    @GetMapping("/14.pagina_ordenes")
    public String verOrdenesDirecto(Model model) {
        model.addAttribute("ordenes", ordenService.listarOrdenes());
        return "domiciliario/14.pagina_ordenes";
    }

    // Vista del domiciliario
    @GetMapping("/domiciliario/14.pagina_ordenes")
    public String verOrdenesDomiciliario(Model model) {
        model.addAttribute("ordenes", ordenService.listarOrdenes());
        return "domiciliario/14.pagina_ordenes";
    }

    // Vista del administrador
    @GetMapping("/18.pagina_orden_admin")
    public String verOrdenesAdmin(Model model) {
        model.addAttribute("ordenes", ordenService.listarOrdenes());
        return "administrador/18.pagina_orden_admin";
    }

    @GetMapping("/nuevo")
    public String mostrarformulario(@RequestParam("idMedicamento") Long idMedicamento,
                                    @RequestParam("cantidad") Integer cantidad,
                                    HttpSession session, Model model)
    {try {
        // Obtener usuario logueado usando tu método
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuario == null) {
            return "redirect:/login"; // Si no está logueado
        }


        // Obtener medicamento
        MedicamentoDTO medicamento = medicamentosService.buscarPorIdMedicamento(idMedicamento);

        if (medicamento == null) {
            model.addAttribute("error", "Medicamento no encontrado");
            return "error";
        }

        // Crear nueva orden DTO
        OrdenDTO ordenDTO = new OrdenDTO();
        ordenDTO.setPacienteNombre(usuario.getNombre());
        ordenDTO.setCantidad(cantidad);
        ordenDTO.setNombreMedicamento(medicamento.getNombreMedicamento());
        ordenDTO.setEstadoOrden("ACTIVO");

        // Agregar al modelo
        model.addAttribute("orden", ordenDTO);
        model.addAttribute("usuarioLogueado", usuario);
        model.addAttribute("medicamento", medicamento);
        model.addAttribute("idMedicamento", idMedicamento);
        model.addAttribute("cantidad", cantidad);

        return "pacientes/4.pagina_domicilio";

    } catch (Exception e) {
        model.addAttribute("error", "Error al cargar el formulario: " + e.getMessage());
        return "error";
    }
    }

    @PostMapping("/guardar")
    public String guardarOrden(@ModelAttribute OrdenDTO ordenDTO,
//                               @RequestParam("formulaFile") MultipartFile formulaFile,

                               @RequestParam("idMedicamento") Long idMedicamento,
                               HttpSession session, Model model) {
        try {
            // Verificar usuario logueado
            Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
            if (usuario == null) {
                return "redirect:/login";
            }

            // Obtener medicamento
            MedicamentoDTO medicamento = medicamentosService.buscarPorIdMedicamento(idMedicamento);

            if (medicamento == null) {
                model.addAttribute("error", "Medicamento no encontrado");
                return "error";
            }

            // Completar datos de la orden
            ordenDTO.setPacienteNombre(usuario.getNombre());
            ordenDTO.setNombreMedicamento(medicamento.getNombreMedicamento());
            ordenDTO.setEstadoOrden("ACTIVO");

            // Si no se especifica fecha, usar fecha actual + 1 día
            if (ordenDTO.getFechaEntrega() == null) {
                ordenDTO.setFechaEntrega(LocalDateTime.now().plusDays(1));
            }

            // Guardar orden
            ordenService.crear(ordenDTO,usuario.getIdUsuario(),idMedicamento);

            // Redirigir con mensaje de éxito
            return "redirect:/8.pagina_med";

        } catch (Exception e) {
            model.addAttribute("error", "Error al guardar la orden: " + e.getMessage());
            model.addAttribute("orden", ordenDTO);
            model.addAttribute("usuarioLogueado", session.getAttribute("usuarioLogueado"));
            return "pacientes/4.pagina_domicilio";
        }
    }

     // Marcar orden como entregada desde domiciliario
    @GetMapping("/domiciliario/ordenes/entregar/{id}")
    public String entregarOrdenDesdeDomiciliario(@PathVariable Long id) {
        ordenService.marcarComoEntregada(id);
        return "redirect:/domiciliario/14.pagina_ordenes";
    }

    // Marcar orden como entregada desde administrador
    @GetMapping("/administrador/ordenes/entregar/{id}")
    public String entregarOrdenDesdeAdmin(@PathVariable Long id) {
        ordenService.marcarComoEntregada(id);
        return "redirect:/administrador/18.pagina_orden_admin";
    }
}