package org.university.web;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.ThreadContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.university.web.controller.ProfessorController;
import org.university.web.controller.StudentController;
import org.university.web.dto.student.RequestStudentMapper;
import org.university.web.utilities.professor.ProfessorValidator;
import org.university.web.utilities.student.StudentValidator;

import java.io.IOException;
import java.sql.SQLException;
import java.util.UUID;


public class DispatcherServlet extends HttpServlet {
    private final ProfessorController professorController;
    private final StudentController studentController;

    private static final Logger log = LoggerFactory.getLogger(DispatcherServlet.class);

    public DispatcherServlet(ProfessorController professorController, StudentController studentController) {
        this.professorController = professorController;
        this.studentController = studentController;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String correlationId = UUID.randomUUID().toString();
        ThreadContext.put("X-Flow-Id", correlationId);
        String pathFromOriginHttpRequest = "/" + request.getPathInfo().split("/")[1];
        String getPathInfo = request.getPathInfo();
        Integer pathVariable = RequestStudentMapper.getPathVariable(getPathInfo);
        log.info("Real http path = {}", getPathInfo);
        try {
            switch (pathFromOriginHttpRequest) {
                case "/professors":
                    ProfessorValidator.checkGetPath(getPathInfo);
                    log.info("pathVariable= {}", pathVariable);
                    professorController.readProfessors(pathVariable, response);
                    break;
                case "/students":
                    StudentValidator.checkGetPath(getPathInfo);
                    log.info("pathVariable= {}", pathVariable);
                    studentController.read(pathVariable, response);
                    break;
                default:
                    response.sendError(404);
                    log.error("404. Path {} doesn`t exists.",
                            request.getPathInfo()
                    );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ThreadContext.clearAll();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        prepareDataForLogging();
        String pathFromOriginHttpRequest = getShortPath(request.getPathInfo());
        try {
            switch (pathFromOriginHttpRequest) {
                case "/professors":
                    ProfessorValidator.checkPostHttpPath(request.getPathInfo());
                    professorController.createProfessor(request, response);
                    break;
                case "/students":
                    StudentValidator.checkPostHttpPath(request.getPathInfo());
                    studentController.create(request, response);
                    break;
                default:
                    response.sendError(404);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ThreadContext.clearAll();
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        prepareDataForLogging();
        String pathFromOriginHttpRequest = getShortPath(request.getPathInfo());
        Integer pathVariable = RequestStudentMapper.getPathVariable(request.getPathInfo());
        log.info("pathVariable= {}", pathVariable);
        // 2. Маршрутизуємо запит залежно від URL
        try {
            switch (pathFromOriginHttpRequest) {
                case "/professor":
                    ProfessorValidator.checkPutDeletePath(request.getPathInfo());
                    professorController.updateProfessor(request, response, pathVariable);
                    break;
                case "/students":
                    StudentValidator.checkPutDeletePath(request.getPathInfo());
                    log.info("Successfull path check for /students");
                    studentController.update(request, response, pathVariable);
                    break;
                default:
                    response.sendError(404);
            }
        } catch (RuntimeException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        prepareDataForLogging();
        String pathFromOriginHttpRequest = getShortPath(request.getPathInfo());
        Integer pathVariable = RequestStudentMapper.getPathVariable(request.getPathInfo());
        // 2. Маршрутизуємо запит залежно від URL
        try {
            switch (pathFromOriginHttpRequest) {
                case "/professors":
                    ProfessorValidator.checkDeletetHttpBody(pathVariable);
                    log.info("pathVariable= {}", pathVariable);
                    professorController.deleteProfessor(pathVariable, response);
                    break;
                case "/students":
                    StudentValidator.checkDeletetHttpBody(pathVariable);
                    log.info("/students pathVariable= {}", pathVariable);
                    studentController.delete(pathVariable, response);
                    break;
                default:
                    response.sendError(404);
            }
        } catch (RuntimeException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getShortPath(String getPathInfo) {
        String pathFromOriginHttpRequest = "/" + getPathInfo.split("/")[1];
        log.info(" http path[1] = {}, request.getPathInfo()={} ", pathFromOriginHttpRequest, getPathInfo);
        return pathFromOriginHttpRequest;
    }

    public static void prepareDataForLogging() {
        String correlationId = UUID.randomUUID().toString();
        ThreadContext.put("X-Flow-Id", correlationId);
    }
}
