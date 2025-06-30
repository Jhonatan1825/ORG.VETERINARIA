package org.proyectoFinal.controlador;

import java.time.LocalDateTime;
import java.util.Optional;

import org.proyectoFinal.entidad.Citas;
import org.proyectoFinal.entidad.Diagnostico;
import org.proyectoFinal.entidad.Mascotas;
import org.proyectoFinal.repositorio.CitasRepositorio;
import org.proyectoFinal.repositorio.MascotaRepositorio;
import org.proyectoFinal.repositorio.VeterinarioRepositorio;
import org.proyectoFinal.servicio.CitasUseCase;
import org.proyectoFinal.servicio.DiagnosticoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/diagnosticos")
public class DiagnosticoController {

    @Autowired
    private DiagnosticoServicio diagnosticoServicio;

    @Autowired
    private CitasUseCase citasUseCase;

    @Autowired
    private MascotaRepositorio mascotaRepositorio;

    @Autowired
    private VeterinarioRepositorio veterinarioRepositorio;

    @Autowired
    private CitasRepositorio citasRepositorio;

    // Mostrar formulario para registrar diagnóstico desde una cita (opcional)
    @GetMapping("/nuevo/{idCita}")
    public String mostrarFormularioDesdeCita(@PathVariable("idCita") Integer idCita, Model model) {
        Citas cita = citasUseCase.buscarPorId(idCita);
        if (cita == null) {
            return "redirect:/citas";
        }

        Diagnostico diagnostico = new Diagnostico();
        diagnostico.setCita(cita);
        diagnostico.setVeterinario(cita.getVeterinario());
        diagnostico.setMascota(cita.getMascota());
        diagnostico.setFecha(LocalDateTime.now());

        model.addAttribute("diagnostico", diagnostico);
        return "formDiagnostico";
    }

    // Guardar diagnóstico
    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Diagnostico diagnostico) {
        diagnosticoServicio.guardar(diagnostico);
        return "redirect:/detalle-mascota/" + diagnostico.getMascota().getIdMascota();
    }

    // AJAX: mostrar formulario modal desde detalle de mascota
    @GetMapping("/form/{idMascota}")
    public String mostrarFormularioDiagnostico(@PathVariable int idMascota, Model model) {
        Optional<Mascotas> mascotaOpt = mascotaRepositorio.findById(idMascota);
        if (mascotaOpt.isPresent()) {
            Diagnostico diag = new Diagnostico();
            diag.setMascota(mascotaOpt.get());
            diag.setFecha(LocalDateTime.now());

            model.addAttribute("diagnostico", diag);
            model.addAttribute("veterinarios", veterinarioRepositorio.findAll());
            model.addAttribute("citas", citasRepositorio.findByMascotaIdMascota(idMascota));

            return "formDiagnostico :: form";
        }
        return "error/404";
    }

    // Listar historial (no obligatorio si ya está en detalle de mascota)
    @GetMapping("/mascota/{idMascota}")
    public String listarPorMascota(@PathVariable("idMascota") Integer idMascota, Model model) {
        model.addAttribute("diagnosticos", diagnosticoServicio.listarPorMascota(idMascota));
        return "listaDiagnosticos";
    }
}
