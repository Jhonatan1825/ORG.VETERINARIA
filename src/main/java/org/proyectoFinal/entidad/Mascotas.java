package org.proyectoFinal.entidad;

import java.io.Serializable;

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
@Table(name="mascotas")
public class Mascotas implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	@Column(name="id_mascota")
	private Integer id_mascota;
	
	@Column(name="nombre")
	private String nombre;
	
	@Column(name="especie")
	private String especie;
	
	@Column(name="raza")
	private String raza;
	
	@Column(name="edad")
	private int edad;
	
	@Column(name="genero")
	private String genero;
	
	@ManyToOne
	@JoinColumn(name="id_cliente")
	private Cliente cliente;

}
