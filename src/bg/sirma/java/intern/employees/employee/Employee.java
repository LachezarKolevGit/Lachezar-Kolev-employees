package bg.sirma.java.intern.employees.employee;

import java.time.LocalDate;
import java.util.*;

public class Employee {
    private final String id;

    private final Map<String, List<LocalDate>> projectsToWorkTime;

    public Employee(String id, String projectId, LocalDate startedWorkOn, LocalDate finishedWorkOn) {
        this.id = id;
        projectsToWorkTime = new HashMap<>();
        projectsToWorkTime.put(projectId, new ArrayList<>());
        projectsToWorkTime.get(projectId).add(startedWorkOn);
        projectsToWorkTime.get(projectId).add(finishedWorkOn);
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "id='" + id + '\'' ;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(id, employee.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void addNewProject(String projectId, List<LocalDate> workPeriod) {
        projectsToWorkTime.put(projectId, new ArrayList<>());
        for (LocalDate date : workPeriod) {
            projectsToWorkTime.get(projectId).add(date);
        }
    }

    public Map<String, List<LocalDate>> getProjectsToWorkTime() {
        return Collections.unmodifiableMap(projectsToWorkTime);
    }

    public LocalDate getStartingDate(String projectId) {
        return projectsToWorkTime.get(projectId).get(0);
    }

    public LocalDate getEndDate(String projectId) {
        return projectsToWorkTime.get(projectId).get(1);
    }
}
