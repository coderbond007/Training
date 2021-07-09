package net.media.training.designpattern.strategy;

import net.media.training.designpattern.validator.MinimumValidator;

/**
 * Created by IntelliJ IDEA.
 * User: goyalamit
 * Date: Jul 18, 2011
 * Time: 1:31:37 PM
 * To change this template use File | Settings | File Templates.
 */
public class Employee extends GenericEmployee {
    private int maxAllowedLeaves;
    private static final MinimumValidator minimumLeavesValidator = new MinimumValidator(1);

    @Override
    public void setSalary(int salary) {
        super.setSalary(salary);
        this.salary = salary;
    }

    @Override
    public void setMonthsSpent(int months) {
        super.setMonthsSpent(months);
        this.monthsSpent = months;
    }

    public void setMaxAllowedLeaves(int leaves) {
        minimumLeavesValidator.validate(leaves);
        this.maxAllowedLeaves = leaves;
    }
}

