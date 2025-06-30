package org.proyectoFinal.entidad;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_mascota")
	private Integer idMascota;

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

	@Column(name="imagen")
	private String imagen;

	@Column(name="alergias")
	private String alergias;

	@Column(name="comportamiento")
	private String comportamiento;

	@ManyToOne
	@JoinColumn(name="id_cliente")
	private Cliente cliente;

	@OneToMany(mappedBy = "mascota", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Citas> citas;
}
