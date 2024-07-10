
package com.prueba.dao.interfaces;

import com.prueba.models.Detalle;
import java.util.List;

/**
 *
 * @author Samuel
 */
public interface DetalleDaoInterface {
    
    public List<Detalle> findAll();
    public List<Detalle> findDetallesByFactura(int idFact);
    public Detalle findDetalleById(int id);
    public void saveDetalle(Detalle detalle, int idFact);
    public void saveAllDetalle(List<Detalle> detalles, int idFact);
    public void updateDetalle(int id, Detalle detalle, int idFact);
    public void deleteDetalle(int id);
}
