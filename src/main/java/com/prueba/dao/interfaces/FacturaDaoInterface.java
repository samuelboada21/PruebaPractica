package com.prueba.dao.interfaces;

import com.prueba.models.Detalle;
import com.prueba.models.Factura;
import java.util.List;

/**
 *
 * @author Samuel
 */
public interface FacturaDaoInterface {
    
    public List<Factura> findAll();
    public Factura findById(int id);
    public void save(Factura factura, List<Detalle> detalles);
    public void update(int id, Factura factura);
    public void delete(int id);
}
