package org.university.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.university.domain.model.Professor;
import org.university.service.ProfessorService;
import org.university.web.dto.professor.RequestProfessorDto;
import org.university.web.dto.professor.ResponseProfessorDto;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ProfessorController {

    private static final Logger log = LoggerFactory.getLogger(ProfessorController.class);

    private final ProfessorService professorService;

    public ProfessorController(ProfessorService professorService) {
        this.professorService = professorService;
    }

    public List<ResponseProfessorDto> readProfessors(Integer id) throws IOException, SQLException {
        List<Professor> result = professorService.getProfessors(id);
        List<ResponseProfessorDto> getProfessorDto = result.stream().map(ResponseProfessorDto::toGetDto).toList();
        log.info("GET reply  {}", getProfessorDto);
        return getProfessorDto;
    }

    public List<ResponseProfessorDto> createProfessor(RequestProfessorDto requestProfessorDto) throws IOException, SQLException {
        List<Professor> result = professorService.createProfessor(requestProfessorDto);
        List<ResponseProfessorDto> responseDto = result.stream().map(ResponseProfessorDto::toPostPutDeleteDto).toList();
        log.info("POST http response= {}", responseDto);
        return responseDto;

    }

    public List<ResponseProfessorDto> updateProfessor(RequestProfessorDto requestProfessorDto, Integer pathVariable) throws IOException, SQLException {
        List<Professor> result = professorService.updateProfessor(requestProfessorDto, pathVariable);
        List<ResponseProfessorDto> responseDto = result.stream().map(ResponseProfessorDto::toPostPutDeleteDto).toList();
        log.info("PUT http response= {}", responseDto);
        return responseDto;
    }

    public List<ResponseProfessorDto> deleteProfessor(Integer id) throws IOException, SQLException {
        List<Professor> result = professorService.deleteProfessor(id);
        List<ResponseProfessorDto> postProfessorDto = result.stream().map(ResponseProfessorDto::toPostPutDeleteDto).toList();
        log.info("DELETE http response= {}", postProfessorDto);
        return postProfessorDto;
    }
}
