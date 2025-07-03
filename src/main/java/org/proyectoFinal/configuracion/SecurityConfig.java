package org.proyectoFinal.configuracion;

import java.util.Collections;
import java.util.HashSet;

import org.proyectoFinal.entidad.Rol;
import org.proyectoFinal.entidad.Usuario;
import org.proyectoFinal.repositorio.RolRepositorio;
import org.proyectoFinal.repositorio.UsuarioRepositorio;
import org.proyectoFinal.servicio.UsuarioDetalleServicio;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public UserDetailsService userDetailsService() {
        return new UsuarioDetalleServicio(); // Tu clase de servicio personalizado
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userDetailsService());
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authz -> authz
                .requestMatchers("/css/**", "/js/**", "/login", "/registro", "/error").permitAll()
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers("/veterinario/**").hasRole("VETERINARIO")
                .requestMatchers("/recepcion/**").hasRole("RECEPCION")
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/inicio", true)
                .permitAll()
            )
            .logout(logout -> logout
            		  .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            );

        return http.build();
    }

    //admin para testearrr
    @Bean
    public CommandLineRunner crearAdminPorDefecto(UsuarioRepositorio usuarioRepo, RolRepositorio rolRepo, PasswordEncoder encoder) {
        return args -> {
            String username = "admin";
            String password = "admin123";

            if (usuarioRepo.findByUsername(username).isEmpty()) {
                Rol rolAdmin = rolRepo.findByNombre("ADMIN").orElseGet(() -> {
                    Rol nuevoRol = new Rol();
                    nuevoRol.setNombre("ADMIN");
                    return rolRepo.save(nuevoRol);
                });

                Usuario admin = new Usuario();
                admin.setUsername(username);
                admin.setPassword(encoder.encode(password));
                admin.setActivo(true);
                admin.setRoles(new HashSet<>(Collections.singletonList(rolAdmin)));

                usuarioRepo.save(admin);
                System.out.println("✅ Usuario admin creado por defecto: " + username + "/" + password);
            }
        };
    }
}
