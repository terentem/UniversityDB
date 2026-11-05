package org.university.web.dto.validation;

import java.util.List;

public class BadRequestDto {
    public final String type="https://zalando.github.io/problem/constraint-violation";
    public final int status=400;
    private List<ViolationDto> violations;
    public final String title="Constraint Violation";

    public void setViolations(List<ViolationDto> violations) {
        this.violations = violations;
    }

    public List<ViolationDto> getViolations() {
        return violations;
    }
}
