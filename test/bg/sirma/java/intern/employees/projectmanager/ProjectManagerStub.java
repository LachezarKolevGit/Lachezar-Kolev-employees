package bg.sirma.java.intern.employees.projectmanager;

import bg.sirma.java.intern.employees.employee.Employee;

import java.time.LocalDate;
import java.util.List;

public class ProjectManagerStub extends ProjectManager {

    @Override
    public void loadProjectsAndEmployees(List<Employee> employeesList) throws IllegalArgumentException {
        super.loadProjectsAndEmployees(List.of(
                new Employee("1", "20", LocalDate.of(2015, 1, 1),
                        LocalDate.of(2020, 1, 1)),
                new Employee("2", "20", LocalDate.of(2015, 1, 1),
                        LocalDate.of(2020, 1, 1)),
                new Employee("2", "100", LocalDate.of(2015, 1, 1),
                        LocalDate.of(2020, 1, 1)),
                new Employee("1", "100", LocalDate.of(2015, 1, 1),
                        LocalDate.of(2020, 1, 1)),
                new Employee("3", "150", LocalDate.of(2015, 1, 1),
                        LocalDate.of(2020, 1, 1)),
                new Employee("4", "150", LocalDate.of(2015, 1, 1),
                        LocalDate.of(2020, 1, 1))
        ));
    }
}
