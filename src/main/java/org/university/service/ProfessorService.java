package org.university.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.university.domain.model.Professor;
import org.university.repository.ProfessorRepository;
import org.university.web.model.RequestParameter;
import org.university.web.utilities.PathVariablesValidator;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ProfessorService {
    private final ProfessorRepository repository;
    private static final Logger log = LoggerFactory.getLogger(ProfessorService.class);

    public ProfessorService(ProfessorRepository repository) {
        this.repository = repository;
    }

    public Optional<List<Professor>> getProfessors(String params, String pathVariableName) throws SQLException {
        log.info("Input parameters in method \"getProfessors\" = {} ", params);
        //Парсимо params
        String[] paramsArr = PathVariablesValidator.forReadProfessors(params);

        //ВИзначаємо метод для пошуку
        if (paramsArr[0].equals("all")) {
            return repository.getAllProfessors(params);
        } else {
            if (pathVariableName.equals(RequestParameter.ID.value())) {
                log.info("id = paramsArr[0]={}", paramsArr[0]);
                Integer id = Integer.parseInt(paramsArr[0]);
                return repository.getProfessorsById(id);
            } else if (pathVariableName.equals(RequestParameter.EMAIL.value())) {
                log.info("email= paramsArr[0]={}", paramsArr[0]);
                return repository.getProfessorsByEmail(paramsArr[0]);
            } else if (pathVariableName.equals(RequestParameter.NAME.value())) {
                log.info("name= paramsArr[0]={}", paramsArr[0]);
                return repository.getProfessorsByName(paramsArr[0]);
            } else {
                log.error("Unknown parameter {}", pathVariableName);
                throw new IllegalArgumentException(
                        "Unknown parameter " + pathVariableName
                );
            }
        }
    }

    public Optional<List<Professor>> createProfessor(String params) throws SQLException {
        String[] pathVariable = PathVariablesValidator.forCreateProfessor(params);
        if (pathVariable.length != 3) {
            return Optional.empty();
        } else {
            return repository.postProfessor(pathVariable[0], pathVariable[1], Integer.parseInt(pathVariable[2]));
        }
    }

    public Optional<List<Professor>> updateProfessor(String params) throws SQLException {
        String[] pathVariable = PathVariablesValidator.forUpdateProfessor(params);
        if (pathVariable.length != 4) {
            return Optional.empty();
        } else {
            return repository.putProfessor(Integer.parseInt(pathVariable[0]), pathVariable[1], pathVariable[2], Integer.parseInt(pathVariable[3]));
        }
    }
}



