package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.domain.dto.GetProfessorDto;
import org.example.domain.dto.PostProfessorDto;
import org.example.domain.model.Professor;
import org.example.service.ProfessorService;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


public class ProfessorController extends HttpServlet {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        String profEmailForService = request.getParameter("email");
        String profNameForService = request.getParameter("name");
        String params = (profNameForService != null) ? profNameForService : profEmailForService;
        System.out.println("Отриманий параметр = " + params);
        ProfessorService service = new ProfessorService();

        try {
            // Отримуємо Optional з сервісу
            Optional<List<Professor>> optionalResult = service.getProfessors(params);

            // РАЗПАРШУЄМО: якщо Optional порожній, підставляємо порожній список
            List<Professor> professorsList = optionalResult.orElse(Collections.emptyList());
            List<GetProfessorDto> getProfessorDto = professorsList.stream()
                    .map(p -> new GetProfessorDto(p.name(), p.email()))
                    .toList();
            // Серіалізуємо вже чистий List в JSON
            String jsonResult = objectMapper.writeValueAsString(getProfessorDto);

            response.getWriter().write(jsonResult);
            response.getWriter().flush();

        } catch (SQLException ex) {
            // Замість RuntimeException краще віддати клієнту 500 помилку
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"" + ex.getMessage() + "\"}");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        String profNameForService = request.getParameter("name");
        String profEmailForService = request.getParameter("email");
        String profDepartmentForService = request.getParameter("department_id");
        String params = profNameForService + "," + profEmailForService + "," + profDepartmentForService;
        System.out.println("Отриманий параметр = " + params);

        ProfessorService service = new ProfessorService();
        try {
            Optional<List<Professor>> optionalResult = service.postProfessor(params);

            if (optionalResult.isPresent()) {
                response.setStatus(HttpServletResponse.SC_CREATED); // 201 Created
                List<Professor> professorList=optionalResult.orElse(Collections.emptyList());
                List<PostProfessorDto> postProfessorDto=professorList.stream()
                                .map(p->new PostProfessorDto(p.name(),p.email(),p.departmentId()))
                                        .toList();
                response.getWriter().write(objectMapper.writeValueAsString(postProfessorDto));
            } else {
                // Якщо paramsArr.length != 3 або сталася інша помилка валідації
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400 Bad Request
                response.getWriter().write("{\"error\": \"Invalid parameters count or format. Expected: name,email,department_id\"}");
            }
        } catch (SQLException ex) {
            // Замість RuntimeException краще віддати клієнту 500 помилку
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"" + ex.getMessage() + "\"}");
        }
    }
}
