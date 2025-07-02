package org.proyectoFinal.controlador;

import java.util.List;

import org.proyectoFinal.entidad.*;
import org.proyectoFinal.servicio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/citas")
public class CitasController {

    @Autowired
    private CitasUseCase citasUseCase;

    @Autowired
    private ClienteServicio clienteServicio;

    @Autowired
    private MascotaServicio mascotaServicio;

    @Autowired
    private VeterinarioServicio veterinarioServicio;

    @Autowired
    private ServicioServicio servicioServicio;

    //  MÉTODO para el modal
    @GetMapping("/form/{idCliente}")
    public String mostrarFormularioCita(@PathVariable("idCliente") int idCliente, Model model) {
        Cliente cliente = clienteServicio.buscarPorId(idCliente).orElse(null);
        if (cliente == null) {
            return "redirect:/clientes";
        }

        Citas nuevaCita = new Citas();
        nuevaCita.setCliente(cliente);

        model.addAttribute("cliente", cliente);
        model.addAttribute("nuevaCita", nuevaCita);
        model.addAttribute("mascotas", mascotaServicio.listarPorCliente(idCliente));
        model.addAttribute("veterinarios", veterinarioServicio.listarTodos());
        model.addAttribute("servicios", servicioServicio.listarTodos());

        return "formCita :: formCita"; // Retorna solo el fragmento del modal
    }

    // Guardar la cita
    @PostMapping("/guardar")
    public String guardarCita(@ModelAttribute("nuevaCita") Citas cita,
                              RedirectAttributes redirectAttributes) {
        citasUseCase.registrarCita(cita);
        redirectAttributes.addFlashAttribute("success", "✅ Cita registrada exitosamente.");
        return "redirect:/clientes";
    }

    // Listar todas las citas
    @GetMapping
    public String listarCitas(Model model) {
        List<Citas> citas = citasUseCase.listarTodas();
        model.addAttribute("citas", citas);
        return "listaCitas";
    }
    
 // Mostrar el formulario de edición en modal
    @GetMapping("/editar/{idCita}")
    public String mostrarFormularioEditar(@PathVariable("idCita") int idCita, Model model) {
        Citas cita = citasUseCase.buscarPorId(idCita);
        if (cita == null) return "error/404";

        model.addAttribute("cita", cita);
        model.addAttribute("veterinarios", veterinarioServicio.listarTodos());
        model.addAttribute("servicios", servicioServicio.listarTodos());

        return "formEditarCita :: formEditarCita";
    }

    // Guardar edición
    @PostMapping("/actualizar")
    public String actualizarCita(@ModelAttribute("cita") Citas cita, RedirectAttributes redirect) {
        citasUseCase.registrarCita(cita); // Usa el mismo método save()
        redirect.addFlashAttribute("success", "✅ Cita actualizada.");
        return "redirect:/citas";
    }

    // Eliminar cita
    @GetMapping("/eliminar/{idCita}")
    public String eliminarCita(@PathVariable("idCita") int idCita, RedirectAttributes redirect) {
        citasUseCase.eliminarPorId(idCita);
        redirect.addFlashAttribute("warning", "❌ Cita eliminada.");
        return "redirect:/citas";
    }
    
    
}
