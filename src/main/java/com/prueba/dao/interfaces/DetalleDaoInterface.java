
package com.prueba.dao.interfaces;

import com.prueba.models.Detalle;
import java.util.List;

/**
 *
 * @author Samuel
 */
public interface DetalleDaoInterface {
    
    public List<Detalle> findAllbyFacturaId();
    public Detalle findById(int id);
    public void save(Detalle detalle);
    public void update(int id, Detalle detalle);
    public void delete(int id);
}
