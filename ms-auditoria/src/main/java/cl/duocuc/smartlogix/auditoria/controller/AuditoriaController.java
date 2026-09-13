package cl.duocuc.smartlogix.auditoria.controller;

import cl.duocuc.smartlogix.auditoria.model.Auditoria;
import cl.duocuc.smartlogix.auditoria.service.AuditoriaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auditoria")
public class AuditoriaController {

    private final AuditoriaService auditoriaService;

    public AuditoriaController(AuditoriaService auditoriaService) {
        this.auditoriaService = auditoriaService;
    }

    @PostMapping
    public ResponseEntity<Auditoria> registrarAuditoria(@RequestBody Auditoria auditoria) {
        Auditoria nuevaAuditoria = auditoriaService.registrarAccion(auditoria);
        return ResponseEntity.ok(nuevaAuditoria);
    }

    // Ejemplo de uso: GET /api/auditoria/Pedido/5 (Traerá el historial del pedido 5)
    @GetMapping("/{entidad}/{id}")
    public ResponseEntity<List<Auditoria>> obtenerHistorial(
            @PathVariable String entidad, 
            @PathVariable Long id) {
        return ResponseEntity.ok(auditoriaService.obtenerHistorialPorEntidad(entidad, id));
    }

    @GetMapping
    public ResponseEntity<List<Auditoria>> listarTodo() {
        return ResponseEntity.ok(auditoriaService.obtenerTodo());
    }
}