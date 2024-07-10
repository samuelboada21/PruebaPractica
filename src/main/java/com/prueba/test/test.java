
package com.prueba.test;

import com.prueba.dao.implement.ProductoDaoJdbc;
import com.prueba.dao.interfaces.ProductoDaoInterface;
import com.prueba.models.Producto;
import java.util.List;

/**
 *
 * @author Samuel
 */
public class test {
    
    public static void main(String[] args) {
//        testGuardarProducto();
//        testListarProductos();
//        testBuscarProductoPorId();
//        testActualizarProducto();
        testEliminarProducto();
    }

    private static void testGuardarProducto() {
        ProductoDaoInterface productoDao = new ProductoDaoJdbc();
        Producto nuevoProducto = new Producto(0, "Producto de Prueba", 1500.0);
        productoDao.save(nuevoProducto);
        System.out.println("Producto guardado: " + nuevoProducto.getNombre());
    }

    private static void testListarProductos() {
        ProductoDaoInterface productoDao = new ProductoDaoJdbc();
        List<Producto> productos = productoDao.findAll();
        System.out.println("Lista de productos:");
        for (Producto producto : productos) {
            System.out.println("ID: " + producto.getId() + ", Nombre: " + producto.getNombre() + ", precio: " + producto.getPrecio());
        }
    }

    private static void testBuscarProductoPorId() {
        ProductoDaoInterface productoDao = new ProductoDaoJdbc();
        int id = 1;
        Producto productoObtenido = productoDao.findById(id);
        if (productoObtenido != null) {
            System.out.println("Producto obtenido por ID: " + productoObtenido.getNombre());
        } else {
            System.out.println("Producto con ID " + id + " no encontrado.");
        }
    }

    private static void testActualizarProducto() {
        ProductoDaoInterface productoDao = new ProductoDaoJdbc();
        int id = 1; 
        Producto productoAActualizar = productoDao.findById(id);
        if (productoAActualizar != null) {
            productoAActualizar.setNombre("Producto Actualizado S");
            productoAActualizar.setPrecio(1000.0);
            productoDao.update(id, productoAActualizar);
            System.out.println("Producto actualizado: " + productoAActualizar.getNombre());
        } else {
            System.out.println("Producto con ID " + id + " no encontrado.");
        }
    }

    private static void testEliminarProducto() {
        ProductoDaoInterface productoDao = new ProductoDaoJdbc();
        int id = 4;
        try {
            productoDao.delete(id);
            System.out.println("Producto con ID " + id + " eliminado.");
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }
}
