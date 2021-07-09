package net.media.training.live.srp;

public class EmployeeOutputter {
    public String toHtml(Employee employee) {
        String str = "<div>" +
                "<h1>Employee Info</h1>" +
                "<div id='emp" + employee.getEmpId() + "'>" +
                "<span>" + employee.getName() + "</span>" +
                "<div class='left'>" +
                "<span>Leave Left :</span>" +
                "<span>Annual Salary:</span>" +
                "<span>Manager:</span>" +
                "<span>Reimbursable Leave:</span>" +
                "</div>";
        str += "<div class='right'><span>" + employee.getRemainingLeaves() + "</span>";
        str += "<span>" + employee.getAnnualSalary() + "</span>";
        str += "<span>" + employee.getManager() + "</span>";
        str += "<span>" + employee.getTotalLeavesLeftPreviously() + "</span>";
        return str + "</div> </div>";
    }
}