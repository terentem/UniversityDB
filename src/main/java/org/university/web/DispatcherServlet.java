package org.university.web;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.ThreadContext;
import org.university.web.controller.ProfessorController;
import org.university.web.dto.ShortHttpGetRequestDto;
import org.university.web.dto.ShortHttpPostRequestDto;
import org.university.web.utilities.RequestDataProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;


public class DispatcherServlet extends HttpServlet {
    private final ProfessorController professorController;

    private static final Logger log = LoggerFactory.getLogger(DispatcherServlet.class);

    public DispatcherServlet(ProfessorController professorController) {
        this.professorController = professorController;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        ShortHttpGetRequestDto httpRequestDto = RequestDataProvider.createGetHttpRequestDto(request);
        String correlationId = UUID.randomUUID().toString();
        ThreadContext.put("X-Flow-Id", correlationId);

        try {
            String path = httpRequestDto.path();
            String query = Objects.toString(request.getQueryString(), "")
                    .replaceAll("[\r\n]", "_");
            switch (path) {
                case "/professor":
                    log.info(
                            "Incoming request. GetHttpResponseDto={}",
                            httpRequestDto
                    );
                    professorController.readProfessors(httpRequestDto, response);
                    break;
                default:
                    response.sendError(404);
                    log.error("404. Path {} doesn`t exists.",
                            httpRequestDto.fullPath()
                    );
            }
        } finally {
            ThreadContext.clearAll();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        ShortHttpPostRequestDto httpRequestDto = RequestDataProvider.createPostHttpDto(request);
        String path = httpRequestDto.fullPath();
        switch (path) {
            case "/professor":
                professorController.createProfessor(httpRequestDto, response);
                break;
            default:
                response.sendError(404);
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // 1. Отримуємо DTO для PUT запиту через ваш RequestDataProvider
        ShortHttpPostRequestDto httpRequestDto = RequestDataProvider.createPostHttpDto(request);
        log.info("Input parameters = {}",httpRequestDto);
        String path = httpRequestDto.fullPath();

        // 2. Маршрутизуємо запит залежно від URL
        switch (path) {
            case "/professor":
                professorController.updateProfessor(httpRequestDto, response);
                break;
            default:
                response.sendError(404);
        }
    }
}
