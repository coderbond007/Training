package net.media.training.designpattern.validator;

import net.media.training.designpattern.exception.WrongSalaryException;

public class MaximumValidator implements Validator<Integer> {
    private int maximumFixedSalary;

    public MaximumValidator(int maximumFixedSalary) {
        this.maximumFixedSalary = maximumFixedSalary;
    }

    public void validate(Integer value) {
        if (value.compareTo(maximumFixedSalary) > 0) {
            throw new WrongSalaryException("Salary more than expected limit");
        }
    }
}
