package org.university.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.university.domain.model.Offering;
import org.university.repository.OfferingRepository;
import org.university.web.dto.offering.RequestOfferingDto;


import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class OfferingService {

    private static final Logger log = LoggerFactory.getLogger(OfferingService.class);

    private final OfferingRepository repository;

    public OfferingService(OfferingRepository repository) {
        this.repository = repository;
    }

    public List<Offering> get(Integer id) throws SQLException {
        //ВИзначаємо метод для пошуку
        if (id == null) {
            log.info("id = params={}", id);
            return repository.findAll().orElse(Collections.emptyList());
        } else {
            log.info("id = params={}", id);
            return repository.findById(id).orElse(Collections.emptyList());
        }
    }

    public List<Offering> create(RequestOfferingDto courseDto) throws SQLException {
        return repository.create(courseDto).orElse(Collections.emptyList());
    }

    public List<Offering> update(RequestOfferingDto courseDto, Integer pathVariable) throws SQLException {
        return repository.update(courseDto, pathVariable).orElse(Collections.emptyList());
    }

    public List<Offering> delete(Integer id) throws SQLException {
        return repository.deleteById(id).orElse(Collections.emptyList());
    }
}



