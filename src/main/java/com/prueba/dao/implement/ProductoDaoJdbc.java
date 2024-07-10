package com.prueba.dao.implement;

import com.prueba.dao.interfaces.ProductoDaoInterface;
import com.prueba.dbconnection.Conexion;
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
public class ProductoDaoJdbc implements ProductoDaoInterface {

    //Consultas SQL JDBC
    private static final String SELECT_PRODUCTOS = "SELECT * FROM productos";
    private static final String SELECT_PRODUCTO_ID = "SELECT * FROM productos WHERE id = ?";
    private static final String INSERT_PRODUCTO = "INSERT INTO productos(nombre, precio) VALUES (?,?)";
    private static final String UPDATE_PRODUCTO = "UPDATE productos SET nombre = ?, precio = ? WHERE id = ?";
    private static final String DELETE_PRODUCTO = "DELETE FROM productos WHERE id = ?";

    //Variables
    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    //Metodos
    @Override
    public List<Producto> findAll() {
        List<Producto> productos = new ArrayList<Producto>();
        try {
            con = Conexion.getConnection();
            ps = con.prepareStatement(SELECT_PRODUCTOS);
            rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                double precio = rs.getDouble("precio");
                productos.add(new Producto(id, nombre, precio));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conexion.close(rs);
            Conexion.close(con);
            Conexion.close(ps);
        }
        return productos;
    }

    @Override
    public Producto findById(int id) {
        Producto producto = null;
        try {
            con = Conexion.getConnection();
            ps = con.prepareStatement(SELECT_PRODUCTO_ID);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                String nombre = rs.getString("nombre");
                double precio = rs.getDouble("precio");
                producto = new Producto(id, nombre, precio);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conexion.close(rs);
            Conexion.close(con);
            Conexion.close(ps);
        }
        return producto;
    }

    @Override
    public void save(Producto producto) {
        try {
            con = Conexion.getConnection();
            ps = con.prepareStatement(INSERT_PRODUCTO);
            ps.setString(1, producto.getNombre());
            ps.setDouble(2, producto.getPrecio());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conexion.close(con);
            Conexion.close(ps);
        }
    }

    @Override
    public void update(int id, Producto producto) {
        try {
            con = Conexion.getConnection();
            ps = con.prepareStatement(UPDATE_PRODUCTO);
            ps.setString(1, producto.getNombre());
            ps.setDouble(2, producto.getPrecio());
            ps.setInt(3, id);

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
            ps = con.prepareStatement(DELETE_PRODUCTO);
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
