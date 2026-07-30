package org.university.web.utilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.university.web.controller.ProfessorController;
import org.university.web.dto.InputProfessorDto;
import org.university.web.model.RequestParameter;

import java.util.Map;
import java.util.stream.Collectors;

public class RequestDataProvider {
    private static final Logger log = LoggerFactory.getLogger(ProfessorController.class);


    public static Map<String, String> getParameters(Map<String, String[]> parameters) {
        Map<String, String> params = parameters
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> e.getValue()[0]
                ));
        return params;
    }

    public static InputProfessorDto mapToInputPostProfessorDto(Map<String, String> body) {
        return new InputProfessorDto(
                body.get(RequestParameter.NAME.value()),
                body.get(RequestParameter.EMAIL.value()),
                Integer.parseInt(body.get(RequestParameter.DEPARTMENT.value())));
    }

    public static InputProfessorDto mapToInputPutProfessorDto(Map<String, String> body) {
        return new InputProfessorDto(
                Integer.parseInt(body.get(RequestParameter.ID.value())),
                body.get(RequestParameter.NAME.value()),
                body.get(RequestParameter.EMAIL.value()),
                Integer.parseInt(body.get(RequestParameter.DEPARTMENT.value())));
    }

    public static String getPathVariable(String path) {
        int pathLength = path.split("/").length;
        return pathLength == 3 ? path.split("/")[2] : "all";
    }

    public static InputProfessorDto mapToDeleteProfessorDto(Map<String, String> body) {
        return new InputProfessorDto(
                Integer.parseInt(body.get(RequestParameter.ID.value())));
    }
}



