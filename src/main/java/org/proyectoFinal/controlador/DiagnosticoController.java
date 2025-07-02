package org.proyectoFinal.controlador;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.time.format.DateTimeFormatter;
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

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;


import jakarta.servlet.http.HttpServletResponse;

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

    // Listar historial 
    @GetMapping("/mascota/{idMascota}")
    public String listarPorMascota(@PathVariable("idMascota") Integer idMascota, Model model) {
        model.addAttribute("diagnosticos", diagnosticoServicio.listarPorMascota(idMascota));
        return "listaDiagnosticos";
    }
    
    
    // Generar el PDF paralas mascotitas 
    @GetMapping("/exportar-pdf/{idMascota}")
    public void exportarDiagnosticoPdf(@PathVariable("idMascota") int idMascota, HttpServletResponse response) throws Exception {
        Optional<Mascotas> mascotaOpt = mascotaRepositorio.findById(idMascota);
        if (!mascotaOpt.isPresent()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        Mascotas mascota = mascotaOpt.get();
        List<Diagnostico> diagnosticos = diagnosticoServicio.listarPorMascota(idMascota);
        List<Citas> citas = citasRepositorio.findByMascotaIdMascota(idMascota);

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=diagnostico_mascota_" + idMascota + ".pdf");

        Document document = new Document();
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();

        // Encabezado: Nombre de la Veterinaria
        Font fontTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, BaseColor.BLACK);
        Paragraph titulo = new Paragraph("Veterinaria Vida Animal");
        titulo.setFont(fontTitulo);
        titulo.setAlignment(Element.ALIGN_CENTER);
        document.add(titulo);

        // Subtítulo
        Font fontSubtitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 15, BaseColor.BLACK);
        Paragraph subtitulo = new Paragraph("Informe Médico de Mascota");
        subtitulo.setFont(fontSubtitulo);
        subtitulo.setAlignment(Element.ALIGN_CENTER);
        document.add(subtitulo);

        document.add(new Paragraph(" "));
        document.add(new LineSeparator());
        document.add(new Paragraph(" "));

        // Datos de la mascota y su dueño
        document.add(new Paragraph("Nombre de la Mascota: " + mascota.getNombre()));
        document.add(new Paragraph("Dueño: " + mascota.getCliente().getNombre() + " " + mascota.getCliente().getApellido()));
        document.add(new Paragraph("Teléfono del Dueño: " + mascota.getCliente().getTelefono()));
        document.add(new Paragraph(" "));

        // Diagnósticos
        if (!diagnosticos.isEmpty()) {
            document.add(new Paragraph(">> Diagnósticos:"));
            for (Diagnostico d : diagnosticos) {
                document.add(new Paragraph("- Fecha: " + d.getFecha()));
                document.add(new Paragraph("  Estado: " + d.getEstadoMascota()));
                document.add(new Paragraph("  Veterinario: " + d.getVeterinario().getNombre()));
                document.add(new Paragraph("  Descripción: " + d.getDescripcion()));
                document.add(new Paragraph(" "));
            }
        } else {
            document.add(new Paragraph("No se encontraron diagnósticos registrados."));
            document.add(new Paragraph(" "));
        }

        // Citas
        if (!citas.isEmpty()) {
            document.add(new Paragraph(">> Citas:"));
            for (Citas c : citas) {
                document.add(new Paragraph("- Fecha: " + c.getFecha()));
                document.add(new Paragraph("  Servicio: " + c.getServicio().getNombreServicio()));
                document.add(new Paragraph("  Veterinario: " + c.getVeterinario().getNombre()));
                document.add(new Paragraph("  Costo: S/ " + c.getServicio().getCosto()));
                
                document.add(new Paragraph(" "));
            }
        } else {
            document.add(new Paragraph("No se encontraron citas registradas."));
            document.add(new Paragraph(" "));
        }

		// Fecha de emisión
		        LocalDateTime ahora = LocalDateTime.now();
		        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		        String fechaFormateada = ahora.format(formato);
		
		        document.add(new Paragraph("Fecha de emisión: " + fechaFormateada));
        document.close();
    }  
}
