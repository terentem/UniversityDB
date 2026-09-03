package org.university.context;


import org.university.repository.OfferingRepository;
import org.university.service.OfferingService;
import org.university.web.controller.OfferingController;

public class OfferingContext {

    OfferingRepository repository = new OfferingRepository();
    OfferingService service = new OfferingService(repository);
    OfferingController controller = new OfferingController(service);

    public OfferingRepository getRepository() {
        return repository;
    }

    public OfferingService getOfferingService() {
        return service;
    }

    public OfferingController getOfferingController() {
        return controller;
    }

}