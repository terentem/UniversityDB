package org.university.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.university.domain.model.Offering;
import org.university.service.OfferingService;
import org.university.web.dto.offering.RequestOfferingDto;
import org.university.web.dto.offering.ResponseOfferingDto;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class OfferingController {


    private static final Logger log = LoggerFactory.getLogger(OfferingController.class);

    private final OfferingService courseService;

    public OfferingController(OfferingService courseService) {
        this.courseService = courseService;
    }

    public List<ResponseOfferingDto> read(Integer id) throws IOException, SQLException {
        List<Offering> result = courseService.get(id);
        List<ResponseOfferingDto> getResponseDto = result.stream().map(ResponseOfferingDto::toRead).toList();
        log.info("GET reply  {}", getResponseDto);
        return getResponseDto;
    }

    public List<ResponseOfferingDto> create(RequestOfferingDto requestDto) throws IOException, SQLException {
        List<Offering> result = courseService.create(requestDto);
        List<ResponseOfferingDto> getResponseDto = result.stream().map(ResponseOfferingDto::toPostPutDeleteDto).toList();
        log.info("POST http response= {}", getResponseDto);

        return getResponseDto;

    }

    public List<ResponseOfferingDto> update(RequestOfferingDto requestDto, Integer pathVariable) throws IOException, SQLException {
        List<Offering> result = courseService.update(requestDto, pathVariable);
        List<ResponseOfferingDto> getResponseDto = result.stream().map(ResponseOfferingDto::toPostPutDeleteDto).toList();
        log.info("PUT http response= {}", getResponseDto);
        return getResponseDto;
    }

    public List<ResponseOfferingDto> delete(Integer id) throws IOException, SQLException {
        List<Offering> result = courseService.delete(id);
        List<ResponseOfferingDto> getResponseDto = result.stream().map(ResponseOfferingDto::toPostPutDeleteDto).toList();
        log.info("DELETE http response= {}", getResponseDto);
        return getResponseDto;
    }


}
