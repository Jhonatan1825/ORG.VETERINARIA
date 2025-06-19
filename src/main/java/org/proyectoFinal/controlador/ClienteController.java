package org.proyectoFinal.controlador;

import java.util.List;
import java.util.Optional;

import org.proyectoFinal.entidad.Cliente;
import org.proyectoFinal.servicio.ClienteServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

	@Autowired
    private ClienteServicio clienteServicio;
    
    // Mostrar la página principal de clientes
    @GetMapping
    public String mostrarClientes(Model model) {
        List<Cliente> clientes = clienteServicio.listarTodos();
        model.addAttribute("clientes", clientes);
        model.addAttribute("cliente", new Cliente()); // Para el formulario
        return "clientes";
    }
    
    // Guardar nuevo cliente
    @PostMapping("/guardar")
    public String guardarCliente(@ModelAttribute Cliente cliente, 
                                RedirectAttributes redirectAttributes) {
        try {
            // Validar DNI único solo si no es el mismo cliente
            if (cliente.getIdCliente() == 0) {
                // Nuevo cliente
                if (clienteServicio.existePorDni(cliente.getDni())) {
                    redirectAttributes.addFlashAttribute("error", "El DNI ya existe en el sistema");
                    return "redirect:/clientes";
                }
            } else {
                // Actualizar cliente existente
                if (!clienteServicio.validarDniUnico(cliente.getDni(), cliente.getIdCliente())) {
                    redirectAttributes.addFlashAttribute("error", "El DNI ya existe en el sistema");
                    return "redirect:/clientes";
                }
            }
            
            clienteServicio.guardar(cliente);
            redirectAttributes.addFlashAttribute("success", "Cliente guardado exitosamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al guardar el cliente: " + e.getMessage());
        }
        return "redirect:/clientes";
    }
    
    // Obtener cliente por ID (para edición)
    @GetMapping("/editar/{id}")
    @ResponseBody
    public ResponseEntity<Cliente> obtenerClientePorId(@PathVariable int id) {
        Optional<Cliente> cliente = clienteServicio.buscarPorId(id);
        if (cliente.isPresent()) {
            return ResponseEntity.ok(cliente.get());
        }
        return ResponseEntity.notFound().build();
    }
    
    // Actualizar cliente
    @PostMapping("/actualizar")
    public String actualizarCliente(@ModelAttribute Cliente cliente, 
                                   RedirectAttributes redirectAttributes) {
        try {
            // Validar DNI único para actualización
            if (!clienteServicio.validarDniUnico(cliente.getDni(), cliente.getIdCliente())) {
                redirectAttributes.addFlashAttribute("error", "El DNI ya existe en el sistema");
                return "redirect:/clientes";
            }
            
            clienteServicio.guardar(cliente);
            redirectAttributes.addFlashAttribute("success", "Cliente actualizado exitosamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al actualizar el cliente: " + e.getMessage());
        }
        return "redirect:/clientes";
    }
    
    // Eliminar cliente
    @PostMapping("/eliminar/{id}")
    public String eliminarCliente(@PathVariable int id, 
                                 RedirectAttributes redirectAttributes) {
        try {
        	clienteServicio.eliminar(id);
            redirectAttributes.addFlashAttribute("success", "Cliente eliminado exitosamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al eliminar el cliente: " + e.getMessage());
        }
        return "redirect:/clientes";
    }
    
    // Eliminar cliente vía AJAX
    @DeleteMapping("/eliminar/{id}")
    @ResponseBody
    public ResponseEntity<String> eliminarClienteAjax(@PathVariable int id) {
        try {
        	clienteServicio.eliminar(id);
            return ResponseEntity.ok("Cliente eliminado exitosamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al eliminar el cliente: " + e.getMessage());
        }
    }

}
