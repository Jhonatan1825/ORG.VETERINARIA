package org.proyectoFinal.entidad;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor 
@NoArgsConstructor 
@Entity 
@Table(name = "citas")
public class Citas implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer  id_cita;

	// Cambiado para permitir fecha y hora
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	private Date fecha;

	private String motivo;

	@ManyToOne
	@JoinColumn(name = "id_mascota")
	private Mascotas mascota;

	@ManyToOne
	@JoinColumn(name = "id_veterinario")
	private Veterinario veterinario;

	@ManyToOne
	@JoinColumn(name = "id_servicio")
	private Servicio servicio;

	@ManyToOne
	@JoinColumn(name = "id_cliente")
	private Cliente cliente;
}
