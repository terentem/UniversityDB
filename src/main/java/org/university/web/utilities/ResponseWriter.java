package org.university.web.utilities;

import jakarta.servlet.http.HttpServletResponse;
import org.university.context.ApplicationContext;

import java.io.IOException;

public class ResponseWriter {
    public static <T> void responseSender(
            T responseDto,
            int responseStatus,
            HttpServletResponse response) throws IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            if (responseStatus!=200) {
                response.setStatus(responseStatus);
                response.getWriter().write(
                        "{\"error\":\"not found.\"}"
                );
                return;
            }
            response.setStatus(responseStatus);
            response.getWriter().write(
                    ApplicationContext.objectMapper
                            .writeValueAsString(responseDto)
            );

        } catch (IOException ex) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write(
                    "{\"error\":\"" + ex.getMessage() + "\"}"
            );
        }
    }
}
