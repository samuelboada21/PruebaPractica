
package com.prueba.services.implement;

import com.prueba.dao.implement.FacturaDaoJdbc;
import com.prueba.dao.interfaces.FacturaDaoInterface;
import com.prueba.models.Factura;
import com.prueba.services.interfaces.FacturaServiceInterface;
import java.util.List;

/**
 *
 * @author Samuel
 */
public class FacturaService implements FacturaServiceInterface{
    
    private FacturaDaoInterface facturaDao = new FacturaDaoJdbc();

    @Override
    public List<Factura> listarFacturas() {
        return facturaDao.findAll();
    }

    @Override
    public Factura buscarFactura(Integer id) {
       return facturaDao.findById(id);
    }

    @Override
    public void añadirFactura(Factura factura) {
        facturaDao.save(factura);
    }

    @Override
    public void actualizarFactura(int id, Factura factura) {
        facturaDao.update(id, factura);
    }

    @Override
    public void eliminarFactura(int id) {
        facturaDao.delete(id);
    }
    
}
