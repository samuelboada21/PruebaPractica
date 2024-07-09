
package com.prueba.services.implement;

import com.prueba.dao.implement.DetalleDaoJdbc;
import com.prueba.dao.interfaces.DetalleDaoInterface;
import com.prueba.models.Detalle;
import com.prueba.services.interfaces.DetalleServiceInterface;
import java.util.List;

/**
 *
 * @author Samuel
 */
public class DetalleService implements DetalleServiceInterface{

    private DetalleDaoInterface detalleDao = new DetalleDaoJdbc();

    @Override
    public List<Detalle> listarDetallesFactura() {
        return detalleDao.findAllbyFacturaId();
    }

    @Override
    public Detalle buscarDetalle(Integer id) {
       return detalleDao.findById(id);
    }

    @Override
    public void añadirDetalle(Detalle detalle) {
        detalleDao.save(detalle);
    }

    @Override
    public void actualizarDetalle(int id, Detalle detalle) {
        detalleDao.update(id, detalle);
    }

    @Override
    public void eliminarDetalle(int id) {
        detalleDao.delete(id);
    }
    
}
