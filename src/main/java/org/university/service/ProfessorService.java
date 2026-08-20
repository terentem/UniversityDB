package org.university.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.university.domain.model.Professor;
import org.university.repository.ProfessorRepository;
import org.university.web.dto.professor.RequestProfessorDto;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class ProfessorService {
    private final ProfessorRepository repository;
    private static final Logger log = LoggerFactory.getLogger(ProfessorService.class);

    public ProfessorService(ProfessorRepository repository) {
        this.repository = repository;
    }

    public List<Professor> getProfessors(Integer id) throws SQLException {
        //ВИзначаємо метод для пошуку
        if (id == null||id ==0) {
            log.info("id = params={}", id);
            return repository.findAll().orElse(Collections.emptyList());
        } else {
            log.info("id = params={}", id);
            return repository.findById(id).orElse(Collections.emptyList());
        }
    }

    public List<Professor> createProfessor(RequestProfessorDto professorDto) throws SQLException {
        return repository.createProfessor(professorDto).orElse(Collections.emptyList());
    }

    public List<Professor> updateProfessor(RequestProfessorDto professorDto, Integer pathVariable) throws SQLException {
        return repository.updateProfessor(professorDto, pathVariable).orElse(Collections.emptyList());
    }

    public List<Professor> deleteProfessor(Integer id) throws SQLException {
        return repository.deleteById(id).orElse(Collections.emptyList());
    }
}



