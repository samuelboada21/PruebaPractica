
package com.prueba.services.interfaces;

import com.prueba.models.Detalle;
import com.prueba.models.Factura;
import java.util.List;

/**
 *
 * @author Samuel
 */
public interface FacturaServiceInterface {
    
    public List<Factura> listarFacturas();
    public Factura buscarFactura(Integer id);
    public void añadirFactura(Factura factura, List<Detalle> detalles);
    public void actualizarFactura(int id, Factura factura);
    public void eliminarFactura(int id);
}
