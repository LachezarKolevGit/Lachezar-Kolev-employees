package bg.sirma.java.intern.employees.employee;

import java.time.LocalDate;

public record EmployeeDTO (String id, String projectId, LocalDate startOfWork, LocalDate endOfWork){
}
