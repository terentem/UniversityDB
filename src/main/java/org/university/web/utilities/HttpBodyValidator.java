package org.university.web.utilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.university.web.dto.ShortHttpPostRequestDto;
import org.university.web.model.RequestParameter;

public class HttpBodyValidator {
    private static final Logger log = LoggerFactory.getLogger(HttpBodyValidator.class);

    public static String checkPostHttpBody(ShortHttpPostRequestDto request) {
        String profNameForService = request.getParameter(RequestParameter.NAME.value())
                .orElseThrow(() -> new IllegalArgumentException("Missing required parameter: NAME"));

        String profEmailForService = request.getParameter(RequestParameter.EMAIL.value())
                .orElseThrow(() -> new IllegalArgumentException("Missing required parameter: EMAIL"));

        String profDepartmentForService = request.getParameter(RequestParameter.DEPARTMENT.value())
                .orElseThrow(() -> new IllegalArgumentException("Missing required parameter: DEPARTMENT"));


        String params = profNameForService + "," + profEmailForService + "," + profDepartmentForService;
        log.info("Отриманий параметр = {} ", params);
        return params;
    }

    public static String checkPUTtHttpBody(ShortHttpPostRequestDto request) {
        String profIdForService = request.getParameter(RequestParameter.ID.value())
                .orElseThrow(() -> new IllegalArgumentException("Missing required parameter: ID"));

        String profNameForService = request.getParameter(RequestParameter.NAME.value())
                .orElseThrow(() -> new IllegalArgumentException("Missing required parameter: NAME"));

        String profEmailForService = request.getParameter(RequestParameter.EMAIL.value())
                .orElseThrow(() -> new IllegalArgumentException("Missing required parameter: EMAIL"));

        String profDepartmentForService = request.getParameter(RequestParameter.DEPARTMENT.value())
                .orElseThrow(() -> new IllegalArgumentException("Missing required parameter: DEPARTMENT"));

        String params = profIdForService+","+profNameForService + "," + profEmailForService + "," + profDepartmentForService;
        log.info("Отриманий параметр = {} .Method = {}. FullPath={}  ", params);
        return params;
    }

}
