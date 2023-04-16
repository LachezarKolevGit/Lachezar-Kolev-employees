package bg.sirma.java.intern.employees.projectmanager;

import bg.sirma.java.intern.employees.employee.Employee;
import bg.sirma.java.intern.employees.pair.Pair;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class ProjectManager {
    private final Map<String, List<Employee>> projectsToEmployees;

    public ProjectManager() {
        projectsToEmployees = new HashMap<>();
    }

    /**
     * Loads the data in a map allowing direct access
     *
     * @param employeesList list of employees
     * @throws IllegalArgumentException if the passed employeesList is null or empty
     */
    public void loadProjectsAndEmployees(List<Employee> employeesList) throws IllegalArgumentException {
        if (employeesList == null || employeesList.isEmpty()) {
            throw new IllegalArgumentException();
        }

        for (Employee employee : employeesList) {
            Map<String, List<LocalDate>> projectsWorkedOnByEmployee = employee.getProjectsToWorkTime();
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
     * Forms pairs of employees
     *
     * @return map containing all the pairs and attached ids to them
     */
    public Map<String, Pair> createPairs() {
        Map<Integer, Pair> hashToPair = new HashMap<>();
        Map<String, Pair> idToPair = new HashMap<>();
        int idCounter = 1;
        for (Map.Entry<String, List<Employee>> entry : projectsToEmployees.entrySet()) {
            String currentProjectIndex = entry.getKey();
            List<Employee> employeeList = entry.getValue();
            long workTime = calculatePairWorkTimeByYears(currentProjectIndex, employeeList.get(0), employeeList.get(1));

            int hashCode = employeeList.get(0).hashCode();
            hashCode += employeeList.get(1).hashCode();
            Pair pair = new Pair(employeeList.get(0), employeeList.get(1), workTime);

            //uses hashCode to make sure that a new pair that has mirrored ids won't be added as a separate pair
            if (hashToPair.containsKey(hashCode)) {
                hashToPair.get(hashCode).addWorkTime(workTime);
            } else {
                hashToPair.put(hashCode, pair);
                idToPair.put(String.valueOf(idCounter++), pair);
            }
        }
        System.out.println("Pairs are " + idToPair);
        return Collections.unmodifiableMap(idToPair);
    }

    /**
     * Find the longest working together pair
     *
     * @param idToPair map containing the pairs
     * @return the pair which worked together the most
     */
    public Pair findLongestWorkingPair(Map<String, Pair> idToPair) {
        String idOfLongestWorkingPair = null;
        long mostMonthsWorkedTogether = 0;
        for (Map.Entry<String, Pair> entry : idToPair.entrySet()) {
            Pair pair = entry.getValue();
            long workTime = pair.getTotalWorkTimeInMonths();
            if (workTime > mostMonthsWorkedTogether) {
                mostMonthsWorkedTogether = workTime;
                idOfLongestWorkingPair = entry.getKey();
            }
        }
        return idToPair.get(idOfLongestWorkingPair);
    }

    /**
     * Returns a map of the projects and the period of mutual work of the pair
     *
     * @param pair of employees who worked together on common projects
     * @return all the projects of the pair
     */
    public Map<String, List<LocalDate>> getAllProjectsByPair(Pair pair) {
        if (pair == null) {
            throw new IllegalArgumentException("Couldn't find projects by pair, because pair is null ");
        }
        return pair.getProjectsOfPair();
    }

    /**
     * Calculates the time spent by the pair working together
     *
     * @param projectId id of the project the pair worked on
     * @param employee1 first employee of the pair
     * @param employee2 second employee of the pair
     * @return the period which the pair spent working together in months
     */
    public long calculatePairWorkTimeByYears(String projectId, Employee employee1, Employee employee2) {
        LocalDate employee1StartingDate = employee1.getStartingDate(projectId);
        LocalDate employee1EndDate = employee1.getEndDate(projectId);

        LocalDate employee2StartingDate = employee2.getStartingDate(projectId);
        LocalDate employee2EndDate = employee2.getEndDate(projectId);

        if (employee1EndDate.isBefore(employee2StartingDate) ||
                employee2EndDate.isBefore(employee1StartingDate)) {
            return 0;
        }

        LocalDate pairStartWorkDate = employee1StartingDate.isBefore(employee2StartingDate) ?
                employee2StartingDate : employee1StartingDate;

        LocalDate pairEndWorkDate = employee1EndDate.isBefore(employee2EndDate) ?
                employee1EndDate : employee2EndDate;

        return ChronoUnit.YEARS.between(pairStartWorkDate, pairEndWorkDate);
    }
}
