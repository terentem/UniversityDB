package org.university.repository.mapper.offering;

public class MapperConstants {
    public static final MapperExecutor CONSTANT =
            rs -> ResultSetMapper.mapToReadCreateUpdateDelete(rs);

}
