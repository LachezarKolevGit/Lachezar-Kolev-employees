package bg.sirma.java.intern.employees.ui;

import bg.sirma.java.intern.employees.employee.Employee;
import bg.sirma.java.intern.employees.projectmanager.ProjectManager;

import java.util.List;

public class UI {

    private final ProjectManager projectManager;

    public UI() {
        projectManager = new ProjectManager();
    }

    public void init(List<Employee> employeeList) {
        projectManager.loadProjectsAndEmployees(employeeList);
    }

    public void printData() {
        List<Employee> pair = projectManager.findLongestWorkingPair();
        System.out.println("Employee ID #1 | Employee ID #2 | Project ID | Days worked");
        projectManager.getAllProjectsByPair(pair.get(0), pair.get(1));
    }
}
