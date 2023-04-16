package bg.sirma.java.intern.employees.ui;

import bg.sirma.java.intern.employees.employee.Employee;
import bg.sirma.java.intern.employees.pair.Pair;
import bg.sirma.java.intern.employees.projectmanager.ProjectManager;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

public class UI {

    private final ProjectManager projectManager;

    public UI() {
        projectManager = new ProjectManager();
    }

    public void init(List<Employee> employeeList) {
        projectManager.loadProjectsAndEmployees(employeeList);
    }

    public void printData() {
        Map<String, Pair> pairs = projectManager.createPairs();
        Pair pair = projectManager.findLongestWorkingPair(pairs);
        Map<String, List<LocalDate>> map = projectManager.getAllProjectsByPair(pair);

        System.out.println("Employee ID #1 | Employee ID #2 | Project ID | Days worked");
        for (Map.Entry<String, List<LocalDate>> entry : map.entrySet()){
            System.out.println(pair.getEmployee1() + "           " + " " + pair.getEmployee2() + "         " + entry.getKey() + "         " + ChronoUnit.DAYS.between(entry.getValue().get(0), entry.getValue().get(1) ));
        }
    }
}
