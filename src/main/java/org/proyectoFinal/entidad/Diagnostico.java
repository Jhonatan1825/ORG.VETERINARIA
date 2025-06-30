package org.proyectoFinal.entidad;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "diagnostico")
public class Diagnostico implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_diagnostico")
	private Integer id;

	@Column(name = "descripcion", columnDefinition = "TEXT")
	private String descripcion;

	@Column(name = "tratamiento", columnDefinition = "TEXT")
	private String tratamiento;

	@Column(name = "estado_mascota")
	private String estadoMascota; // Ej: Crítico, Estable, Dado de alta

	
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	@Column(name = "fecha")
	private LocalDateTime fecha;
	
	
	@ManyToOne
	@JoinColumn(name = "id_mascota")
	private Mascotas mascota;

	@ManyToOne
	@JoinColumn(name = "id_veterinario")
	private Veterinario veterinario;

	@ManyToOne
	@JoinColumn(name = "id_cita")
	private Citas cita;
}
