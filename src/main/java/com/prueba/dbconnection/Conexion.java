package com.prueba.dbconnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Samuel
 */
public class Conexion {

    public static final String url_db = "jdbc:postgresql://localhost:5432/practica_prueba";
    private static final String user_db = "postgres";
    private static final String password_db = "MySQL2121";
    private static final String driver = "org.postgresql.Driver";

//    static {
//        try {
//            Class.forName(driver);
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//    }
    public static Connection getConnection() {
        try {
            Class.forName(driver);
            return DriverManager.getConnection(url_db, user_db, password_db);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void close(ResultSet rs) {
        try {
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void close(Connection con) {
        try {
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    public static void close(PreparedStatement ps){
        try {
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
