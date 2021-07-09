package tp;

public class Team {
    Manager manager;
    String companyName;

    public Team(String companyName) {
        this.companyName = companyName;
    }

    public void addManager(Manager manager) {
        this.manager = manager;
    }

    public void addEmployee(Employee employee) {
        manager.addEmployee(employee);
    }
}