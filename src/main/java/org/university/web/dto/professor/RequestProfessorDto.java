package org.university.web.dto.professor;

public record RequestProfessorDto(//for parameters in PUT http request
                                  Integer id,
                                  String name,
                                  String email,
                                  Integer departmentId
) {
    public RequestProfessorDto(String name, String email, int id){//for parameters in POST http request
        this(null, name, email,id);
    }

    public RequestProfessorDto(Integer id){//for Veriable in GET http request
        this(id ,null, null,null);
    }

}
