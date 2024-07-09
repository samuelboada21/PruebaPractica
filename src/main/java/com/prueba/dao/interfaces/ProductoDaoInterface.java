
package com.prueba.dao.interfaces;

import com.prueba.models.Producto;
import java.util.List;

/**
 *
 * @author Samuel
 */
public interface ProductoDaoInterface {
    
    public List<Producto> findAll();
    public Producto findById(int id);
    public void save(Producto producto);
    public void update(int id, Producto producto);
    public void delete(int id);
    
}
