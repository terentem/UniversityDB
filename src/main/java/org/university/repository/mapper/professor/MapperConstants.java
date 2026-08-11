package org.university.repository.mapper.professor;

public class MapperConstants {
    public static final MapperExecutor READ_CONSTANT =
            rs -> ResultSetMapper.mapToReadProfessor(rs);

    public static final MapperExecutor CREATE_UPDATE_DELETE_CONSTANT =
            rs -> ResultSetMapper.mapToCreateUpdateDeleteProfessor(rs);
}
