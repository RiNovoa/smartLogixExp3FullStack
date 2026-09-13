package cl.duocuc.smartlogix.auditoria.repository;

import cl.duocuc.smartlogix.auditoria.model.Auditoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuditoriaRepository extends JpaRepository<Auditoria, Long> {
    
    // Método mágico de Spring: Busca todo el historial de un ID específico (ej: todo lo que le pasó al pedido 5)
    List<Auditoria> findByEntidadAndEntidadIdOrderByFechaAccionDesc(String entidad, Long entidadId);
}