package org.university.web.dto.report;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Map;

public class ReportDtoMapper {
    private static final Logger log = LoggerFactory.getLogger(ReportDtoMapper.class);

    public static RequestReportDto mapToRequestDto(String pathVariable, Map<String, String[]> queryParams) {
        log.info("queryParams={}", queryParams);
        try {
            // 1. Сканируем конструкторы рекорда
            for (Constructor<?> constructor : RequestReportDto.class.getConstructors()) {

                if (constructor.isAnnotationPresent(ReportConstructor.class)) {
                    ReportConstructor meta = constructor.getAnnotation(ReportConstructor.class);

                    // 2. Если нашли конструктор для нашего отчета
                    if (meta.reportId().equalsIgnoreCase(pathVariable)) {

                        Parameter[] parameters = constructor.getParameters();
                        Object[] initArgs = new Object[parameters.length];
                        log.info("initArgs.length={}", initArgs.length);

                        // 3. Мапим параметры из Map<String, String[]> на аргументы конструктора
                        for (int i = 0; i < parameters.length; i++) {
                            String paramName = parameters[i].getName(); // Например: "grade"
                            String[] httpValues = queryParams.get(paramName);
                            log.info("httpValues={}", httpValues);

                            if (httpValues == null || httpValues.length == 0 || httpValues[0].isBlank()) {
                                initArgs[i] = null; // Или бросаем ошибку, если параметр обязательный
                                log.info("initArgs[{}]={}", i, initArgs[i]);
                                continue;
                            }

                            String rawValue = httpValues[0]; // Берем первое значение из массива
                            Class<?> paramType = parameters[i].getType();

                            // 4. Приведение типов на основе типа аргумента конструктора
                            if (paramType == Integer.class) {
                                initArgs[i] = Integer.parseInt(rawValue);
                            } else {
                                initArgs[i] = rawValue;
                            }
                        }

                        // 5. Создаем DTO
                        RequestReportDto requestReportDto = (RequestReportDto) constructor.newInstance(initArgs);
                        log.info("queryParams={}, initArgs={} ", queryParams, initArgs);
                        return requestReportDto;
                    }
                }
            }
            throw new IllegalArgumentException("Не найден конструктор для отчета: " + pathVariable);

        } catch (Exception e) {
            throw new RuntimeException("Ошибка сборки DTO из параметров запроса", e);
        }
    }

    public static ResponseReportDto mapToResponseDto(String pathVariable, ResultSet rs) {
        try {
            // 1. Ищем подходящий конструктор (по аннотации или канонический)
            Constructor<ResponseReportDto> targetConstructor = findConstructor(pathVariable);
            Parameter[] parameters = targetConstructor.getParameters();

            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            Object[] initArgs = new Object[parameters.length];

            // 2. Сопоставляем параметры конструктора с колонками БД
            for (int i = 0; i < parameters.length; i++) {
                String paramName = parameters[i].getName(); // Требуется <parameters>true</parameters> в pom.xml
                Object resultSetValue = getResultSetValueByName(rs, metaData, columnCount, paramName);
                initArgs[i] = resultSetValue;
            }
            ResponseReportDto responseReportDto = targetConstructor.newInstance(initArgs);
            return responseReportDto;

        } catch (Exception e) {
            throw new RuntimeException("Ошибка динамического маппинга строки ResultSet в ResponseReportDto", e);
        }
    }

    private static Constructor<ResponseReportDto> findConstructor(String pathVariable) {
        Constructor<?>[] constructors = ResponseReportDto.class.getConstructors();
        for (Constructor<?> c : constructors) {
            ReportConstructor[] annotations = c.getAnnotationsByType(ReportConstructor.class);
            for (ReportConstructor meta : annotations) {
                if (meta.reportId().equalsIgnoreCase(pathVariable)) {
                    return (Constructor<ResponseReportDto>) c;
                }
            }
        }
        // Якщо return е спрацював, то беремо канонічний конструктор. Порівнюємо кількість параметрів в ResponseReportDto і кількість параметрів в конструторі
        int recordFieldsCount = ResponseReportDto.class.getRecordComponents().length;
        for (Constructor<?> c : constructors) {
            if (c.getParameterCount() == recordFieldsCount) {
                return (Constructor<ResponseReportDto>) c;
            }
        }
        throw new IllegalStateException("Не удалось найти подходящий конструктор для " + ResponseReportDto.class.getSimpleName());
    }

    private static Object getResultSetValueByName(ResultSet rs, ResultSetMetaData metaData, int columnCount, String paramName) throws SQLException, SQLException {
        String normalizedParam = paramName.replace("_", "").toLowerCase();
        for (int i = 1; i <= columnCount; i++) {
            String columnLabel = metaData.getColumnLabel(i);
            String normalizedColumn = columnLabel.replace("_", "").toLowerCase();
            if (normalizedColumn.equals(normalizedParam)) {
                return rs.getObject(i);
            }
        }
        return null;
    }
}


