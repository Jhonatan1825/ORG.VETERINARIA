package org.proyectoFinal.controlador;

import java.util.List;
import java.util.Optional;

import org.proyectoFinal.entidad.Cliente;
import org.proyectoFinal.entidad.Mascotas;
import org.proyectoFinal.repositorio.ClienteRepositorio;
import org.proyectoFinal.repositorio.MascotaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class MascotasController {

    @Autowired
    private MascotaRepositorio mascotaRepositorio;

    @Autowired
    private ClienteRepositorio clienteRepositorio;

    // Listar mascotas
    @GetMapping("/mascotas")
    public String listarMascotas(Model model) {
        List<Cliente> clientes = clienteRepositorio.findAll();
        List<Mascotas> mascotas = mascotaRepositorio.findAll();

        model.addAttribute("clientes", clientes);
        model.addAttribute("mascotas", mascotas);
        model.addAttribute("cliente", new Cliente());  // Para el formulario
        model.addAttribute("mascota", new Mascotas()); // Necesario para evitar error con el input hidden

        return "mascotas";
    }

    // Guardar nueva mascota o actualizar
    @PostMapping("/guardar")
    public String guardarMascota(@ModelAttribute Mascotas mascota,
                                 RedirectAttributes redirectAttributes) {
        try {
            // Si el ID viene como 0, lo anulamos para que se genere uno nuevo
            if (mascota.getId_mascota() != null && mascota.getId_mascota() == 0) {
                mascota.setId_mascota(null);
            }

            mascotaRepositorio.save(mascota);
            redirectAttributes.addFlashAttribute("success", "Mascota guardada exitosamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al guardar la mascota: " + e.getMessage());
        }
        return "redirect:/clientes";
    }
 // Cargar formulario de nueva mascota
    @GetMapping("/mascotas/form/{idCliente}")
    public String mostrarFormularioMascota(@PathVariable int idCliente, Model model) {
        Optional<Cliente> clienteOpt = clienteRepositorio.findById(idCliente);
        if (clienteOpt.isPresent()) {
            Mascotas mascota = new Mascotas();
            mascota.setCliente(clienteOpt.get());
            model.addAttribute("mascota", mascota);
            return "formMascota :: form"; // <-- nombre de archivo y fragmento
        }
        return "error/404";}

    

    // Obtener mascota por ID (para edición por AJAX)
    @GetMapping("/mascotas/editar/{id}")
    @ResponseBody
    public ResponseEntity<Mascotas> obtenerMascotaPorId(@PathVariable int id) {
        Optional<Mascotas> mascota = mascotaRepositorio.findById(id);
        if (mascota.isPresent()) {
            return ResponseEntity.ok(mascota.get());
        }
        return ResponseEntity.notFound().build();
    }

    // Eliminar mascota desde formulario
    @PostMapping("/mascotas/eliminar/{id}")
    public String eliminarMascota(@PathVariable int id,
                                  RedirectAttributes redirectAttributes) {
        try {
            mascotaRepositorio.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Mascota eliminada exitosamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al eliminar la mascota: " + e.getMessage());
        }
        return "redirect:/mascotas";
    }

    // Eliminar mascota vía AJAX (opcional si usas JS)
    @DeleteMapping("/eliminar/{id}")
    @ResponseBody
    public ResponseEntity<String> eliminarMascotaAjax(@PathVariable int id) {
        try {
            mascotaRepositorio.deleteById(id);
            return ResponseEntity.ok("Mascota eliminada exitosamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al eliminar la mascota: " + e.getMessage());
        }
    }
}
