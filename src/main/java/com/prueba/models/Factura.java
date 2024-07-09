
package com.prueba.models;

import java.util.Date;

/**
 *
 * @author Samuel
 */
public class Factura {
    private Integer id;
    private Integer num_factura;
    private String nombre_cliente;
    private Date fecha;
    private Double iva;
    
    public Factura() {
    }

    public Factura(Integer id, Integer num_factura, String nombre_cliente, Date fecha, Double iva) {
        this.id = id;
        this.num_factura = num_factura;
        this.nombre_cliente = nombre_cliente;
        this.fecha = fecha;
        this.iva = iva;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNum_factura() {
        return num_factura;
    }

    public void setNum_factura(Integer num_factura) {
        this.num_factura = num_factura;
    }

    public String getNombre_cliente() {
        return nombre_cliente;
    }

    public void setNombre_cliente(String nombre_cliente) {
        this.nombre_cliente = nombre_cliente;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Double getIva() {
        return iva;
    }

    public void setIva(Double iva) {
        this.iva = iva;
    }

    @Override
    public String toString() {
        return "Factura{" + "id=" + id + ", num_factura=" + num_factura + ", nombre_cliente=" + nombre_cliente + ", fecha=" + fecha + ", iva=" + iva + '}';
    }
    
    
}
