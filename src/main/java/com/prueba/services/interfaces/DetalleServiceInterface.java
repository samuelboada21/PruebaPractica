
package com.prueba.services.interfaces;

import com.prueba.models.Detalle;
import java.util.List;

/**
 *
 * @author Samuel
 */
public interface DetalleServiceInterface {
    
    public List<Detalle> listarDetallesFactura();
    public Detalle buscarDetalle(Integer id);
    public void añadirDetalle(Detalle Detalle);
    public void actualizarDetalle(int id, Detalle detalle);
    public void eliminarDetalle(int id);
}
