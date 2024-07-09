
package com.prueba.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prueba.models.Detalle;
import com.prueba.services.implement.DetalleService;
import com.prueba.services.interfaces.DetalleServiceInterface;
import com.prueba.util.MessagesResponse;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Samuel
 */
@WebServlet("/api/detalle")
public class DetalleServlet extends HttpServlet{
    private static final long serialVersionUID = 1L;
    private final DetalleServiceInterface detalleService = new DetalleService();
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getPathInfo();

        if (action == null || action.equals("/")) {
            listarDetalles(request, response);
        } else if (action.matches("/\\d+")) {
            mostrarDetallePorId(request, response);
        } else {
            enviarResponse(response, HttpServletResponse.SC_BAD_REQUEST, "Ruta inválida");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getPathInfo();

        if (action != null && action.matches("/create")) {
            crearDetalle(request, response);
        } else {
            enviarResponse(response, HttpServletResponse.SC_BAD_REQUEST, "Ruta inválida");
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getPathInfo();

        if (action != null && action.matches("/update/\\d+")) {
            actualizarDetalle(request, response);
        } else {
            enviarResponse(response, HttpServletResponse.SC_BAD_REQUEST, "Ruta inválida");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getPathInfo();

        if (action != null && action.matches("/delete/\\d+")) {
            eliminarDetalle(request, response);
        } else {
            enviarResponse(response, HttpServletResponse.SC_BAD_REQUEST, "Ruta inválida");
        }
    }

    private void listarDetalles(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Detalle> detalles = detalleService.listarDetallesFactura();
        String jsonFacturas = mapper.writeValueAsString(detalles);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonFacturas);
    }

    private void mostrarDetallePorId(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getPathInfo();
        int id = Integer.parseInt(action.substring(1)); // elimina el "/"

        Detalle detalle = detalleService.buscarDetalle(id);
        if (detalle != null) {
            String jsonDetalle = mapper.writeValueAsString(detalle);

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(jsonDetalle);
        } else {
            enviarResponse(response, HttpServletResponse.SC_NOT_FOUND, "Especificación no encontrada");
        }
    }

    private void crearDetalle(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Detalle nuevoDetalle = mapper.readValue(request.getInputStream(), Detalle.class);

        detalleService.añadirDetalle(nuevoDetalle);
        enviarResponse(response, HttpServletResponse.SC_CREATED, "Se creó exitosamente la especificación");
    }

    private void actualizarDetalle(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getPathInfo();
        int id = Integer.parseInt(action.substring(8)); // elimina el "/update/"
        Detalle encontrarDetalle = detalleService.buscarDetalle(id);
        if (encontrarDetalle != null) {
            Detalle detalleActualizado = mapper.readValue(request.getReader(), Detalle.class);
            detalleActualizado.setId(id);
            detalleService.actualizarDetalle(id, detalleActualizado);
            enviarResponse(response, HttpServletResponse.SC_OK, "Se actualizó correctamente la especificación");
        } else {
            enviarResponse(response, HttpServletResponse.SC_NOT_FOUND, "Detalle no encontrado");
        }
    }

    private void eliminarDetalle(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getPathInfo();
        int id = Integer.parseInt(action.substring(8)); // elimina el "/delete/"
        Detalle encontrarDetalle = detalleService.buscarDetalle(id);
        if (encontrarDetalle != null) {
            detalleService.eliminarDetalle(id);
            enviarResponse(response, HttpServletResponse.SC_OK, "Especificación eliminada correctamente");
        }else{
            enviarResponse(response, HttpServletResponse.SC_NOT_FOUND, "No se encuentra la especificación");
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
