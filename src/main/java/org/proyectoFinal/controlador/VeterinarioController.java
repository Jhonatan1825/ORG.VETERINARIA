package org.proyectoFinal.controlador;

import java.util.List;
import java.util.Optional;


import org.proyectoFinal.entidad.Veterinario;
import org.proyectoFinal.servicio.VeterinarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/veterinarios")
public class VeterinarioController {

	
	@Autowired
	private VeterinarioServicio veterinarioServicio;

	@GetMapping
	public String mostrarVeterinarios(Model model) {
		List<Veterinario> veterinarios = veterinarioServicio.listartodos();
		 model.addAttribute("veterinarios", veterinarios);
	     model.addAttribute("veterinario", new Veterinario()); // Para el formulario
	        return "veterinarios";
		
	}
	
	// Guardar nuevo veterinario
    @PostMapping("/guardarVeterinario")
    public String guardarVeterinario(@ModelAttribute Veterinario veterinario, 
                                RedirectAttributes redirectAttributes) {
        try {
            veterinarioServicio.guardar(veterinario);
            redirectAttributes.addFlashAttribute("success", "Veterinario guardado exitosamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al guardar el veterinario: " + e.getMessage());
        }
        return "redirect:/veterinarios";
    }
    
    // Obtener veterinario por ID (para edición)
    @GetMapping("/editar/{id}")
    @ResponseBody
    public ResponseEntity<Veterinario> obtenerVeterinarioPorId(@PathVariable int id) {
        Optional<Veterinario> veterinario = veterinarioServicio.buscarPorId(id);
        if (veterinario.isPresent()) {
            return ResponseEntity.ok(veterinario.get());
        }
        return ResponseEntity.notFound().build();
    }
    
    @PostMapping("/eliminar/{id}")
    public String eliminarVeterinario(@PathVariable int id, 
                                 RedirectAttributes redirectAttributes) {
        try {
        	veterinarioServicio.eliminar(id);
            redirectAttributes.addFlashAttribute("success", "Veterinario eliminado exitosamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al eliminar el veterinario: " + e.getMessage());
        }
        return "redirect:/veterinarios";
    }
    
 // Eliminar cliente vía AJAX
    @DeleteMapping("/eliminar/{id}")
    @ResponseBody
    public ResponseEntity<String> eliminarVeterinarioAjax(@PathVariable int id) {
        try {
        	veterinarioServicio.eliminar(id);
            return ResponseEntity.ok("Veterinario eliminado exitosamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al eliminar el veterinario: " + e.getMessage());
        }
    }

	
}

