package bg.sirma.java.intern.employees.filereader;

import bg.sirma.java.intern.employees.employee.EmployeeDTO;
import bg.sirma.java.intern.employees.exceptions.InvalidRowException;
import bg.sirma.java.intern.employees.exceptions.ProjectAlreadyAddedException;
import bg.sirma.java.intern.employees.exceptions.StartDateAfterEndDateException;
import bg.sirma.java.intern.employees.employee.Employee;

import java.util.List;

public interface Reader {
    List<List<String>> readFromFile() throws InvalidRowException;

    EmployeeDTO convertData(List<String> list) throws StartDateAfterEndDateException;

    List<Employee> getListOfEmployees(List<List<String>> records) throws ProjectAlreadyAddedException;
}
