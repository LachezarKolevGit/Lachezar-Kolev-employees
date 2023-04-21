package bg.sirma.java.intern.employees.pair;

import bg.sirma.java.intern.employees.employee.Employee;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Pair {
    private final Employee employee1;
    private final Employee employee2;
    private long totalWorkTimeInMonths;

    public Pair(Employee employee1Id, Employee employee2Id, long totalWorkingTime) {
        this.employee1 = employee1Id;
        this.employee2 = employee2Id;
        this.totalWorkTimeInMonths = totalWorkingTime;
    }

    public Employee getEmployee1() {
        return employee1;
    }

    public Employee getEmployee2() {
        return employee2;
    }

    public long getTotalWorkTimeInMonths() {
        return totalWorkTimeInMonths;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair pair = (Pair) o;
        return totalWorkTimeInMonths == pair.totalWorkTimeInMonths && Objects.equals(employee1, pair.employee1) && Objects.equals(employee2, pair.employee2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employee1, employee2, totalWorkTimeInMonths);
    }

    @Override
    public String toString() {
        return "Pair{" +
                ", employee1=" + employee1 +
                ", employee2=" + employee2 +
                ", totalWorkTime=" + totalWorkTimeInMonths +
                " days}" + '\n';
    }

    public void addWorkTime(long workTimeToBeAdded) {
        totalWorkTimeInMonths += workTimeToBeAdded;
    }

    public Map<String, List<LocalDate>> getProjectsOfPair(){
        return Collections.unmodifiableMap(employee1.getProjectsToWorkTime());
    }

}
