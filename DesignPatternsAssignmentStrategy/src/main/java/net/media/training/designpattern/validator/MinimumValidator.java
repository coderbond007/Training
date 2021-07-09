package net.media.training.designpattern.validator;

import net.media.training.designpattern.exception.WrongSalaryException;

public class MinimumValidator implements Validator<Integer> {
    private int minimumFixedSalary;

    public MinimumValidator(int minimumFixedSalary) {
        this.minimumFixedSalary = minimumFixedSalary;
    }

    public void validate(Integer value) {
        if (value.compareTo(minimumFixedSalary) < 0) {
            throw new WrongSalaryException("Salary less than expected limit");
        }
    }
}
