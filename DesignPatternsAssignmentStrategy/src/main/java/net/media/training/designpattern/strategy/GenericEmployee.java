package net.media.training.designpattern.strategy;

import net.media.training.designpattern.validator.MaximumValidator;
import net.media.training.designpattern.validator.MinimumValidator;
import net.media.training.designpattern.validator.NotNullValidator;

/**
 * Created by IntelliJ IDEA.
 * User: goyalamit
 * Date: Jul 18, 2011
 * Time: 1:27:08 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class GenericEmployee extends CompanyMember {
    private static MaximumValidator maximumSalaryValidator = new MaximumValidator(1000);
    private static MinimumValidator minimumMonthValidator = new MinimumValidator(0);
    private static NotNullValidator notNullValidator = new NotNullValidator();


    public void setSalary(int salary) {
        super.setSalary(salary);
        maximumSalaryValidator.validate(salary);
    }

    public void setManagerName(String name) {
        super.setManagerName(name);
        notNullValidator.validate(name);
        this.mgrName = name;
    }

    public void setMonthsSpent(int months) {
        minimumMonthValidator.validate(months);
    }
}
