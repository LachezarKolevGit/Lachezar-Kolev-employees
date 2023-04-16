package bg.sirma.java.intern.employees.projectmanager;

import bg.sirma.java.intern.employees.employee.Employee;
import bg.sirma.java.intern.employees.pair.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ProjectManagerTest {
    private ProjectManager projectManager;

    @BeforeEach
    void init() {
        this.projectManager = new ProjectManager();
    }

    @Test
    void testCreatePairs() {
        ProjectManager projectManagerStub = new ProjectManagerStub();
        projectManagerStub.loadProjectsAndEmployees(null);
        Map<String, Pair> actualPairs = projectManagerStub.createPairs();

        Employee employee1 = new Employee("1", "20", LocalDate.of(2015, 1, 1),
                LocalDate.of(2020, 1, 1));
        Employee employee2 = new Employee("2", "20", LocalDate.of(2015, 1, 1),
                LocalDate.of(2020, 1, 1));
        Employee employee3 = new Employee("2", "100", LocalDate.of(2015, 1, 1),
                LocalDate.of(2020, 1, 1));
        Employee employee4 = new Employee("1", "100", LocalDate.of(2015, 1, 1),
                LocalDate.of(2020, 1, 1));
        Employee employee5 = new Employee("3", "150", LocalDate.of(2015, 1, 1),
                LocalDate.of(2020, 1, 1));
        Employee employee6 = new Employee("4", "150", LocalDate.of(2015, 1, 1),
                LocalDate.of(2020, 1, 1));

        Map<String, Pair> expectedPairs = new HashMap<>();
        expectedPairs.put("1", new Pair(employee2, employee1, 5 * 2));
        expectedPairs.put("2", new Pair(employee5, employee6, 5));

         assertEquals(expectedPairs, actualPairs, "Expected pairs does not match actual pairs");
    }

    @Test
    void testFindLongestWorkingPair() {
        Employee employee1 = new Employee("1", "20", LocalDate.of(2015, 1, 1),
                LocalDate.of(2020, 1, 1));
        Employee employee2 = new Employee("2", "20", LocalDate.of(2015, 1, 1),
                LocalDate.of(2020, 1, 1));
        Employee employee3 = new Employee("2", "100", LocalDate.of(2015, 1, 1),
                LocalDate.of(2020, 1, 1));
        Employee employee4 = new Employee("1", "100", LocalDate.of(2015, 1, 1),
                LocalDate.of(2020, 1, 1));
        Employee employee5 = new Employee("3", "150", LocalDate.of(2015, 1, 1),
                LocalDate.of(2020, 1, 1));
        Employee employee6 = new Employee("4", "150", LocalDate.of(2015, 1, 1),
                LocalDate.of(2020, 1, 1));

        Pair longestWorkingPair = new Pair(employee1, employee2, 5 * 12 * 2);

        Map<String, Pair> map = Map.of("1", longestWorkingPair,
                "2", new Pair(employee5, employee6, 5 * 12));

        Pair actualPair = projectManager.findLongestWorkingPair(map);
        Pair expectedPair = longestWorkingPair;

        assertEquals(expectedPair, actualPair, "Expected pair does not match actual pair");
    }

    @Test
    void testCalculatePairWorkTimeByYears() {
        String projectId = "1";
        Employee employee1 = new Employee("1", projectId, LocalDate.of(2015, 1, 1), LocalDate.of(2020, 1, 1));
        Employee employee2 = new Employee("1", projectId, LocalDate.of(2015, 1, 1), LocalDate.of(2020, 1, 1));
        long actualYears = projectManager.calculatePairWorkTimeByYears(projectId, employee1, employee2);
        long expectedYears = 5;
        assertEquals(expectedYears, actualYears, "Expected months does not match actual months");
    }
}