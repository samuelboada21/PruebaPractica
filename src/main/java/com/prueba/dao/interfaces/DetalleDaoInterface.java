
package com.prueba.dao.interfaces;

import com.prueba.models.Detalle;
import java.util.List;

/**
 *
 * @author Samuel
 */
public interface DetalleDaoInterface {
    
    public List<Detalle> findAllbyFacturaId(int idFact);
    public Detalle findById(int id);
    public void save(Detalle detalle, int idFact);
    public void saveAll(List<Detalle> detalles, int idFact);
    public void update(int id, Detalle detalle, int idFact);
    public void delete(int id);
}
