package net.media.training.designpattern.strategy;

import net.media.training.designpattern.validator.MinimumValidator;

/**
 * Created by IntelliJ IDEA.
 * User: goyalamit
 * Date: Jul 18, 2011
 * Time: 1:35:40 PM
 * To change this template use File | Settings | File Templates.
 */
public class SeniorEmployee extends GenericEmployee {
    private static final MinimumValidator minimumSalaryValidator = new MinimumValidator(200);
    private static final MinimumValidator minimumMonthsValidator = new MinimumValidator(60);
    private static final MinimumValidator maximumBonusValidator = new MinimumValidator(1);
    private int setMaxBonus;

    @Override
    public void setSalary(int salary) {
        super.setSalary(salary);
        minimumSalaryValidator.validate(salary);
        this.salary = salary;
    }

    @Override
    public void setMonthsSpent(int months) {
        super.setMonthsSpent(months);
        minimumMonthsValidator.validate(months);
        this.monthsSpent = months;
    }

    public void setMaxBonus(int bonus) {
        maximumBonusValidator.validate(bonus);
        this.setMaxBonus = bonus;
    }
}
