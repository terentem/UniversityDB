package org.example.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.util.DBConnector;
import org.example.utilits.ResultCreator;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet(name = "UniversityServise", value = "/get")
public class UniversityService extends HttpServlet {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        String parameterForService = request.getParameter("parameter");

        try {
            Connection conn = DBConnector.getConnection();
            ResultCreator result = new ResultCreator();
            if (parameterForService.equals("professors")) {
                // Jackson:  List<ProfessorDto> в JSON строку
                String jsonResult = objectMapper.writeValueAsString(result.getProfessorsList(conn, parameterForService));
                // JSON  в браузер
                out.print(jsonResult);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

