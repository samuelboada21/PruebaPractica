package com.prueba.models;

/**
 *
 * @author Samuel
 */
public class Detalle {

    private Integer id;
    private Integer cantidad;
    private Double valor;
    private Producto producto;

    public Detalle(Integer id, Integer cantidad, Producto producto) {
        this.id = id;
        this.producto = producto;
        this.cantidad = cantidad;
        this.valor = cantidad * producto.getPrecio();
    }

    public Detalle() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return "Detalle{" + "id=" + id + ", producto=" + producto + ", cantidad=" + cantidad + ", valor=" + valor + '}';
    }
}
