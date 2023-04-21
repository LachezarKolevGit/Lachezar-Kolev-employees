package bg.sirma.java.intern.employees.filereader;

import bg.sirma.java.intern.employees.employee.EmployeeDTO;
import bg.sirma.java.intern.employees.exceptions.InvalidRowException;
import bg.sirma.java.intern.employees.exceptions.ProjectAlreadyAddedException;
import bg.sirma.java.intern.employees.exceptions.StartDateAfterEndDateException;
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
    public List<List<String>> readFromFile() throws InvalidRowException {
        List<List<String>> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("employees.csv"))) {
            String line;
            int counter = 0;
            while ((line = br.readLine()) != null) {
                if (counter == 0) { //skips the first row containing the column names
                    counter++;
                    continue;
                }
                counter++;
                String[] values = line.split(COMMA_DELIMITER);
                if (!validRow(values)) {
                    throw new InvalidRowException("Row number: " + counter + " is not correctly formatted");
                }
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
    public EmployeeDTO convertData(List<String> list) throws StartDateAfterEndDateException {
        if (list == null || list.isEmpty()) {
            throw new IllegalArgumentException("Row can't be null or empty ");
        }
        Iterator<String> listIterator = list.listIterator();
        String employeeId = listIterator.next();
        String projectId = listIterator.next();
        String startingDateString = listIterator.next();
        String endDateString = listIterator.next();

        if (endDateString.equals("null")) {
            endDateString = String.valueOf(LocalDate.now());
        }

        LocalDate startDate = parseDateFormat(startingDateString);
        LocalDate endDate = parseDateFormat(endDateString);
        if (endDate.isBefore(startDate)) {
            throw new StartDateAfterEndDateException("Start date cannot be after end date");
        }

        return new EmployeeDTO(employeeId, projectId, startDate, endDate);
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
    public List<Employee> getListOfEmployees(List<List<String>> records) throws ProjectAlreadyAddedException {
        if (records == null || records.isEmpty()) {
            throw new IllegalArgumentException("Rows read from file are null or empty");
        }
        Map<String, Employee> savedEmployees = new HashMap<>();  //returns employee based on id
        try {
            for (List<String> list : records) {
                EmployeeDTO employeeDTO = convertData(list);
                if (savedEmployees.containsKey(employeeDTO.id())) {
                    Employee alreadySavedEmployee = savedEmployees.get(employeeDTO.id());
                    if (alreadySavedEmployee.getProjectsToWorkTime().containsKey(employeeDTO.projectId())) {
                        throw new ProjectAlreadyAddedException("Duplicate project id is not allowed");
                    }
                    alreadySavedEmployee.addNewProject(employeeDTO.projectId(), List.of(employeeDTO.startOfWork(), employeeDTO.endOfWork()));
                } else {
                    savedEmployees.put(employeeDTO.id(),
                            new Employee(employeeDTO.id(), employeeDTO.projectId(), employeeDTO.startOfWork(), employeeDTO.endOfWork()));
                }
            }
        } catch (StartDateAfterEndDateException e) {
            System.err.println("Exception occurred " + e.getMessage());
            e.printStackTrace();
        }

        return new ArrayList<>(savedEmployees.values());
    }

    public boolean validRow(String[] values) {
        String regexDate = "^(0[1-9]|[12][0-9]|3[01])[-/.](0[1-9]|1[012])[-/.](19|20)\\d\\d$|^(19|20)\\d\\d[-/.](0[1-9]|1[012])[-/.](0[1-9]|[12][0-9]|3[01])$";
        String regexDateOrNull = "^(0[1-9]|[12][0-9]|3[01])[-/.](0[1-9]|1[012])[-/.](19|20)\\d\\d$|^(19|20)\\d\\d[-/.](0[1-9]|1[012])[-/.](0[1-9]|[12][0-9]|3[01])$|^null$";

        if (!values[0].matches("[0-9]+")) {
            return false;
        }
        if (!values[1].matches("[0-9]+")) {
            return false;
        }
        if (!values[2].matches(regexDate)) {
            return false;
        }
        if (!values[3].matches(regexDateOrNull)) {
            return false;
        }
        return true;
    }
}

