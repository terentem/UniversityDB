package org.university.web.dto.student;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record RequestStudentDto(
        Integer id,
        @NotEmpty(message = "must not be empty")
        @NotBlank(message = "must not be blank")
        @Pattern(
                regexp = "^[A-Za-z]+(?:[ -][A-Za-z]+)*$",
                message = "Only Latin letters are allowed"
        )
        @Size(max = 50, message = "more then 50 symbols name")
        String name,

        @NotEmpty(message = "must not be empty")
        @NotBlank(message = "must not be blank")
        @Email(message = "not email patern")
        String email,

        @PastOrPresent(message = "date must be in past or present")
        LocalDate enrollmentDate,

        @Past(message = "date must be in past")
        LocalDate birthday,

        @NotEmpty(message = "must not be empty")
        @NotBlank(message = "must not be blank")
        @Pattern(
                regexp = "^(credit|budget|selfPayment)$",
                message = "Funding source must be one of: credit, budget, selfPayment"
        )
        String fundingSource,
        Long admissionScore,
        Boolean specialNeeds,

        @NotEmpty(message = "must not be empty")
        @NotBlank(message = "must not be blank")
        @Size(max = 250, message = "more then 250 symbols address")
        String address,

        @NotEmpty(message = "must not be empty")
        @NotBlank(message = "must not be blank")
        @Pattern(
                regexp = "^\\+380[0-9]{9}$",
                message = "Phone number must contain 13 digits, start with +"
        )
        String phoneNumber,
        String gender,
        int[] academicInterests
) {
}

