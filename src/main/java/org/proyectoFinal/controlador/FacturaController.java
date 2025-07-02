package org.proyectoFinal.controlador;

import java.awt.Color;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.proyectoFinal.entidad.Citas;
import org.proyectoFinal.entidad.Factura;
import org.proyectoFinal.repositorio.CitasRepositorio;
import org.proyectoFinal.repositorio.FacturaRepositorio;
import org.proyectoFinal.servicio.FacturaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/facturas")
public class FacturaController {

    @Autowired
    private CitasRepositorio citasRepo;

    @Autowired
    private FacturaRepositorio facturaRepositorio;

    
    @Autowired
    private FacturaRepositorio facturaRepo;

    @Autowired
    private FacturaServicio facturaServicio;
    
    
    @GetMapping
    public String listarFacturas(Model model) {
        List<Factura> facturas = facturaRepositorio.findAll();
        model.addAttribute("facturas", facturas);
        return "listaFacturas";
    }


    @GetMapping("/generar/{idCita}")
    public void generarFacturaPdf(@PathVariable("idCita") Integer idCita, HttpServletResponse response) throws IOException {
        Citas cita = citasRepo.findById(idCita).orElse(null);
        if (cita == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Cita no encontrada");
            return;
        }

        // Crear objeto factura y guardar en la base de datos
        Factura factura = facturaServicio.crearFacturaDesdeCita(cita);
        facturaRepo.save(factura);

        // Configurar cabecera de respuesta
        response.setContentType("application/pdf");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=factura_cita_" + idCita + ".pdf";
        response.setHeader(headerKey, headerValue);

        // Crear documento PDF
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();

        // Estilo
        Font tituloFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20, Font.UNDERLINE, Color.BLACK);
        Font textoFont = FontFactory.getFont(FontFactory.HELVETICA, 12);

        // Encabezado
        Paragraph titulo = new Paragraph("FACTURA - Veterinaria Vida Animal", tituloFont);
        titulo.setAlignment(Element.ALIGN_CENTER);
        document.add(titulo);
        document.add(new Paragraph(" "));

        // Fecha emisión
        String fecha = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date());
        document.add(new Paragraph("Fecha de emisión: " + fecha, textoFont));
        document.add(new Paragraph("Factura N°: " + factura.getIdFactura(), textoFont));
        document.add(new Paragraph(" "));

        // Datos del cliente
        document.add(new Paragraph("Cliente: " + cita.getCliente().getNombre() + " " + cita.getCliente().getApellido(), textoFont));
        document.add(new Paragraph("DNI: " + cita.getCliente().getDni(), textoFont));
        document.add(new Paragraph("Teléfono: " + cita.getCliente().getTelefono(), textoFont));
        document.add(new Paragraph("Dirección: " + cita.getCliente().getDirecion(), textoFont));
        document.add(new Paragraph(" "));

        // Datos de la cita/servicio
        document.add(new Paragraph("Servicio brindado: " + cita.getServicio().getNombreServicio(), textoFont));
        document.add(new Paragraph("Veterinario: " + cita.getVeterinario().getNombre(), textoFont));
        document.add(new Paragraph("Motivo: " + cita.getMotivo(), textoFont));
        document.add(new Paragraph("Fecha de la cita: " + new SimpleDateFormat("dd/MM/yyyy HH:mm").format(cita.getFecha()), textoFont));
        document.add(new Paragraph(" "));

        // Totales
        double total = cita.getServicio().getCosto();  // Este ya incluye IGV
        double base = total / 1.18;
        double igv = total - base;

        document.add(new Paragraph("Base imponible: S/ " + String.format("%.2f", base), textoFont));
        document.add(new Paragraph("IGV (18%): S/ " + String.format("%.2f", igv), textoFont));
        document.add(new Paragraph("Total a pagar: S/ " + String.format("%.2f", total), textoFont));

        document.close();
    }
}
