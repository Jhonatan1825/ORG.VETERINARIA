package org.proyectoFinal.servicio;

import java.time.LocalDateTime;

import org.proyectoFinal.entidad.Citas;
import org.proyectoFinal.entidad.Factura;
import org.springframework.stereotype.Service;

@Service
public class FacturaServicio {

    public Factura crearFacturaDesdeCita(Citas cita) {
        if (cita == null || cita.getServicio() == null || cita.getCliente() == null) {
            throw new IllegalArgumentException("Cita inválida para generar factura");
        }

        double total = cita.getServicio().getCosto();      
        double subtotal = total / 1.18;                     
        double igv = total - subtotal;

        Factura factura = new Factura();
        factura.setCita(cita);
        factura.setCliente(cita.getCliente());
        factura.setFechaEmision(LocalDateTime.now());
        factura.setSubtotal(subtotal);
        factura.setIgv(igv);
        factura.setTotal(total);

        return factura;
    }
}
