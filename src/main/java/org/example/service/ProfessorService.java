package org.example.service;

import org.example.domain.model.Professor;
import org.example.repository.ProfessorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;


public class ProfessorService {
    private final ProfessorRepository repository;
    private static final Logger log = LoggerFactory.getLogger(ProfessorService.class);

    public ProfessorService(ProfessorRepository repository) {
        this.repository = repository;
    }

    public Optional<List<Professor>> getProfessors(String params) throws SQLException {
        log.info("Input parameters in method \"getProfessors\" = {} and call ProfessorRepository.getProfessorsList", params);
        return repository.getProfessorsList(params);
    }

    public Optional<List<Professor>> createProfessor(String params) throws SQLException {
        return repository.postProfessor(params);

    }
}



