package org.university.web.utilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PathVariablesValidator {
    private static final Logger log = LoggerFactory.getLogger(PathVariablesValidator.class);

    public static String[] forReadProfessors(String params) {
        String[] paramsArr;
        if (params != null) {
            paramsArr = params.split(",");
        } else {
            paramsArr = new String[0];
        }

        if (paramsArr[0].equals("")) {
            paramsArr = new String[0];
        }
        return paramsArr;
    }

    public static String[] forCreateProfessor(String params) {
        String[] paramsArr;
        if (params != null) {
            paramsArr = params.split(",");
            log.info("paramsArr= {}, {}, {}", paramsArr[0], paramsArr[1], paramsArr[2]);
            if (paramsArr.length != 3) {
                log.error("Validation error. Expected 3 input parameters.");
            }
        } else {
            paramsArr = new String[0];
            log.error("Empty input parameters.");
        }
        return paramsArr;
    }

    public static String[] forUpdateProfessor(String params) {
        String[] paramsArr;
        if (params != null) {
            paramsArr = params.split(",");
            log.info("paramsArr= {}, {}, {}, {}", paramsArr[0], paramsArr[1], paramsArr[2], paramsArr[3]);
            if (paramsArr.length != 4) {
                log.error("Validation error. Expected 4 input parameters.");
            }
        } else {
            paramsArr = new String[0];
            log.error("Empty input parameters.");
        }
        return paramsArr;
    }
}
