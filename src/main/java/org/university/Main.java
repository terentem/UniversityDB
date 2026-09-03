package org.university;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;
import org.university.context.*;
import org.university.repository.ProfessorRepository;
import org.university.service.ProfessorService;
import org.university.web.DispatcherServlet;
import org.university.web.controller.ProfessorController;

import java.io.File;

public class Main {
    public static void main(String[] args) throws LifecycleException {
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8080);
        Connector connector = tomcat.getConnector(); //
        Context path = tomcat.addContext("", new File(".").getAbsolutePath());// створює у пам’яті Java об'єкт, який «вказує» на поточну папку (це параметр ocBase → де лежать ресурси).

        StudentContext studentContext = new StudentContext();
        CourseContext courseContext = new CourseContext();
        OfferingContext offeringContext = new OfferingContext();
        EnrollmentContext enrollmentContext = new EnrollmentContext();
        ReportContext reportContext = new ReportContext();

        ProfessorRepository repository = new ProfessorRepository();
        ProfessorService professorService = new ProfessorService(repository);
        ProfessorController professorController = new ProfessorController(professorService);
        DispatcherServlet dispatcherController = new DispatcherServlet(
                professorController,
                studentContext.getStudentController(),
                courseContext.getStudentController(),
                offeringContext.getOfferingController(),
                enrollmentContext.getController(),
                reportContext.getReportController());

        Tomcat.addServlet(path, "DispatcherController", dispatcherController);
        path.addServletMappingDecoded("/*", "DispatcherController");

        tomcat.start(); // Запуск сервера
        System.out.println("Піднімаємо сервер");
        tomcat.getServer().await(); // Очікування запитів
    }
}