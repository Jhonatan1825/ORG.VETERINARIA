package org.proyectoFinal.controlador;

import java.util.List;

import org.proyectoFinal.entidad.*;
import org.proyectoFinal.servicio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/citas")
public class CitasController {

	@Autowired
	private CitasUseCase citasUseCase;

    @Autowired
    private ClienteServicio clienteServicio;

    @Autowired
    private MascotaServicio mascotaServicio;  // <-- NECESARIO para el Combo de mascotas

    @Autowired
    private VeterinarioServicio veterinarioServicio;

    @Autowired
    private ServicioServicio servicioServicio; // <-- NECESARIO para el Combo de servicios

    // Muestra el formulario para crear cita con datos del cliente y combos
    @GetMapping("/form/{idCliente}")
    public String mostrarFormularioCita(@PathVariable("idCliente") int idCliente, Model model) {
        Cliente cliente = clienteServicio.buscarPorId(idCliente).orElse(null);
        if (cliente == null) {
            return "redirect:/clientes";
        }

        Citas nuevaCita = new Citas();
        nuevaCita.setCliente(cliente);

        List<Mascotas> mascotasCliente = mascotaServicio.listarPorCliente(idCliente);  // <-- mascotas del cliente
        List<Veterinario> veterinarios = veterinarioServicio.listarTodos();
        List<Servicio> servicios = servicioServicio.listarTodos();

        model.addAttribute("cliente", cliente);
        model.addAttribute("nuevaCita", nuevaCita);
        model.addAttribute("mascotas", mascotasCliente);
        model.addAttribute("veterinarios", veterinarios);
        model.addAttribute("servicios", servicios);

        return "crearCita"; // <-- formulario HTML
    }

    @PostMapping("/guardar")
    public String guardarCita(@ModelAttribute("nuevaCita") Citas cita) {
        citasUseCase.registrarCita(cita);
        return "redirect:/clientes";
    }
    @GetMapping
    public String listarCitas(Model model) {
        List<Citas> citas = citasUseCase.listarTodas();
        model.addAttribute("citas", citas);
        return "listaCitas";
    }

}
