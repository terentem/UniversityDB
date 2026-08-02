package org.university.web.dto;

public record InputProfessorDto(//for parameters in PUT http request
        Integer id,
        String name,
        String email,
        Integer departmentId
) {
    public InputProfessorDto(String name, String email, int id){//for parameters in POST http request
        this(null, name, email,id);
    }

    public InputProfessorDto(int id){//for Veriable in GET http request
        this(id,null, null,id);
    }

}
