
package com.prueba.services.implement;

import com.prueba.dao.implement.ProductoDaoJdbc;
import com.prueba.dao.interfaces.ProductoDaoInterface;
import com.prueba.models.Producto;
import com.prueba.services.interfaces.ProductoServiceInterface;
import java.util.List;

/**
 *
 * @author Samuel
 */
public class ProductoService implements ProductoServiceInterface{

    private ProductoDaoInterface productoDao = new ProductoDaoJdbc();
    @Override
    public List<Producto> listarProductos() {
        return productoDao.findAll();
    }

    @Override
    public Producto buscarProducto(Integer id) {
        return productoDao.findById(id);
    }

    @Override
    public void añadirProducto(Producto producto) {
        productoDao.save(producto);
    }

    @Override
    public void actualizarProducto(int id, Producto producto) {
        productoDao.update(id, producto);
    }

    @Override
    public void eliminarProducto(int id) {
        productoDao.delete(id);
    }
    
}
