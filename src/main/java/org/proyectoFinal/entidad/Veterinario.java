package org.proyectoFinal.entidad;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor 
@NoArgsConstructor 
@Entity 
@Table(name="veterinario")
public class Veterinario implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	@Column(name="id_veterinario")
	private Integer id_veterinario;
	
	@Column(name="nombre")
	private String nombre;
	
	@Column(name="especialidad")
	private String especialidad;
	
	@Column(name="telefono")
	private String telefono;
	
	
	
}
