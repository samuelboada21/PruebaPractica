package com.prueba.dao.implement;

import com.prueba.dao.interfaces.DetalleDaoInterface;
import com.prueba.dao.interfaces.FacturaDaoInterface;
import com.prueba.dao.interfaces.ProductoDaoInterface;
import com.prueba.dbconnection.Conexion;
import com.prueba.models.Detalle;
import com.prueba.models.Factura;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Samuel
 */
public class FacturaDaoJdbc implements FacturaDaoInterface {

    //Consultas SQL JDBC
    private static final String SELECT_FACTURAS = "SELECT * FROM facturas";
    private static final String SELECT_FACTURA_ID = "SELECT * FROM facturas WHERE id = ?";
    private static final String INSERT_FACTURA = "INSERT INTO facturas(nombre_cliente, fecha, subtotal, iva, total) VALUES (?,?,?,?,?)";
    private static final String UPDATE_FACTURA = "UPDATE facturas SET nombre_cliente = ?, fecha = ?, subtotal = ?, iva = ?, total = ? WHERE id = ?";
    private static final String DELETE_FACTURA = "DELETE FROM facturas WHERE id = ?";

    //Consulta SQL JDBC para Insertar Detalle
    private static final String INSERT_DETALLE = "INSERT INTO detalles(cantidad, valor, factura_id, producto_id) VALUES (?,?,?,?)";
    private static final String DELETE_DETALLE_FACTURA = "DELETE FROM detalles WHERE factura_id = ?";

    private ProductoDaoInterface productoDao = new ProductoDaoJdbc();
    private DetalleDaoInterface detalleDao = new DetalleDaoJdbc();

    //Variables
    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    //Metodos
    @Override
    public List<Factura> findAll() {
        List<Factura> facturas = new ArrayList<Factura>();
        try {
            con = Conexion.getConnection();
            ps = con.prepareStatement(SELECT_FACTURAS);
            rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre_cliente = rs.getString("nombre_cliente");
                Date fecha = rs.getDate("fecha");
                double subtotal = rs.getDouble("subtotal");
                double iva = rs.getDouble("iva");
                double total = rs.getDouble("total");
                facturas.add(new Factura(id, nombre_cliente, fecha, subtotal, iva, total));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conexion.close(rs);
            Conexion.close(con);
            Conexion.close(ps);
        }
        return facturas;
    }

    @Override
    public Factura findById(int id) {
        Factura factura = null;
        try {
            con = Conexion.getConnection();
            ps = con.prepareStatement(SELECT_FACTURA_ID);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                String nombre_cliente = rs.getString("nombre_cliente");
                Date fecha = rs.getDate("fecha");
                double subtotal = rs.getDouble("subtotal");
                double iva = rs.getDouble("iva");
                double total = rs.getDouble("total");
                factura = new Factura(id, nombre_cliente, fecha, subtotal, iva, total);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conexion.close(rs);
            Conexion.close(con);
            Conexion.close(ps);
        }
        return factura;
    }

    @Override
    public void save(Factura factura, List<Detalle> detalles) {
        java.sql.Date dateSQL = new java.sql.Date(factura.getFecha().getTime());
        Connection con = null;
        PreparedStatement psFactura = null;
        PreparedStatement psDetalle = null;
        ResultSet rs = null;
        try {
            con = Conexion.getConnection();
            con.setAutoCommit(false);
            // Guarda la factura
            psFactura = con.prepareStatement(INSERT_FACTURA, Statement.RETURN_GENERATED_KEYS);
            psFactura.setString(1, factura.getNombreCliente());
            psFactura.setDate(2, dateSQL);
            psFactura.setDouble(3, factura.getSubtotal());
            psFactura.setDouble(4, factura.getIva());
            psFactura.setDouble(5, factura.getTotal());
            psFactura.executeUpdate();
            rs = psFactura.getGeneratedKeys();
            int facturaId = 0;
            if (rs.next()) {
                facturaId = rs.getInt(1);
            }
            // Guarda los detalles de la factura
            psDetalle = con.prepareStatement(INSERT_DETALLE);
            for (Detalle detalle : detalles) {
                psDetalle.setInt(1, detalle.getCantidad());
                psDetalle.setDouble(2, detalle.getValor());
                psDetalle.setInt(3, facturaId);
                psDetalle.setInt(4, detalle.getProducto().getId());
                psDetalle.addBatch();
            }
            psDetalle.executeBatch();
            // Confirmamos la transacción
            con.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                if (con != null) {
                    con.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                if (con != null) {
                    con.setAutoCommit(true);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            Conexion.close(rs);
            Conexion.close(psFactura);
            Conexion.close(psDetalle);
            Conexion.close(con);
        }
    }

    @Override
    public void update(int id, Factura factura) {
        try {
            con = Conexion.getConnection();
            ps = con.prepareStatement(UPDATE_FACTURA);
            ps.setString(1, factura.getNombreCliente());
            ps.setDate(2, new java.sql.Date(factura.getFecha().getTime()));
            ps.setDouble(3, factura.getSubtotal());
            ps.setDouble(4, factura.getIva());
            ps.setDouble(5, factura.getTotal());
            ps.setInt(6, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conexion.close(con);
            Conexion.close(ps);
        }
    }

    @Override
    public void delete(int id) {
        Connection con = null;
        PreparedStatement psFactura = null;
        PreparedStatement psDetalle = null;
        try {
            con = Conexion.getConnection();
            con.setAutoCommit(false);

            // Eliminar los detalles asociados a la factura
            psDetalle = con.prepareStatement(DELETE_DETALLE_FACTURA);
            psDetalle.setInt(1, id);
            psDetalle.executeUpdate();
            Conexion.close(psDetalle);

            // Eliminar la factura
            psFactura = con.prepareStatement(DELETE_FACTURA);
            psFactura.setInt(1, id);
            psFactura.executeUpdate();

            // Confirmamos la transacción
            con.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                if (con != null) {
                    con.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                if (con != null) {
                    con.setAutoCommit(true);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            Conexion.close(psFactura);
            Conexion.close(con);
        }
    }
}
