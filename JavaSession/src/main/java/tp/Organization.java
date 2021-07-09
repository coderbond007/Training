package tp;

import java.util.HashMap;
import java.util.Map;

public class Organization {
    String companyName;
    int counterID = 1;
    Map<Integer, String> employeeIDMap;
    Map<String, Team> teamMap;
    Map<Integer, String> idPasswordMap;

    public Organization(String companyName) {
        this.companyName = companyName;
        employeeIDMap = new HashMap<Integer, String>();
        teamMap = new HashMap<String, Team>();
        idPasswordMap = new HashMap<Integer, String>();
    }

    public void addEmployee(String name, String teamName, String managerName) {
        Employee employee = createEmployee(name);
        Team currentTeam = teamMap.get(teamName);
        if (currentTeam == null) {
            currentTeam = new Team(companyName);
            Manager newManager = createManager(managerName);
            currentTeam.addManager(newManager);
            teamMap.put(teamName, currentTeam);
            currentTeam.addEmployee(employee);
        } else {
            if (currentTeam.manager != null) {
                currentTeam.addEmployee(employee);
            } else {
                throw new IllegalStateException("Manager not present in team");
            }
        }
    }

    public void addManager(String name) {
        Team team = new Team(companyName);
        Manager manager = createManager(name);
        team.addManager(manager);
    }

    private synchronized Employee createEmployee(String name) {
        employeeIDMap.put(counterID, name);
        Employee employee = new Employee(name, counterID, companyName);
        ++counterID;
        return employee;
    }

    private synchronized Manager createManager(String name) {
        employeeIDMap.put(counterID, name);
        Manager manager = new Manager(name, counterID, companyName);
        ++counterID;
        return manager;
    }
}