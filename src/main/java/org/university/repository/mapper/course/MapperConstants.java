package org.university.repository.mapper.course;

public class MapperConstants {
    public static final MapperExecutor CREATE_UPDATE_DELETE_CONSTANT =
            rs -> ResultSetMapper.mapToCreateUpdateDelete(rs);
    public static final MapperExecutor READ_CONSTANT =
            rs -> ResultSetMapper.mapToRead(rs);
}
