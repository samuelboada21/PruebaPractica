package com.prueba.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prueba.models.Factura;
import com.prueba.services.implement.FacturaService;
import com.prueba.services.implement.ProductoService;
import com.prueba.services.interfaces.FacturaServiceInterface;
import com.prueba.services.interfaces.ProductoServiceInterface;
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
@WebServlet("/api/facturas/*")
public class FacturaServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final FacturaServiceInterface facturaService = new FacturaService();
    private final ProductoServiceInterface productoService = new ProductoService();
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getPathInfo();

        if (action == null || action.equals("/")) {
            listarFacturas(request, response);
        } else if (action.matches("/\\d+")) {
            mostrarFacturaPorId(request, response);
        } else {
            enviarResponse(response, HttpServletResponse.SC_BAD_REQUEST, "Ruta inválida");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getPathInfo();

        if (action != null && action.matches("/create")) {
            crearFactura(request, response);
        } else {
            enviarResponse(response, HttpServletResponse.SC_BAD_REQUEST, "Ruta inválida");
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getPathInfo();

        if (action != null && action.matches("/update/\\d+")) {
            actualizarFactura(request, response);
        } else {
            enviarResponse(response, HttpServletResponse.SC_BAD_REQUEST, "Ruta inválida");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getPathInfo();

        if (action != null && action.matches("/delete/\\d+")) {
            eliminarFactura(request, response);
        } else {
            enviarResponse(response, HttpServletResponse.SC_BAD_REQUEST, "Ruta inválida");
        }
    }

    private void listarFacturas(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Factura> facturas = facturaService.listarFacturas();
        String jsonFacturas = mapper.writeValueAsString(facturas);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonFacturas);
    }

    private void mostrarFacturaPorId(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getPathInfo();
        int id = Integer.parseInt(action.substring(1)); // elimina el "/"

        Factura factura = facturaService.buscarFactura(id);
        if (factura != null) {
            String jsonFactura = mapper.writeValueAsString(factura);

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(jsonFactura);
        } else {
            enviarResponse(response, HttpServletResponse.SC_NOT_FOUND, "Factura no encontrada");
        }
    }

    private void crearFactura(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Factura nuevaFactura = mapper.readValue(request.getInputStream(), Factura.class);

        facturaService.añadirFactura(nuevaFactura);
        enviarResponse(response, HttpServletResponse.SC_CREATED, "Se creó exitosamente la factura y sus detalles");
    }

    private void actualizarFactura(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getPathInfo();
        int id = Integer.parseInt(action.substring(8)); // elimina el "/update/"
        Factura encontrarFactura = facturaService.buscarFactura(id);
        if (encontrarFactura != null) {
            Factura facturaActualizada = mapper.readValue(request.getReader(), Factura.class);
            facturaActualizada.setId(id);
            facturaService.actualizarFactura(id, facturaActualizada);
            enviarResponse(response, HttpServletResponse.SC_OK, "Se actualizó correctamente la factura");
        } else {
            enviarResponse(response, HttpServletResponse.SC_NOT_FOUND, "Factura no encontrada");
        }
    }

    private void eliminarFactura(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getPathInfo();
        int id = Integer.parseInt(action.substring(8)); // elimina el "/delete/"
        Factura encontrarFactura = facturaService.buscarFactura(id);
        if (encontrarFactura != null) {
            facturaService.eliminarFactura(id);
            enviarResponse(response, HttpServletResponse.SC_OK, "Factura eliminada correctamente");
        } else {
            enviarResponse(response, HttpServletResponse.SC_NOT_FOUND, "Factura no encontrada");
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
