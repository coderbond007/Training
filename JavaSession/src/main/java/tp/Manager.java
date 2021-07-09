package tp;

import java.util.ArrayList;
import java.util.List;

public class Manager extends Employee {
    List<Employee> reportingEmployees;

    public Manager(String name, Integer id, String companyName) {
        super(name, id, companyName);
        reportingEmployees = new ArrayList<Employee>();
    }

    public void addEmployee(Employee employee) {
        reportingEmployees.add(employee);
    }
}
