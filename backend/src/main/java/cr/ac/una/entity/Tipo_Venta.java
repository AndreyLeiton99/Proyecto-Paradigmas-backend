package cr.ac.una.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity

public class Tipo_Venta {

    public Tipo_Venta(){

    }
    public Long getId_tipo_venta() {
        return id_tipo_venta;
    }

    public void setId_tipo_venta(Long id_tipo_venta) {
        this.id_tipo_venta = id_tipo_venta;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id_tipo_venta;
    private String descripcion;

}
