package org.university.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.university.domain.model.Professor;
import org.university.repository.ProfessorRepository;
import org.university.web.dto.professor.RequestProfessorDto;
import org.university.web.utilities.professor.ProfessorValidator;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ProfessorService {
    private final ProfessorRepository repository;
    private static final Logger log = LoggerFactory.getLogger(ProfessorService.class);

    public ProfessorService(ProfessorRepository repository) {
        this.repository = repository;
    }

    public Optional<List<Professor>> getProfessors(Integer id) throws SQLException {
        //ВИзначаємо метод для пошуку
        if (id == null) {
            log.info("id = params={}", id);
            return repository.findAll();
        } else {
            log.info("id = params={}", id);
            return repository.findById(id);
        }
    }

    public Optional<List<Professor>> createProfessor(RequestProfessorDto professorDto) throws SQLException {
        ProfessorValidator.checkPostHttpBody(professorDto);
        return repository.createProfessor(professorDto);
    }

    public Optional<List<Professor>> updateProfessor(RequestProfessorDto professorDto, Integer pathVariable) throws SQLException {
        ProfessorValidator.checkPutHttpBody(professorDto);
        return repository.updateProfessor(professorDto, pathVariable);
    }

    public Optional<List<Professor>> deleteProfessor(Integer id) throws SQLException {
        return repository.deleteById(id);
    }
}



