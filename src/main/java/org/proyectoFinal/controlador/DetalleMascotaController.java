package org.proyectoFinal.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.List;

import org.proyectoFinal.entidad.Mascotas;
import org.proyectoFinal.entidad.Diagnostico;
import org.proyectoFinal.entidad.Citas;
import org.proyectoFinal.repositorio.MascotaRepositorio;
import org.proyectoFinal.repositorio.DiagnosticoRepositorio;
import org.proyectoFinal.repositorio.CitasRepositorio;

@Controller
@RequestMapping("/detalle-mascota")
public class DetalleMascotaController {

    @Autowired
    private MascotaRepositorio mascotaRepositorio;

    @Autowired
    private DiagnosticoRepositorio diagnosticoRepositorio;

    @Autowired
    private CitasRepositorio citasRepositorio;

    @GetMapping("/{id}")
    public String verDetalleMascota(@PathVariable("id") int id, Model model) {
        Optional<Mascotas> mascotaOpt = mascotaRepositorio.findById(id);
        if (mascotaOpt.isPresent()) {
            Mascotas mascota = mascotaOpt.get();

            List<Diagnostico> diagnosticos = diagnosticoRepositorio.findByMascotaIdMascota(id);
            List<Citas> citas = citasRepositorio.findByMascotaIdMascota(id);

            model.addAttribute("mascota", mascota);
            model.addAttribute("diagnosticos", diagnosticos);
            model.addAttribute("citas", citas);

            return "mascotadetalle"; // Vista detalle.html
        }

        return "error/404";
    }
}
