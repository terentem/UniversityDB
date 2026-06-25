package org.example.service;

import org.example.domain.dto.UniversityDto;
import org.example.utilits.ResultCreator;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;


public class UniversityService {
    Connection connection;
    public UniversityService(Connection conn) {
        this.connection = conn;
    }
    public List<? extends UniversityDto> getServiceResult(String parameter) throws SQLException {
        ResultCreator result = new ResultCreator();

        if (parameter.equals("professors")) {
            System.out.println("Зайшли в if=professors в UniversityService"+" parameter ="+parameter);
            return result.getProfessorsList(connection, parameter);
        }
        else return Collections.emptyList();
    }
}



