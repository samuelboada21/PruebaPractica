package com.prueba.services.implement;

import com.prueba.dao.implement.DetalleDaoJdbc;
import com.prueba.dao.implement.FacturaDaoJdbc;
import com.prueba.dao.interfaces.DetalleDaoInterface;
import com.prueba.dao.interfaces.FacturaDaoInterface;
import com.prueba.models.Detalle;
import com.prueba.models.Factura;
import com.prueba.services.interfaces.FacturaServiceInterface;
import java.util.List;

/**
 *
 * @author Samuel
 */
public class FacturaService implements FacturaServiceInterface {

    private FacturaDaoInterface facturaDao = new FacturaDaoJdbc();
    private DetalleDaoInterface detalleDao = new DetalleDaoJdbc();

    @Override
    public List<Factura> listarFacturas() {
        return facturaDao.findAll();
    }

    @Override
    public Factura buscarFactura(Integer id) {
        return facturaDao.findById(id);
    }

//    @Override
//    public void añadirFactura(Factura factura, List<Detalle> detalles) {
//        facturaDao.save(factura, detalles);
//    }
    @Override
    public void añadirFactura(Factura factura, List<Detalle> detalles) {
        int idFact = facturaDao.save(factura);
//        for (Detalle detalle : detalles) {
//            detalle.setFactura(factura);
        detalleDao.saveAll(detalles, idFact);
//        }
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
