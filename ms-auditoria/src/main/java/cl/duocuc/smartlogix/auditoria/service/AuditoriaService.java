package cl.duocuc.smartlogix.auditoria.service;

import cl.duocuc.smartlogix.auditoria.model.Auditoria;
import cl.duocuc.smartlogix.auditoria.repository.AuditoriaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuditoriaService {

    private final AuditoriaRepository auditoriaRepository;

    public AuditoriaService(AuditoriaRepository auditoriaRepository) {
        this.auditoriaRepository = auditoriaRepository;
    }

    // Guarda un nuevo registro de auditoría en la base de datos
    public Auditoria registrarAccion(Auditoria auditoria) {
        return auditoriaRepository.save(auditoria);
    }

    // Trae todo el historial de un registro específico (ej: todas las acciones de un Pedido)
    public List<Auditoria> obtenerHistorialPorEntidad(String entidad, Long entidadId) {
        return auditoriaRepository.findByEntidadAndEntidadIdOrderByFechaAccionDesc(entidad, entidadId);
    }

    // Trae todos los registros del sistema (útil para un panel de administrador)
    public List<Auditoria> obtenerTodo() {
        return auditoriaRepository.findAll();
    }
}