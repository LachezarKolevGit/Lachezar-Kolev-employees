package bg.sirma.java.intern.employees.filereader;

import bg.sirma.java.intern.employees.employee.Employee;

import java.util.List;

public interface Reader {
    List<List<String>> readFromFile();

    Employee convertData(List<String> list);

    List<Employee> getListOfEmployees(List<List<String>> records);
}
