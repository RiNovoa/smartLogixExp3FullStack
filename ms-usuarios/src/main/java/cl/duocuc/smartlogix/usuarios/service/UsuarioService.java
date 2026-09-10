package cl.duocuc.smartlogix.usuarios.service;

import cl.duocuc.smartlogix.usuarios.model.Usuario;
import cl.duocuc.smartlogix.usuarios.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.List;
import java.util.Optional;
import java.util.HashMap;
import java.util.Map;
@Service
public class UsuarioService {
    

    private final UsuarioRepository usuarioRepository;
    private final RestTemplate restTemplate;
    // Inyección de dependencias (Spring conecta el repositorio automáticamente)
        public UsuarioService(UsuarioRepository usuarioRepository, RestTemplate restTemplate) {
        this.usuarioRepository = usuarioRepository;
        this.restTemplate = restTemplate;
    }

    public Usuario crearUsuario(Usuario usuario) {
        // 1. Guardamos en la base de datos local
        Usuario nuevoUsuario = usuarioRepository.save(usuario);
        
// 2. Enviamos el log de trazabilidad a ms-auditoria
   try {
            String auditoriaUrl = "http://localhost:8082/api/auditoria"; 
            
            Map<String, Object> logJson = new HashMap<>();
            
            // ¡Nombres exactos según tu clase Auditoria!
            logJson.put("accion", "CREACION_USUARIO"); 
            logJson.put("entidad", "Usuario"); 
            logJson.put("entidadId", nuevoUsuario.getId()); 
            
            // Como quien crea la cuenta es el propio usuario, usaremos su email. 
            // En el futuro, si un Admin lo crea, podrías sacar el email del token JWT.
            logJson.put("usuarioResponsable", nuevoUsuario.getEmail()); 
            
            logJson.put("detalles", "Se creó un nuevo usuario con nombre: " + nuevoUsuario.getNombre());
            
            // Disparamos la petición
            restTemplate.postForObject(auditoriaUrl, logJson, Map.class);
            
            System.out.println("Log de auditoría enviado con éxito.");
        } catch (Exception e) {
            System.err.println("No se pudo enviar el log a ms-auditoria: " + e.getMessage());
        }
        return nuevoUsuario;
    }
    public List<Usuario> obtenerUsuariosActivos() {
        return usuarioRepository.findByActivoTrue();
    }

    public Optional<Usuario> obtenerPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    public boolean eliminarUsuario(Long id) {
        // 1. Buscamos el usuario antes de borrarlo (para tener su email o nombre para el log)
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);
        
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            
            // 2. Borramos el usuario de la base de datos
            usuarioRepository.deleteById(id);
            
            // 3. Enviamos el log de auditoría del borrado
            try {
                String auditoriaUrl = "http://localhost:8082/api/auditoria"; 
                
                Map<String, Object> logJson = new HashMap<>();
                logJson.put("accion", "ELIMINACION_USUARIO"); 
                logJson.put("entidad", "Usuario"); 
                logJson.put("entidadId", id); 
                logJson.put("usuarioResponsable", usuario.getEmail()); // O el admin que lo eliminó
                logJson.put("detalles", "Se eliminó el usuario con email: " + usuario.getEmail());
                
                restTemplate.postForObject(auditoriaUrl, logJson, Map.class);
                
                System.out.println("Log de auditoría por eliminación enviado con éxito.");
            } catch (Exception e) {
                System.err.println("No se pudo enviar el log de eliminación a ms-auditoria: " + e.getMessage());
            }
            
            return true;
        }
        
        return false;
    }

}