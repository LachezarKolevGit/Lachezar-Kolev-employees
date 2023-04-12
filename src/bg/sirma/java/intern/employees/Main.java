package bg.sirma.java.intern.employees;

import bg.sirma.java.intern.employees.employee.Employee;
import bg.sirma.java.intern.employees.filereader.CsvReader;
import bg.sirma.java.intern.employees.filereader.Reader;
import bg.sirma.java.intern.employees.ui.UI;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        Reader reader = new CsvReader();
        List<List<String>> records = reader.readFromFile();
        List<Employee> employeeList = reader.getListOfEmployees(records);
        UI ui = new UI();
        ui.init(employeeList);
        ui.printData();
    }
}