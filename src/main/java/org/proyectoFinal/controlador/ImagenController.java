package org.proyectoFinal.controlador;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class ImagenController {

    @GetMapping("/img_mascotas/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> mostrarImagen(@PathVariable String filename) {
        try {
            // Ruta donde guardaste las imágenes
            Path path = Paths.get("C:/mascotas_img/").resolve(filename);
            Resource recurso = new UrlResource(path.toUri());

            if (recurso.exists() && recurso.isReadable()) {
                // Detectar tipo MIME (jpg, png, etc.)
                String contentType = Files.probeContentType(path);
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_TYPE, contentType)
                        .body(recurso);
            } else {
                return ResponseEntity.notFound().build();
            }

        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
