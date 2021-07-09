package net.media.training.live.srp;


public class Employee {
    private int empId;
    public static int TOTAL_LEAVES_ALLOWED = 30;
    public static int MONTHS_IN_YEAR = 12;
    private static final String NO_MANAGER = "None";

    private double monthlySalary;
    private String name;
    private String addressStreet;
    private String addressCity;
    private String addressCountry;
    private int leavesTaken;
    private String manager;
    private int yearsInOrg;
    private int[] leavesLeftPreviously;


    public Employee(int empId, double monthlySalary, String name, String addressStreet, String addressCity, String addressCountry, int leavesTaken, int[] leavesLeftPreviously) {
        this.empId = empId;
        this.monthlySalary = monthlySalary;
        this.name = name;
        this.addressStreet = addressStreet;
        this.addressCity = addressCity;
        this.addressCountry = addressCountry;
        this.leavesTaken = leavesTaken;
        this.leavesLeftPreviously = leavesLeftPreviously;
        this.yearsInOrg = leavesLeftPreviously.length;
    }

    public Employee() {
    }

    public String getManager() {
        return manager == null ? NO_MANAGER : manager;
    }

    public int getTotalLeavesLeftPreviously() {
        int years = 3;
        if (yearsInOrg < 3) {
            years = yearsInOrg;
        }
        int totalLeaveLeftPreviously = 0;
        for (int yearIndex = 0; yearIndex < years; yearIndex++) {
            totalLeaveLeftPreviously += leavesLeftPreviously[yearsInOrg - yearIndex - 1];
        }
        return totalLeaveLeftPreviously;
    }

    public int getEmpId() {
        return empId;
    }

    public String getName() {
        return name;
    }

    public int getLeavesTaken() {
        return leavesTaken;
    }

    public double getMonthlySalary() {
        return monthlySalary;
    }

    public int getRemainingLeaves() {
        return Employee.TOTAL_LEAVES_ALLOWED - getLeavesTaken();
    }

    public long getAnnualSalary() {
        return Math.round(getMonthlySalary() * MONTHS_IN_YEAR);
    }
    //other method related to customer
}
