package com.prueba.dao.implement;

import com.prueba.dao.interfaces.FacturaDaoInterface;
import com.prueba.dbconnection.Conexion;
import com.prueba.models.Factura;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    private static final String INSERT_FACTURA = "INSERT INTO facturas(num_factura, nombre_cliente, fecha, iva) VALUES (?,?,?,?)";
    private static final String UPDATE_FACTURA = "UPDATE facturas SET num_factura = ?, nombre_cliente = ?, fecha = ?, iva = ? WHERE id = ?";
    private static final String DELETE_FACTURA = "DELETE FROM facturas WHERE id = ?";

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
                int num_factura = rs.getInt("num_factura");
                String nombre_cliente = rs.getString("nombre_cliente");
                Date fecha = rs.getDate("fecha");
                double iva = rs.getDouble("iva");
                facturas.add(new Factura(id, num_factura, nombre_cliente, fecha, iva));
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
                int num_factura = rs.getInt("num_factura");
                String nombre_cliente = rs.getString("nombre_cliente");
                Date fecha = rs.getDate("fecha");
                double iva = rs.getDouble("iva");
                factura = new Factura(id, num_factura, nombre_cliente, fecha, iva);
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
    public void save(Factura factura) {
        try {
            con = Conexion.getConnection();
            ps = con.prepareStatement(INSERT_FACTURA);
            ps.setInt(1, factura.getNum_factura());
            ps.setString(2, factura.getNombre_cliente());
            ps.setDate(3, (java.sql.Date) factura.getFecha());
            ps.setDouble(4, factura.getIva());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conexion.close(con);
            Conexion.close(ps);
        }
    }

    @Override
    public void update(int id, Factura factura) {
        try {
            con = Conexion.getConnection();
            ps = con.prepareStatement(UPDATE_FACTURA);
            ps.setInt(1, factura.getNum_factura());
            ps.setString(2, factura.getNombre_cliente());
            ps.setDate(3, (java.sql.Date) factura.getFecha());
            ps.setDouble(4, factura.getIva());
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
