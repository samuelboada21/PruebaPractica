
package com.prueba.services.interfaces;

import com.prueba.models.Producto;
import java.util.List;

/**
 *
 * @author Samuel
 */
public interface ProductoServiceInterface {
    
    public List<Producto> listarProductos();
    public Producto buscarProducto(Integer id);
    public void añadirProducto(Producto producto);
    public void actualizarProducto(int id, Producto producto);
    public void eliminarProducto (int id);
    
}
