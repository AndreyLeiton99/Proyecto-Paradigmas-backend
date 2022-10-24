package cr.ac.una.entity;

import javax.persistence.*;
import java.util.Date;

@Entity

public class Venta {


    public Long getId_Venta() {
        return id_Venta;
    }

    public void setId_Venta(Long id_venta) {
        this.id_Venta = id_venta;
    }

    public Tipo_Venta getTipo_venta() {
        return tipo_venta;
    }

    public void setTipo_venta(Tipo_Venta tipo_venta) {
        this.tipo_venta = tipo_venta;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Persona getPersona() { return persona; }

    public void setPersona(Persona persona) { this.persona = persona; }

    public Date getFecha() { return fecha; }

    public void setFecha(Date fecha) { this.fecha = fecha; }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id_Venta;
    @OneToOne
    private Tipo_Venta tipo_venta;
    @OneToOne
    private Persona persona;
    @OneToOne
    private Producto producto;
    private Integer cantidad;

    private Date fecha;
}
