package com.prueba.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    private List<Detalle> detalles = new ArrayList<>();

    public Factura(Integer id, String nombreCliente, Date fecha, Double subtotal, Double iva, Double total, List<Detalle> detalles) {
        this.id = id;
        this.nombreCliente = nombreCliente;
        this.fecha = fecha;
        this.subtotal = subtotal;
        this.iva = iva;
        this.total = total;
        this.detalles = detalles;
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

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getIva() {
        return iva;
    }

    public void setIva(Double iva) {
        this.iva = iva;
    }

    public List<Detalle> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<Detalle> detalles) {
        this.detalles = detalles;
    }

    @Override
    public String toString() {
        return "Factura{" + "id=" + id + ", nombreCliente=" + nombreCliente + ", fecha=" + fecha + ", subtotal=" + subtotal + ", iva=" + iva + ", total=" + total + ", detalles=" + detalles + '}';
    }
}
