package com.prueba.services.implement;

import com.prueba.dao.implement.DetalleDaoJdbc;
import com.prueba.dao.interfaces.DetalleDaoInterface;
import com.prueba.models.Detalle;
import com.prueba.services.interfaces.DetalleServiceInterface;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Samuel
 */
public class DetalleService implements DetalleServiceInterface {

    private DetalleDaoInterface detalleDao = new DetalleDaoJdbc();

    @Override
    public List<Detalle> listarDetalles() {
        return detalleDao.findAll();
    }

    public List<Detalle> listarDetallesFactura(int idFactura) {
        return detalleDao.findDetallesByFactura(idFactura);
    }

    @Override
    public Detalle buscarDetalle(Integer id) {
        return detalleDao.findDetalleById(id);
    }

    @Override
    public void añadirDetalle(Detalle detalle, int idFact) {
        detalleDao.saveDetalle(detalle, idFact);
    }

    @Override
    public void añadirDetalles(List<Detalle> detalles, int idFact) {
        detalleDao.saveAllDetalle(detalles, idFact);
    }

    @Override
    public void actualizarDetalle(int id, Detalle detalle, int idFact) {
        detalleDao.updateDetalle(id, detalle, idFact);
    }

    @Override
    public void eliminarDetalle(int id) {
        detalleDao.deleteDetalle(id);
    }

}
