package org.university.web;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.ThreadContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.university.context.ApplicationContext;
import org.university.web.controller.*;
import org.university.web.dto.course.RequestCourseDto;
import org.university.web.dto.course.ResponseCourseDto;
import org.university.web.dto.enrollment.RequestEnrollmentDto;
import org.university.web.dto.enrollment.ResponseEnrollmentDto;
import org.university.web.dto.offering.RequestOfferingDto;
import org.university.web.dto.offering.ResponseOfferingDto;
import org.university.web.dto.professor.RequestProfessorDto;
import org.university.web.dto.professor.ResponseProfessorDto;
import org.university.web.dto.student.RequestStudentDto;
import org.university.web.dto.student.ResponseStudentDto;
import org.university.web.utilities.ResponseWriter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.UUID;


public class DispatcherServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(DispatcherServlet.class);
    private final static String ENTER_LOG_MSG = "Enter to [{}] with pathVariable = [{}] and body [{}]";
    private final static String EXIT_LOG_MSG = "Exit from [{}] with result [{}] with [{}] ms.";

    private final ProfessorController professorController;
    private final StudentController studentController;
    private final CourseController courseController;
    private final OfferingController offeringController;
    private final EnrollmentController enrollmentController;
    private final ReportController reportController;

    public DispatcherServlet(ProfessorController professorController, StudentController studentController,
                             CourseController courseController, OfferingController offeringController,
                             EnrollmentController enrollmentController, ReportController reportController) {
        this.professorController = professorController;
        this.studentController = studentController;
        this.courseController = courseController;
        this.offeringController = offeringController;
        this.enrollmentController = enrollmentController;
        this.reportController = reportController;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        long logStart = System.nanoTime();
        prepareDataForLogging();
        String pathFromOriginHttpRequest = "/" + request.getPathInfo().split("/")[1];
        Map<String, String[]> queryParameters = request.getParameterMap();
        String body = new String(
                request.getInputStream().readAllBytes(),
                StandardCharsets.UTF_8
        );

        try {
            int responseStatus;
            switch (pathFromOriginHttpRequest) {
                case "/professors" -> {
                    Integer pathVariable = getPathVariable(request.getPathInfo());
                    log.info(ENTER_LOG_MSG, "/professors", pathVariable, body);
                    List<ResponseProfessorDto> responseProfessorDto = professorController.readProfessors(pathVariable);
                    responseStatus = responseProfessorDto.isEmpty() ? HttpServletResponse.SC_NOT_FOUND : HttpServletResponse.SC_OK;
                    ResponseWriter.responseSender(responseProfessorDto, responseStatus, response);
                    log.info(EXIT_LOG_MSG, "/professors", responseProfessorDto, (System.nanoTime() - logStart) / 1_000_000);
                }
                case "/students" -> {
                    Integer pathVariable = getPathVariable(request.getPathInfo());
                    log.info(ENTER_LOG_MSG, "/students", pathVariable, "not expected");
                    List<ResponseStudentDto> responseStudentDto = studentController.read(pathVariable);
                    responseStatus = responseStudentDto.isEmpty() ? HttpServletResponse.SC_NOT_FOUND : HttpServletResponse.SC_OK;
                    ResponseWriter.responseSender(responseStudentDto, responseStatus, response);
                    log.info(EXIT_LOG_MSG, "/students", responseStudentDto, (System.nanoTime() - logStart) / 1_000_000);
                }
                case "/courses" -> {
                    Integer pathVariable = getPathVariable(request.getPathInfo());
                    log.info(ENTER_LOG_MSG, "/courses", pathVariable, "not expected");
                    List<ResponseCourseDto> responseCourseDto = courseController.read(pathVariable);
                    responseStatus = responseCourseDto.isEmpty() ? HttpServletResponse.SC_NOT_FOUND : HttpServletResponse.SC_OK;
                    ResponseWriter.responseSender(responseCourseDto, responseStatus, response);
                    log.info(EXIT_LOG_MSG, "/students", responseCourseDto, (System.nanoTime() - logStart) / 1_000_000);
                }
                case "/offerings" -> {
                    Integer pathVariable = getPathVariable(request.getPathInfo());
                    log.info(ENTER_LOG_MSG, "/offerings", pathVariable, "not expected");
                    List<ResponseOfferingDto> responseOfferingDto = offeringController.read(pathVariable);
                    responseStatus = responseOfferingDto.isEmpty() ? HttpServletResponse.SC_NOT_FOUND : HttpServletResponse.SC_OK;
                    ResponseWriter.responseSender(responseOfferingDto, responseStatus, response);
                    log.info(EXIT_LOG_MSG, "/offerings", responseOfferingDto, (System.nanoTime() - logStart) / 1_000_000);
                }
                case "/reports" -> {
                    String pathVariable = getStringPathVariable(request.getPathInfo());
                    log.info(ENTER_LOG_MSG, "/reports", pathVariable, "not expected");
                    List<Map<String, String>> reportResult = reportController.doReport(pathVariable, queryParameters);
                    responseStatus = reportResult.isEmpty() ? HttpServletResponse.SC_NOT_FOUND : HttpServletResponse.SC_OK;
                    ResponseWriter.responseSender(reportResult, responseStatus, response);
                    log.info(EXIT_LOG_MSG, "/reports", reportResult, (System.nanoTime() - logStart) / 1_000_000);
                }
                default -> {
                    response.sendError(404);
                    log.error("404. Path {} doesn`t exists.",
                            pathFromOriginHttpRequest
                    );
                }
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
        long logStart = System.nanoTime();
        String pathFromOriginHttpRequest = getShortPath(request.getPathInfo());
        String body = new String(
                request.getInputStream().readAllBytes(),
                StandardCharsets.UTF_8
        );
        try {
            int responseStatus;
            switch (pathFromOriginHttpRequest) {
                case "/professors" -> {
                    log.info(ENTER_LOG_MSG, "/professors", "not expected", body);
                    RequestProfessorDto requestProfessorDto = ApplicationContext.objectMapper.readValue(body, RequestProfessorDto.class);
                    List<ResponseProfessorDto> responseProfessorDto = professorController.createProfessor(requestProfessorDto);
                    responseStatus = responseProfessorDto.isEmpty() ? HttpServletResponse.SC_NOT_FOUND : HttpServletResponse.SC_OK;
                    ResponseWriter.responseSender(responseProfessorDto, responseStatus, response);
                    log.info(EXIT_LOG_MSG, "/professors", responseProfessorDto, (System.nanoTime() - logStart) / 1_000_000);
                }
                case "/students" -> {
                    log.info(ENTER_LOG_MSG, "/students", "not expected", body);
                    RequestStudentDto requestStudentDto = ApplicationContext.objectMapper.readValue(body, RequestStudentDto.class);
                    List<ResponseStudentDto> responseStudentDto = studentController.create(requestStudentDto);
                    responseStatus = responseStudentDto.isEmpty() ? HttpServletResponse.SC_NOT_FOUND : HttpServletResponse.SC_OK;
                    ResponseWriter.responseSender(responseStudentDto, responseStatus, response);
                    log.info(EXIT_LOG_MSG, "/students", responseStudentDto, (System.nanoTime() - logStart) / 1_000_000);
                }
                case "/courses" -> {
                    log.info(ENTER_LOG_MSG, "/courses", "not expected", body);
                    RequestCourseDto requestCourseDto = ApplicationContext.objectMapper.readValue(body, RequestCourseDto.class);
                    List<ResponseCourseDto> responseCourseDto = courseController.create(requestCourseDto);
                    responseStatus = responseCourseDto.isEmpty() ? HttpServletResponse.SC_NOT_FOUND : HttpServletResponse.SC_OK;
                    ResponseWriter.responseSender(responseCourseDto, responseStatus, response);
                    log.info(EXIT_LOG_MSG, "/courses", responseCourseDto, (System.nanoTime() - logStart) / 1_000_000);
                }
                case "/offerings" -> {
                    log.info(ENTER_LOG_MSG, "/offerings", "not expected", body);
                    RequestOfferingDto requestOfferingDto = ApplicationContext.objectMapper.readValue(body, RequestOfferingDto.class);
                    List<ResponseOfferingDto> responseOfferingDto = offeringController.create(requestOfferingDto);
                    responseStatus = responseOfferingDto.isEmpty() ? HttpServletResponse.SC_NOT_FOUND : HttpServletResponse.SC_OK;
                    ResponseWriter.responseSender(responseOfferingDto, responseStatus, response);
                    log.info(EXIT_LOG_MSG, "/courses", responseOfferingDto, (System.nanoTime() - logStart) / 1_000_000);
                }
                case "/enrollments" -> {
                    log.info(ENTER_LOG_MSG, "/enrollmentss", "not expected", body);
                    RequestEnrollmentDto requestEnrollmentDto = ApplicationContext.objectMapper.readValue(body, RequestEnrollmentDto.class);
                    List<ResponseEnrollmentDto> responseEnrollmentDto = enrollmentController.create(requestEnrollmentDto);
                    responseStatus = responseEnrollmentDto.isEmpty() ? HttpServletResponse.SC_NOT_FOUND : HttpServletResponse.SC_OK;
                    ResponseWriter.responseSender(responseEnrollmentDto, responseStatus, response);
                    log.info(EXIT_LOG_MSG, "/courses", responseEnrollmentDto, (System.nanoTime() - logStart) / 1_000_000);
                }
                case "/enrollments:batch" -> {
                    log.info(ENTER_LOG_MSG, "/enrollments:batch", "not expected", body);
                    RequestEnrollmentDto requestEnrollmentDto = ApplicationContext.objectMapper.readValue(body, RequestEnrollmentDto.class);
                    List<ResponseEnrollmentDto> responseEnrollmentDto = enrollmentController.createAll(requestEnrollmentDto);
                    responseStatus = responseEnrollmentDto.isEmpty() ? HttpServletResponse.SC_NOT_FOUND : HttpServletResponse.SC_OK;
                    ResponseWriter.responseSender(responseEnrollmentDto, responseStatus, response);
                    log.info(EXIT_LOG_MSG, "/enrollmwnts:batch", responseEnrollmentDto, (System.nanoTime() - logStart) / 1_000_000);
                }
                default -> {
                    response.sendError(404);
                }
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
        long logStart = System.nanoTime();
        String pathFromOriginHttpRequest = getShortPath(request.getPathInfo());
        String[] compoundPathVariable = getCompoundPathVariable(request.getPathInfo());
        Integer pathVariable = Integer.parseInt(compoundPathVariable[0]);
        Integer pathVariablePartTwo = Integer.parseInt(compoundPathVariable[1]);
        String body = new String(
                request.getInputStream().readAllBytes(),
                StandardCharsets.UTF_8
        );
        // 2. Маршрутизуємо запит залежно від URL
        try {
            int responseStatus;
            switch (pathFromOriginHttpRequest) {
                case "/professors" -> {
                    log.info(ENTER_LOG_MSG, "/professors", pathVariable, body);
                    RequestProfessorDto requestProfessorDto = ApplicationContext.objectMapper.readValue(body, RequestProfessorDto.class);
                    List<ResponseProfessorDto> responseProfessorDto = professorController.updateProfessor(requestProfessorDto, pathVariable);
                    responseStatus = responseProfessorDto.isEmpty() ? HttpServletResponse.SC_NOT_FOUND : HttpServletResponse.SC_OK;
                    ResponseWriter.responseSender(responseProfessorDto, responseStatus, response);
                    log.info(EXIT_LOG_MSG, "/professors", responseProfessorDto, (System.nanoTime() - logStart) / 1_000_000);
                }
                case "/students" -> {
                    log.info(ENTER_LOG_MSG, "/students", pathVariable, body);
                    RequestStudentDto requestStudentDto = ApplicationContext.objectMapper.readValue(body, RequestStudentDto.class);
                    List<ResponseStudentDto> responseStudentDto = studentController.update(requestStudentDto, pathVariable);
                    responseStatus = responseStudentDto.isEmpty() ? HttpServletResponse.SC_NOT_FOUND : HttpServletResponse.SC_OK;
                    ResponseWriter.responseSender(responseStudentDto, responseStatus, response);
                    log.info(EXIT_LOG_MSG, "/students", responseStudentDto, (System.nanoTime() - logStart) / 1_000_000);
                }
                case "/courses" -> {
                    log.info(ENTER_LOG_MSG, "/courses", pathVariable, body);
                    RequestCourseDto requestCourseDto = ApplicationContext.objectMapper.readValue(body, RequestCourseDto.class);
                    List<ResponseCourseDto> responseCourseDto = courseController.update(requestCourseDto, pathVariable);
                    responseStatus = responseCourseDto.isEmpty() ? HttpServletResponse.SC_NOT_FOUND : HttpServletResponse.SC_OK;
                    ResponseWriter.responseSender(responseCourseDto, responseStatus, response);
                    log.info(EXIT_LOG_MSG, "/courses", responseCourseDto, (System.nanoTime() - logStart) / 1_000_000);
                }
                case "/offerings" -> {
                    log.info(ENTER_LOG_MSG, "/offerings", pathVariable, body);
                    RequestOfferingDto requestOfferingDto = ApplicationContext.objectMapper.readValue(body, RequestOfferingDto.class);
                    List<ResponseOfferingDto> responseOfferingDto = offeringController.update(requestOfferingDto, pathVariable);
                    responseStatus = responseOfferingDto.isEmpty() ? HttpServletResponse.SC_NOT_FOUND : HttpServletResponse.SC_OK;
                    ResponseWriter.responseSender(responseOfferingDto, responseStatus, response);
                    log.info(EXIT_LOG_MSG, "/courses", responseOfferingDto, (System.nanoTime() - logStart) / 1_000_000);
                }
                case "/enrollments" -> {
                    log.info(ENTER_LOG_MSG, "/enrollments", pathVariable, body);
                    RequestEnrollmentDto requestEnrollmentDto = ApplicationContext.objectMapper.readValue(body, RequestEnrollmentDto.class);
                    List<ResponseEnrollmentDto> responseEnrollmentDto = enrollmentController.update(requestEnrollmentDto, pathVariable, pathVariablePartTwo);
                    responseStatus = responseEnrollmentDto.isEmpty() ? HttpServletResponse.SC_NOT_FOUND : HttpServletResponse.SC_OK;
                    ResponseWriter.responseSender(responseEnrollmentDto, responseStatus, response);
                    log.info(EXIT_LOG_MSG, "/enrollments", responseEnrollmentDto, (System.nanoTime() - logStart) / 1_000_000);
                }
                default -> {
                    response.sendError(404);
                }
            }
        } catch (RuntimeException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        prepareDataForLogging();
        long logStart = System.nanoTime();
        String pathFromOriginHttpRequest = getShortPath(request.getPathInfo());
        String[] compoundPathVariable = getCompoundPathVariable(request.getPathInfo());
        Integer pathVariable = Integer.parseInt(compoundPathVariable[0]);
        Integer pathVariablePartTwo = Integer.parseInt(compoundPathVariable[1]);
        //Integer pathVariable = getPathVariable(request.getPathInfo());
        String body = new String(
                request.getInputStream().readAllBytes(),
                StandardCharsets.UTF_8
        );
        // 2. Маршрутизуємо запит залежно від URL
        try {
            int responseStatus;
            switch (pathFromOriginHttpRequest) {
                case "/professors" -> {
                    log.info(ENTER_LOG_MSG, "/professors", pathVariable, body);
                    List<ResponseProfessorDto> responseProfessorDto = professorController.deleteProfessor(pathVariable);
                    responseStatus = responseProfessorDto.isEmpty() ? HttpServletResponse.SC_NOT_FOUND : HttpServletResponse.SC_OK;
                    ResponseWriter.responseSender(responseProfessorDto, responseStatus, response);
                    log.info(EXIT_LOG_MSG, "/professors", responseProfessorDto, (System.nanoTime() - logStart) / 1_000_000);
                }
                case "/students" -> {
                    log.info(ENTER_LOG_MSG, "/students", pathVariable, body);

                    List<ResponseStudentDto> responseStudentDto = studentController.delete(pathVariable);
                    responseStatus = responseStudentDto.isEmpty() ? HttpServletResponse.SC_NOT_FOUND : HttpServletResponse.SC_OK;
                    ResponseWriter.responseSender(responseStudentDto, responseStatus, response);
                    log.info(EXIT_LOG_MSG, "/students", responseStudentDto, (System.nanoTime() - logStart) / 1_000_000);
                }
                case "/courses" -> {
                    log.info(ENTER_LOG_MSG, "/courses", pathVariable, body);
                    List<ResponseCourseDto> responseCourseDto = courseController.delete(pathVariable);
                    responseStatus = responseCourseDto.isEmpty() ? HttpServletResponse.SC_NOT_FOUND : HttpServletResponse.SC_OK;
                    ResponseWriter.responseSender(responseCourseDto, responseStatus, response);
                    log.info(EXIT_LOG_MSG, "/courses", responseCourseDto, (System.nanoTime() - logStart) / 1_000_000);
                }
                case "/offering" -> {
                    log.info(ENTER_LOG_MSG, "/offerings", pathVariable, body);
                    List<ResponseOfferingDto> responseOfferingDto = offeringController.delete(pathVariable);
                    responseStatus = responseOfferingDto.isEmpty() ? HttpServletResponse.SC_NOT_FOUND : HttpServletResponse.SC_OK;
                    ResponseWriter.responseSender(responseOfferingDto, responseStatus, response);
                    log.info(EXIT_LOG_MSG, "/offerings", responseOfferingDto, (System.nanoTime() - logStart) / 1_000_000);
                }
                case "/enrollments" -> {
                    log.info(ENTER_LOG_MSG, "/enrollments", pathVariable, body);
                    List<ResponseEnrollmentDto> responseEnrollmentDto = enrollmentController.delete(pathVariable, pathVariablePartTwo);
                    responseStatus = responseEnrollmentDto.isEmpty() ? HttpServletResponse.SC_NOT_FOUND : HttpServletResponse.SC_OK;
                    ResponseWriter.responseSender(responseEnrollmentDto, responseStatus, response);
                    log.info(EXIT_LOG_MSG, "/enrollments", responseEnrollmentDto, (System.nanoTime() - logStart) / 1_000_000);
                }
                default -> {
                    response.sendError(404);
                }
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

    public static Integer getPathVariable(String path) {
        int pathLength = path.split("/").length;
        return pathLength == 3 ? Integer.parseInt(path.split("/")[2]) : null;
    }

    public static String getStringPathVariable(String path) {
        int pathLength = path.split("/").length;
        return pathLength == 3 ? path.split("/")[2] : null;
    }

    public static String[] getCompoundPathVariable(String path) {
        int pathLength = path.split("/").length;
        String rawPathVariable = pathLength == 3 ? (path.split("/")[2]) : null;
        String[] compoundVariable = rawPathVariable != null ? rawPathVariable.split("-") : new String[]{null, null};
        return compoundVariable;
    }

}
