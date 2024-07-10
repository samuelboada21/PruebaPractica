package com.prueba.dao.implement;

import com.prueba.dao.interfaces.DetalleDaoInterface;
import com.prueba.dao.interfaces.FacturaDaoInterface;
import com.prueba.dbconnection.Conexion;
import com.prueba.models.Detalle;
import com.prueba.models.Factura;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.sql.Statement;

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

    private DetalleDaoInterface detallesDao = new DetalleDaoJdbc();

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
                List<Detalle> detalles = new ArrayList<>();
                detalles = detallesDao.findAllbyFacturaId(id);
                facturas.add(new Factura(id, nombre_cliente, fecha, subtotal, iva, total, detalles));
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
                List<Detalle> detalles = new ArrayList<>();
                detalles = detallesDao.findAllbyFacturaId(id);
                factura = new Factura(id, nombre_cliente, fecha, subtotal, iva, total, detalles);
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
    public int save(Factura factura) {
        java.sql.Date dateSQL = new java.sql.Date(factura.getFecha().getTime());
        int generatedId = -1;
        try {
            con = Conexion.getConnection();
            con.setAutoCommit(false); // Iniciar transacción
            ps = con.prepareStatement(INSERT_FACTURA, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, factura.getNombreCliente());
            ps.setDate(2, dateSQL);
            ps.setDouble(3, factura.getSubtotal());
            ps.setDouble(4, factura.getIva());
            ps.setDouble(5, factura.getTotal());
            ps.executeUpdate();

            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                generatedId = rs.getInt(1);
            }
            con.commit(); // Hacer commit de la transacción
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                if (con != null) {
                    con.rollback(); // Hacer rollback en caso de error
                }
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
        } finally {
            Conexion.close(con);
            Conexion.close(ps);
            Conexion.close(rs);
        }
        return generatedId;
    }

    @Override
    public void update(int id, Factura factura) {
        try {
            con = Conexion.getConnection();
            ps = con.prepareStatement(UPDATE_FACTURA);
            ps.setString(1, factura.getNombreCliente());
            ps.setDate(2, (java.sql.Date) factura.getFecha());
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
        try {
            con = Conexion.getConnection();
            ps = con.prepareStatement(DELETE_FACTURA);
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
}
