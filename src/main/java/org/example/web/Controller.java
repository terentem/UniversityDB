package org.example.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.service.UniversityService;
import org.example.utilits.DBConnector;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

//@WebServlet(name = "UniversityServise", value = "/get")
public class Controller extends HttpServlet {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        String parameterForService = request.getParameter("parameter");
        System.out.println("Отриманий параметр = " + parameterForService);
        try {
            Connection conn = DBConnector.getConnection();
            UniversityService service = new UniversityService(conn);
            String jsonResult = objectMapper.writeValueAsString(service.getServiceResult(parameterForService));
            response.getWriter().write(jsonResult);
            response.getWriter().flush();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}