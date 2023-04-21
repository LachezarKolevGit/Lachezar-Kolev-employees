package bg.sirma.java.intern.employees;

import bg.sirma.java.intern.employees.employee.Employee;
import bg.sirma.java.intern.employees.exceptions.InvalidRowException;
import bg.sirma.java.intern.employees.exceptions.LongestWorkingPairNotPresentInFile;
import bg.sirma.java.intern.employees.exceptions.ProjectAlreadyAddedException;
import bg.sirma.java.intern.employees.filereader.CsvReader;
import bg.sirma.java.intern.employees.filereader.Reader;
import bg.sirma.java.intern.employees.ui.UI;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        Reader reader = new CsvReader();
        List<List<String>> records = null;
        try {
            records = reader.readFromFile();
        } catch (InvalidRowException e) {
            System.err.println("Exception occurred: " + e.getMessage());
            e.printStackTrace();
        }
        List<Employee> employeeList = null;
        try {
            employeeList = reader.getListOfEmployees(records);
        } catch (ProjectAlreadyAddedException e) {
            System.err.println("Exception occurred " + e.getMessage());
            e.printStackTrace();
        }
        UI ui = new UI();
        ui.init(employeeList);
        try {
            ui.printData();
        } catch (LongestWorkingPairNotPresentInFile e) {
            System.err.println("Exception occurred " + e.getMessage());
            e.printStackTrace();
        }
    }
}