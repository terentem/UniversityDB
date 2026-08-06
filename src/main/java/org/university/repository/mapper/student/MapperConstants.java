package org.university.repository.mapper.student;

public class MapperConstants {
    public static final MapperExecutor CONSTANT =
            rs -> ResultSetMapper.mapToReadCreateUpdateDelete(rs);

}
