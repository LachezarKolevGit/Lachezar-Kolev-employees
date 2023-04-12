package bg.sirma.java.intern.employees.projectmanager;

import bg.sirma.java.intern.employees.employee.Employee;

import java.time.LocalDate;
import java.util.*;

public class ProjectManager {
    private final Map<String, List<Employee>> projectsToEmployees;

    public ProjectManager() {
        projectsToEmployees = new HashMap<>();
    }

    public void loadProjectsAndEmployees(List<Employee> employeesList) throws IllegalArgumentException {
        if (employeesList == null || employeesList.isEmpty()) {
            throw new IllegalArgumentException();
        }

        for (Employee employee : employeesList) {
            Map<String, List<LocalDate>> projectsWorkedOnByEmployee = employee.getProjectWorkTime();
            for (Map.Entry<String, List<LocalDate>> entry : projectsWorkedOnByEmployee.entrySet()) {
                String currentProjectIndex = entry.getKey();
                if (projectsToEmployees.containsKey(currentProjectIndex)) {
                    projectsToEmployees.get(currentProjectIndex).add(employee);
                } else {
                    projectsToEmployees.put(currentProjectIndex, new ArrayList<>());
                    projectsToEmployees.get(currentProjectIndex).add(employee);
                }
            }
        }
    }

    /**
     * Finds the longest working pair of employees on a single project
     *
     * @return pair of employees
     */
    public List<Employee> findLongestWorkingPair() {
        int yearsWorked = 0;
        String longestWorkedOnProjectId = null;

        for (Map.Entry<String, List<Employee>> entry : projectsToEmployees.entrySet()) {
            List<Employee> list = entry.getValue();
            int currentTime = 0;
            for (Employee employee : list) {
                currentTime += employee.getYearsSpentOnProject(entry.getKey());
            }
            if (yearsWorked < currentTime) {
                yearsWorked = currentTime;
                longestWorkedOnProjectId = entry.getKey();
            }
        }
        System.out.println("Pair that worked longest together is: " + projectsToEmployees.get(longestWorkedOnProjectId) +
                " and they worked together for " + yearsWorked + "years");

        return projectsToEmployees.get(longestWorkedOnProjectId);
    }

    /**
     * Prints all the projects the pair have worked on
     *
     * @param employee1 first employee of the pair
     * @param employee2 second employee of the pair
     */
    public void getAllProjectsByPair(Employee employee1, Employee employee2) {
        if (employee1 == null || employee2 == null) {
            throw new IllegalArgumentException("Couldn't find projects by pair, because one the employees is null ");
        }
        for (Map.Entry<String, List<Employee>> entry : projectsToEmployees.entrySet()) {
            List<Employee> employeeList = entry.getValue();
            if (employeeList.contains(employee1) && employeeList.contains(employee2)) {
                int employee1ProjectTime = employee1.getYearsSpentOnProject(entry.getKey());
                int employee2ProjectTime = employee2.getYearsSpentOnProject(entry.getKey());
                int combinedTime = employee1ProjectTime + employee2ProjectTime;
                System.out.println(employee1.getId() + " | " + employee2.getId() + " | " + entry.getKey() + " | "
                        + combinedTime * 365); //multiplies by 365 to get days from years
            }
        }
    }

}
