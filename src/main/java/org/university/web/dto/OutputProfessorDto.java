package org.university.web.dto;

public record OutputProfessorDto(//parameters for response for Get request
        int id,
        String name,
        String email,
        String department,
        int departmentId) {

public OutputProfessorDto(//parameters for response for POST and PUT requests
        int id,
        String name,
        String email,
        int departmentId){
    this(id,name, email,null,departmentId);}
}
