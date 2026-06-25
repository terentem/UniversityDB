package org.example;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;
import org.example.service.UniversityService;

import java.io.File;

public class Main {
    public static void main(String[] args) throws LifecycleException {
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8080);
        Connector connector = tomcat.getConnector(); //
        Context path = tomcat.addContext("", new File(".").getAbsolutePath());// створює у пам’яті Java об'єкт, який «вказує» на поточну папку (це параметр ocBase → де лежать ресурси).

        Tomcat.addServlet(path, "UniversityService", new UniversityService());
        path.addServletMappingDecoded("/get", "UniversityService");

        tomcat.start(); // Запуск сервера
        System.out.println("Піднімаємо сервер");
        tomcat.getServer().await(); // Очікування запитів

    }
}