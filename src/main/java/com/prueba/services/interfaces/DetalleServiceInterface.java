
package com.prueba.services.interfaces;

import com.prueba.models.Detalle;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Samuel
 */
public interface DetalleServiceInterface {
    
    public List<Detalle> listarDetalles();
    public List<Detalle> listarDetallesFactura(int idFactura);
    public Detalle buscarDetalle(Integer id);
    public void añadirDetalle(Detalle detalle, int idFact);
    public void añadirDetalles(List<Detalle> detalles, int idFact);
    public void actualizarDetalle(int id, Detalle detalle, int idFact);
    public void eliminarDetalle(int id);
}
