
package com.prueba.services.interfaces;

import com.prueba.models.Factura;
import java.util.List;

/**
 *
 * @author Samuel
 */
public interface FacturaServiceInterface {
    
    public List<Factura> listarFacturas();
    public Factura buscarFactura(Integer id);
    public void añadirFactura(Factura factura);
    public void actualizarFactura(int id, Factura factura);
    public void eliminarFactura(int id);
}
