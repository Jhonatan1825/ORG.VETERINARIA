package org.proyectoFinal.entidad;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String nombre;  // Ej: ROLE_ADMIN, ROLE_VETERINARIO, ROLE_RECEPCION
}
