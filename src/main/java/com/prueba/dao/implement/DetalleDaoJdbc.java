package com.prueba.dao.implement;

import com.prueba.dao.interfaces.DetalleDaoInterface;
import com.prueba.dao.interfaces.ProductoDaoInterface;
import com.prueba.dbconnection.Conexion;
import com.prueba.models.Detalle;
import com.prueba.models.Producto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Samuel
 */
public class DetalleDaoJdbc implements DetalleDaoInterface {

    //Consultas SQL JDBC
    private static final String SELECT_DETALLES = "SELECT * FROM detalles";
    private static final String SELECT_DETALLES_FACTURA = "SELECT * FROM detalles WHERE factura_id = ?";
    private static final String SELECT_DETALLE_ID = "SELECT * FROM detalles WHERE id = ?";
    private static final String INSERT_DETALLE = "INSERT INTO detalles(cantidad, valor, factura_id, producto_id) VALUES (?,?,?,?)";
    private static final String UPDATE_DETALLE = "UPDATE detalles SET cantidad = ?, valor = ?, factura_id= ?, producto_id= ? WHERE id = ?";
    private static final String DELETE_DETALLE = "DELETE FROM detalles WHERE id = ?";

    private ProductoDaoInterface productoDao = new ProductoDaoJdbc();
    //Variables
    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    //Metodos
    @Override
    public List<Detalle> findAll() {
        List<Detalle> detalles = new ArrayList<Detalle>();
        Producto producto = null;
        try {
            con = Conexion.getConnection();
            ps = con.prepareStatement(SELECT_DETALLES);
            rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                int cantidad = rs.getInt("cantidad");
                int producto_id = rs.getInt("producto_id");
                producto = productoDao.findById(producto_id);
                detalles.add(new Detalle(id, cantidad, producto));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conexion.close(rs);
            Conexion.close(con);
            Conexion.close(ps);
        }
        return detalles;
    }

    @Override
    public List<Detalle> findDetallesByFactura(int idFactura) {
        List<Detalle> detalles = new ArrayList<>();
        Producto producto = null;
        try {
            con = Conexion.getConnection();
            ps = con.prepareStatement(SELECT_DETALLES_FACTURA);
            ps.setInt(1, idFactura);
            rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                int cantidad = rs.getInt("cantidad");
                int producto_id = rs.getInt("producto_id");
                producto = productoDao.findById(producto_id);
                Detalle detalle = new Detalle(id, cantidad, producto);
                detalles.add(detalle);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conexion.close(rs);
            Conexion.close(con);
            Conexion.close(ps);
        }
        return detalles;
    }

    @Override
    public Detalle findDetalleById(int id) {
        Detalle detalle = null;
        Producto producto = null;
        try {
            con = Conexion.getConnection();
            ps = con.prepareStatement(SELECT_DETALLE_ID);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                int cantidad = rs.getInt("cantidad");
                int producto_id = rs.getInt("producto_id");
                producto = productoDao.findById(producto_id);
                detalle = new Detalle(id, cantidad, producto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conexion.close(rs);
            Conexion.close(con);
            Conexion.close(ps);
        }
        return detalle;
    }

    @Override
    public void saveDetalle(Detalle detalle, int idFact) {
        try {
            con = Conexion.getConnection();
            ps = con.prepareStatement(INSERT_DETALLE);
            ps.setInt(1, detalle.getCantidad());
            ps.setDouble(2, detalle.getValor());
            ps.setInt(3, idFact);
            ps.setInt(4, detalle.getProducto().getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } 
//        finally {
//            Conexion.close(con);
//            Conexion.close(ps);
//        }
    }

    @Override
    public void updateDetalle(int id, Detalle detalle, int idFact) {
        try {
            con = Conexion.getConnection();
            ps = con.prepareStatement(UPDATE_DETALLE);
            ps.setInt(1, detalle.getCantidad());
            ps.setDouble(2, detalle.getValor());
            ps.setInt(3, idFact);
            ps.setInt(4, detalle.getProducto().getId());
            ps.setInt(5, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conexion.close(con);
            Conexion.close(ps);
        }
    }

    @Override
    public void deleteDetalle(int id) {
        try {
            con = Conexion.getConnection();
            ps = con.prepareStatement(DELETE_DETALLE);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (ps != null) {
                Conexion.close(ps);
            }
            Conexion.close(con);
        }
    }

    @Override
    public void saveAllDetalle(List<Detalle> detalles, int idFact) {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = Conexion.getConnection();
            for (Detalle detalle : detalles) {
                ps = con.prepareStatement(INSERT_DETALLE);
                ps.setInt(1, detalle.getCantidad());
                ps.setDouble(2, detalle.getValor());
                ps.setInt(3, idFact);
                ps.setInt(4, detalle.getProducto().getId());
                ps.executeUpdate();
                Conexion.close(ps);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                if (con != null) {
                    con.rollback();
                }
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
        } finally {
            Conexion.close(con);
        }
    }
}
