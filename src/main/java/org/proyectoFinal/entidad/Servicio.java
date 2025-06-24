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
@Table(name="servicio")
public class Servicio implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	@Column(name="id_servicio")
	private Integer id_servicio;
	
	@Column(name = "nombre_servicio")
	private String nombreServicio;
	
	@Column(name="descripcion")
	private String descripcion;
	
	@Column(name="costo")
	private double costo;

}
