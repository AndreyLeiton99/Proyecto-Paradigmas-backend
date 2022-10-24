package cr.ac.una.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity

public class Producto {

    public Producto(){

    }

    public Long getId_Producto() {
        return id_Producto;
    }

    public void setId_Producto(Long id_producto) {
        this.id_Producto = id_producto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id_Producto;

    private String descripcion;
    private Integer cantidad;

}
