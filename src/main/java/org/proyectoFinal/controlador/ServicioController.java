package org.proyectoFinal.controlador;

import java.util.Optional;

import org.proyectoFinal.entidad.Servicio;
import org.proyectoFinal.servicio.ServicioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/servicio")
public class ServicioController {

    @Autowired
    private ServicioServicio servicioServicio;

    // 1) Listar servicios y preparar el formulario
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("servicios", servicioServicio.listarTodos());
        model.addAttribute("servicio", new Servicio());
        return "servicio";
    }

    // 2) Guardar nuevo servicio
    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Servicio servicio, RedirectAttributes redirect) {
        try {
            servicioServicio.guardar(servicio);
            redirect.addFlashAttribute("success", "Servicio guardado correctamente");
        } catch (Exception e) {
            redirect.addFlashAttribute("error", "Error al guardar: " + e.getMessage());
        }
        return "redirect:/servicio";
    }

    // 3) Actualizar servicio existente
    @PostMapping("/actualizar")
    public String actualizar(@ModelAttribute Servicio servicio, RedirectAttributes redirect) {
        try {
            servicioServicio.guardar(servicio); // save() funciona para guardar y actualizar
            redirect.addFlashAttribute("success", "Servicio actualizado correctamente");
        } catch (Exception e) {
            redirect.addFlashAttribute("error", "Error al actualizar: " + e.getMessage());
        }
        return "redirect:/servicio";
    }

    // 4) Buscar servicio por ID (usado en AJAX)
    @GetMapping("/editar/{id}")
    @ResponseBody
    public ResponseEntity<Servicio> editar(@PathVariable int id) {
        Optional<Servicio> servicioOpt = servicioServicio.buscarPorId(id);
        return servicioOpt.map(ResponseEntity::ok)
                          .orElse(ResponseEntity.notFound().build());
    }

    // 5) Eliminar servicio
    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable int id, RedirectAttributes redirect) {
        try {
            servicioServicio.eliminar(id);
            redirect.addFlashAttribute("success", "Servicio eliminado correctamente");
        } catch (Exception e) {
            redirect.addFlashAttribute("error", "No se pudo eliminar: " + e.getMessage());
        }
        return "redirect:/servicio";
    }
}
