package com.prueba.models;

import java.util.Date;

/**
 *
 * @author Samuel
 */
public class Factura {

    private Integer id;
    private String nombreCliente;
    private Date fecha;
    private Double subtotal;
    private Double iva;
    private Double total;

    public Factura(Integer id, String nombreCliente, Date fecha, Double subtotal, Double iva, Double total) {
        this.id = id;
        this.nombreCliente = nombreCliente;
        this.fecha = fecha;
        this.subtotal = subtotal;
        this.iva = iva;
        this.total = total;
    }

    public Factura() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public Double getIva() {
        return iva;
    }

    public void setIva(Double iva) {
        this.iva = iva;
    }

    public Double getTotal() {
        return total;
    }

    @Override
    public String toString() {
        return "Factura{" + "id=" + id + ", nombreCliente=" + nombreCliente + ", fecha=" + fecha + ", subtotal=" + subtotal + ", iva=" + iva + ", total=" + total + '}';
    }
}
