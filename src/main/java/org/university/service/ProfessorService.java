package org.university.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.university.domain.model.Professor;
import org.university.repository.ProfessorRepository;
import org.university.web.dto.InputProfessorDto;

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
        log.info("Input parameters in method \"getProfessors\" = {} ", params);

        //ВИзначаємо метод для пошуку
        if (params.equals("all")) {
            return repository.findAll();
        } else {
            log.info("id = params={}", params);
            Integer id = Integer.parseInt(params);
            return repository.findById(id);
        }
    }

    public Optional<List<Professor>> createProfessor(InputProfessorDto professorDto) throws SQLException {
        return repository.createProfessor(professorDto.name(), professorDto.email(), professorDto.departmentId());
    }

    public Optional<List<Professor>> updateProfessor(InputProfessorDto professorDto) throws SQLException {
         return repository.updateProfessor(professorDto.id(), professorDto.name(), professorDto.email(), professorDto.departmentId());
          }

    public Optional<List<Professor>> deleteProfessor(InputProfessorDto professorDto) throws SQLException {
        return repository.deleteById(professorDto.id());
    }
}



