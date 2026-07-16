package org.example.web;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.ThreadContext;
import org.example.controller.ProfessorController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;


public class DispatcherController extends HttpServlet {
    private final ProfessorController professorController;
    private static final Logger log = LoggerFactory.getLogger(DispatcherController.class);

    public DispatcherController(ProfessorController professorController) {
        this.professorController = professorController;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        Request rq = RequestMapper.mapToRequest(request);
        String correlationId = UUID.randomUUID().toString();
        ThreadContext.put("X-Flow-Id", correlationId);
        try {
            String path = rq.path();
            String query = Objects.toString(request.getQueryString(), "")
                    .replaceAll("[\r\n]", "_");
            switch (path) {
                case "/professor":
                    log.info(
                            "Incoming request. method={}, url={}?{}",
                            rq.method(),
                            request.getRequestURL(),
                            query
                    );
                    log.info("Call \"ProfessorController\" class, method \"readProfessors\"");
                    professorController.readProfessors(rq, response);
                    break;
                default:
                    response.sendError(404);
                    log.error("404. Path {} doesn`t exist",
                            path);
            }
        } finally {
            ThreadContext.clearAll();
        }
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        Request rq = RequestMapper.mapToRequest(request);

        String path = rq.path();
        switch (path) {
            case "/professor":
                professorController.createProfessor(rq, response);
                break;
            default:
                response.sendError(404);
        }
    }
}
