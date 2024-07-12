package com.prueba.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prueba.models.Producto;
import com.prueba.services.implement.ProductoService;
import com.prueba.services.interfaces.ProductoServiceInterface;
import com.prueba.util.MessagesResponse;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/api/productos/*")
public class ProductoServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final ProductoServiceInterface productoService = new ProductoService();
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getPathInfo();
        if (action == null || action.equals("/")) {
            listarProductos(request, response);
        } else if (action.matches("/\\d+")) {
            mostrarProductoPorId(request, response);
        } else {
            enviarResponse(response, HttpServletResponse.SC_BAD_REQUEST, "Ruta inválida");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getPathInfo();
        if (action != null && action.matches("/create")) {
            crearProducto(request, response);
        } else {
            enviarResponse(response, HttpServletResponse.SC_BAD_REQUEST, "Ruta inválida");
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getPathInfo();
        if (action != null && action.matches("/update/\\d+")) {
            actualizarProducto(request, response);
        } else {
            enviarResponse(response, HttpServletResponse.SC_BAD_REQUEST, "Ruta inválida");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getPathInfo();
        if (action != null && action.matches("/delete/\\d+")) {
            eliminarProducto(request, response);
        } else {
            enviarResponse(response, HttpServletResponse.SC_BAD_REQUEST, "Ruta inválida");
        }
    }

    private void listarProductos(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Producto> productos = productoService.listarProductos();
        String jsonProductos = mapper.writeValueAsString(productos);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonProductos);
    }

    private void mostrarProductoPorId(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getPathInfo();
        int id = Integer.parseInt(action.substring(1)); // elimina el "/"
        Producto producto = productoService.buscarProducto(id);
        if (producto != null) {
            String jsonProducto = mapper.writeValueAsString(producto);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(jsonProducto);
        } else {
            enviarResponse(response, HttpServletResponse.SC_NOT_FOUND, "Producto no encontrado");
        }
    }

    private void crearProducto(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Producto nuevoProducto = mapper.readValue(request.getInputStream(), Producto.class);
        productoService.añadirProducto(nuevoProducto);
        enviarResponse(response, HttpServletResponse.SC_CREATED, "Se creó exitosamente el producto");
    }

    private void actualizarProducto(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getPathInfo();
        int id = Integer.parseInt(action.substring(8)); // elimina el "/update/"
        Producto encontrarProducto = productoService.buscarProducto(id);
        if (encontrarProducto != null) {
            Producto productoActualizado = mapper.readValue(request.getReader(), Producto.class);
            productoActualizado.setId(id);
            productoService.actualizarProducto(id, productoActualizado);
            enviarResponse(response, HttpServletResponse.SC_OK, "Se actualizó correctamente el producto");
        } else {
            enviarResponse(response, HttpServletResponse.SC_NOT_FOUND, "Producto no encontrado");
        }
    }

    private void eliminarProducto(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getPathInfo();
        int id = Integer.parseInt(action.substring(8)); // elimina el "/delete/"
        Producto encontrarProducto = productoService.buscarProducto(id);
        if (encontrarProducto != null) {
            productoService.eliminarProducto(id);
            enviarResponse(response, HttpServletResponse.SC_OK, "Producto eliminado correctamente");
        }else{
            enviarResponse(response, HttpServletResponse.SC_NOT_FOUND, "Producto no encontrado");
        }
    }
    
    private void enviarResponse(HttpServletResponse response, int statusCode, String mensaje)
            throws IOException {
        //Se construye el objeto
        MessagesResponse messageResponse = new MessagesResponse(statusCode, mensaje);
        String jsonResponse = mapper.writeValueAsString(messageResponse);
        response.setContentType("application/json"); //se transforma a json
        response.setCharacterEncoding("UTF-8");
        response.setStatus(statusCode); // estado
        response.getWriter().write(jsonResponse); //muestra el mensaje
    }
}
