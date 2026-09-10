package cl.duocuc.smartlogix.auditoria.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "auditorias")
public class Auditoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String accion; // Ej: "CREACION_USUARIO", "ELIMINACION_PEDIDO"

    @Column(nullable = false)
    private String entidad; // Ej: "Usuario", "Pedido", "Envio"

    @Column(name = "entidad_id", nullable = false)
    private Long entidadId; // El ID del registro que fue modificado

    @Column(name = "usuario_responsable", nullable = false)
    private String usuarioResponsable; // El email de quien hizo el cambio

    @Column(name = "fecha_accion")
    private LocalDateTime fechaAccion = LocalDateTime.now();

    @Column(columnDefinition = "TEXT")
    private String detalles; // Para guardar algún JSON o descripción extra

    public Auditoria() {}

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getAccion() { return accion; }
    public void setAccion(String accion) { this.accion = accion; }

    public String getEntidad() { return entidad; }
    public void setEntidad(String entidad) { this.entidad = entidad; }

    public Long getEntidadId() { return entidadId; }
    public void setEntidadId(Long entidadId) { this.entidadId = entidadId; }

    public String getUsuarioResponsable() { return usuarioResponsable; }
    public void setUsuarioResponsable(String usuarioResponsable) { this.usuarioResponsable = usuarioResponsable; }

    public LocalDateTime getFechaAccion() { return fechaAccion; }
    public void setFechaAccion(LocalDateTime fechaAccion) { this.fechaAccion = fechaAccion; }

    public String getDetalles() { return detalles; }
    public void setDetalles(String detalles) { this.detalles = detalles; }
}