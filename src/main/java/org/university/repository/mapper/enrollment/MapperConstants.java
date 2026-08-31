package org.university.repository.mapper.enrollment;

public class MapperConstants {
    public static final MapperExecutor CONSTANT =
            rs -> ResultSetMapper.mapToReadCreateUpdateDelete(rs);

}
