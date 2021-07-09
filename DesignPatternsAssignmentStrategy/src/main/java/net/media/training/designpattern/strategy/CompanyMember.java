package net.media.training.designpattern.strategy;

import net.media.training.designpattern.validator.MinimumValidator;
import net.media.training.designpattern.validator.NameLengthValidator;
import net.media.training.designpattern.validator.NotNullValidator;

/**
 * Created by IntelliJ IDEA.
 * User: goyalamit
 * Date: Jul 18, 2011
 * Time: 1:14:31 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class CompanyMember {
    private String name;
    protected String mgrName;
    protected int salary;
    protected int monthsSpent;

    private static final MinimumValidator minimumSalaryValidator = new MinimumValidator(1);
    private static final NameLengthValidator nameLengthValidator = new NameLengthValidator(50);
    static final NotNullValidator notNullValidator = new NotNullValidator();

//    protected void validateLength(String val, int allowedLength) {
//        if (val.length() > allowedLength)
//            throw new RuntimeException("Invalid length:" + allowedLength);
//    }
//
//    protected void notEmpty(String val) {
//        if (val == null || val.length() == 0)
//            throw new RuntimeException("not empty check failed for value:" + val);
//    }
//
//    protected void atLeast(int val, int min) {
//        if (val < min)
//            throw new RuntimeException("at least check failed:" + val);
//    }
//
//    protected void atMost(int val, int max) {
//        if (val > max)
//            throw new RuntimeException("at least check failed:" + val);
//    }

    public void setName(String name) {
        nameLengthValidator.validate(name);
        this.name = name;
    }

    public void setSalary(int salary) {
        minimumSalaryValidator.validate(salary);
    }

    public void setManagerName(String name) {
        nameLengthValidator.validate(name);
    }
}
