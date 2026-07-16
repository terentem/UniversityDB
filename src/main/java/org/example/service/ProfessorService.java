package org.example.service;

import org.example.domain.model.Professor;
import org.example.repository.ProfessorRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;


public class ProfessorService {

    public Optional<List<Professor>> getProfessors(String params) throws SQLException {
                ProfessorRepository repository = new ProfessorRepository();
        return repository.getProfessorsList(params);
    }

    public Optional<List<Professor>> postProfessor(String params) throws SQLException {
        ProfessorRepository repository = new ProfessorRepository();
        return repository.postProfessor(params);
    }
}



