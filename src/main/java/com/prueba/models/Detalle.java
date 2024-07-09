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
    private Factura factura;

    public Detalle() {
    }

    public Detalle(Integer id, Integer cantidad, Double valor, Producto producto, Factura factura) {
        this.id = id;
        this.cantidad = cantidad;
        this.valor = cantidad*producto.getValor();
        this.producto = producto;
        this.factura = factura;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    @Override
    public String toString() {
        return "Detalle{" + "id=" + id + ", cantidad=" + cantidad + ", valor=" + valor + ", producto=" + producto + ", factura=" + factura + '}';
    }
}
