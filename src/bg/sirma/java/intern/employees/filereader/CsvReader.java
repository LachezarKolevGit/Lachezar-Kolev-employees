package bg.sirma.java.intern.employees.filereader;

import bg.sirma.java.intern.employees.employee.Employee;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.*;

public class CsvReader implements Reader {
    private static final String COMMA_DELIMITER = ",";

    public CsvReader() {
    }

    /**
     * Reads all the data from the file
     *
     * @return all rows read from the file
     */
    @Override
    public List<List<String>> readFromFile() {
        List<List<String>> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("employees.csv"))) { //employees.csv
            String line;
            int counter = 0;
            while ((line = br.readLine()) != null) {
                if (counter == 0) { //skips the first row containing the column names
                    counter++;
                    continue;
                }
                String[] values = line.split(COMMA_DELIMITER);
                records.add(Arrays.asList(values));
            }
        } catch (IOException e) {
            System.err.println("Exception occurred " + e);
            e.printStackTrace();
        }
        return records;
    }

    /**
     * Takes a single row from the document and converts it to an Employee object
     * Parses the date taken as String from the csv document
     *
     * @param list - takes as parameter a single row read from the table
     * @return a Employee object
     */
    @Override
    public Employee convertData(List<String> list) {
        if (list == null || list.isEmpty()) {
            throw new IllegalArgumentException("Row can't be null or empty ");
        }
        Iterator<String> listIterator = list.listIterator();
        String employeeId = listIterator.next();
        String projectId = listIterator.next();
        String startingDate = listIterator.next();
        String endDate = listIterator.next();

        if (endDate.equals("null")) {
            endDate = String.valueOf(LocalDate.now());
        }

        return new Employee(employeeId, projectId, parseDateFormat(startingDate), parseDateFormat(endDate));
    }

    public LocalDate parseDateFormat(String date) {
        if (date == null || date.isBlank()) {
            throw new IllegalArgumentException("Date can't be null, empty or blank");
        }
        DateTimeFormatter dateTimeFormatter = new DateTimeFormatterBuilder()
                .parseCaseInsensitive()// For case-insensitive parsing
                .appendPattern("[d-M-uuuu]")
                .appendPattern("[uuuu-M-d]")
                .appendPattern("[uuuu/M/d]")
                .appendPattern("[d/M/uuuu]")
                .appendPattern("[d-MMM-uuuu]")
                .toFormatter(Locale.ENGLISH);
        //the sample data in the problem description is formatted: 2009-01-01

        return LocalDate.parse(date, dateTimeFormatter);
    }

    /**
     * Responsible for returning a list containing all the employees
     *
     * @param records - all the rows read from the opened file
     */
    @Override
    public List<Employee> getListOfEmployees(List<List<String>> records) {
        Map<String, Employee> savedEmployees = new HashMap<>();  //returns employee based on id
        for (List<String> list : records) {
            Employee employee = convertData(list);
            if (savedEmployees.containsKey(employee.getId())) {
                Employee alreadySavedEmployee = savedEmployees.get(employee.getId());
                Map<String, List<LocalDate>> projectWorkTime = employee.getProjectsToWorkTime();
                for (Map.Entry<String, List<LocalDate>> entry : projectWorkTime.entrySet()) {
                    String newProjectId = entry.getKey();
                    List<LocalDate> workPeriod = entry.getValue();
                    alreadySavedEmployee.addNewProject(newProjectId, workPeriod);
                }
            } else {
                savedEmployees.put(employee.getId(), employee);
            }
        }

        return new ArrayList<>(savedEmployees.values());
    }
}
