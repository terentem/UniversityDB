package org.university.web;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.ThreadContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.university.web.controller.ProfessorController;
import org.university.web.dto.InputProfessorDto;
import org.university.web.utilities.HttpValidator;
import org.university.web.utilities.RequestDataProvider;

import java.io.IOException;
import java.util.Map;
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
        String correlationId = UUID.randomUUID().toString();
        ThreadContext.put("X-Flow-Id", correlationId);
        String pathFromOriginHttpRequest = "/" + request.getPathInfo().split("/")[1];
        String getPathInfo = request.getPathInfo();
        log.info("Real http path = {}", getPathInfo);
        try {
            switch (pathFromOriginHttpRequest) {
                case "/professor":
                    HttpValidator.checkGetPath(getPathInfo);
                    String pathVariable = RequestDataProvider.getPathVariable(getPathInfo);
                    log.info("pathVariable= {}", pathVariable);
                    professorController.readProfessors(pathVariable, response);
                    break;
                default:
                    response.sendError(404);
                    log.error("404. Path {} doesn`t exists.",
                            request.getPathInfo()
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
        String correlationId = UUID.randomUUID().toString();
        ThreadContext.put("X-Flow-Id", correlationId);
        String pathFromOriginHttpRequest = "/" + request.getPathInfo().split("/")[1];
        String getPathInfo = request.getPathInfo();
        log.info(" http path[1] = {}, request.getPathInfo()={} ", pathFromOriginHttpRequest, getPathInfo);
        switch (pathFromOriginHttpRequest) {
            case "/professor":
                Map<String, String> body = RequestDataProvider.getParameters(request.getParameterMap());
                String stringBody = HttpValidator.checkPostHttpBody(body);
                log.info("Отримані параметри в body = {} ", stringBody);
                HttpValidator.checkPostPutHttpPath(getPathInfo);
                InputProfessorDto inputProfessorDto = RequestDataProvider.mapToInputPostProfessorDto(body);
                professorController.createProfessor(inputProfessorDto, response);
                break;
            default:
                response.sendError(404);
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String correlationId = UUID.randomUUID().toString();
        ThreadContext.put("X-Flow-Id", correlationId);
        String pathFromOriginHttpRequest = "/" + request.getPathInfo().split("/")[1];
        String getPathInfo = request.getPathInfo();
        log.info(" http path[1] = {}, request.getPathInfo()={} ", pathFromOriginHttpRequest, getPathInfo);
        // 2. Маршрутизуємо запит залежно від URL
        switch (pathFromOriginHttpRequest) {
            case "/professor":
                Map<String, String> body = RequestDataProvider.getParameters(request.getParameterMap());
                String stringBody = HttpValidator.checkPUTtHttpBody(body);
                log.info("Отримані параметри в body = {} ", stringBody);
                HttpValidator.checkPostPutHttpPath(getPathInfo);
                InputProfessorDto inputProfessorDto = RequestDataProvider.mapToInputPutProfessorDto(body);
                professorController.updateProfessor(inputProfessorDto, response);
                break;
            default:
                response.sendError(404);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String correlationId = UUID.randomUUID().toString();
        ThreadContext.put("X-Flow-Id", correlationId);
        String pathFromOriginHttpRequest = "/" + request.getPathInfo().split("/")[1];
        String getPathInfo = request.getPathInfo();
        log.info(" http path[1] = {}, request.getPathInfo()={} ", pathFromOriginHttpRequest, getPathInfo);
        // 2. Маршрутизуємо запит залежно від URL
        switch (pathFromOriginHttpRequest) {
            case "/professor":
                Map<String, String> body = RequestDataProvider.getParameters(request.getParameterMap());
                String stringBody = HttpValidator.checkDeletetHttpBody(body);
                log.info("Отримані параметри в body = {} ", stringBody);
                HttpValidator.checkDeletePath(getPathInfo);
                InputProfessorDto inputProfessorDto = RequestDataProvider.mapToDeleteProfessorDto(body);
                professorController.deleteProfessor(inputProfessorDto, response);
                break;
            default:
                response.sendError(404);
        }
    }
}
