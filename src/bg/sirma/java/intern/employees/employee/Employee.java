package bg.sirma.java.intern.employees.employee;

import java.time.LocalDate;
import java.time.Period;
import java.util.*;

public class Employee {
    private final String id;

    private final Map<String, List<LocalDate>> projectWorkTime;

    public Employee(String id, String projectId, LocalDate startedWorkOn, LocalDate finishedWorkOn) {
        this.id = id;
        projectWorkTime = new HashMap<>();
        projectWorkTime.put(projectId, new ArrayList<>());
        projectWorkTime.get(projectId).add(startedWorkOn);
        projectWorkTime.get(projectId).add(finishedWorkOn);
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id='" + id + '\'' +
                ", projectWorkTime=" + projectWorkTime +
                '}';
    }

    public void addNewProject(String projectId, List<LocalDate> workPeriod){
        projectWorkTime.put(projectId, new ArrayList<>());
        for (LocalDate date : workPeriod){
            projectWorkTime.get(projectId).add(date);
        }
    }

    public Map<String, List<LocalDate>> getProjectWorkTime() {
        return Collections.unmodifiableMap(projectWorkTime);
    }

    public int getYearsSpentOnProject(String projectId) {
        List<LocalDate> timePeriodList = projectWorkTime.get(projectId);
        return Period.between(timePeriodList.get(0), timePeriodList.get(1)).getYears();
    }
}
