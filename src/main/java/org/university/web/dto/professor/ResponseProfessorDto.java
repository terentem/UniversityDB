package org.university.web.dto.professor;

public record ResponseProfessorDto(//parameters for response for Get request
                                   int id,
                                   String name,
                                   String email,
                                   String department,
                                   int departmentId) {

public ResponseProfessorDto(//parameters for response for POST and PUT requests
                            int id,
                            String name,
                            String email,
                            int departmentId){
    this(id,name, email,null,departmentId);}
}
