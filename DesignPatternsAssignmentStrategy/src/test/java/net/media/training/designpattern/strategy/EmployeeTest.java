package net.media.training.designpattern.strategy;

import org.junit.Test;

/**
 * Created by IntelliJ IDEA.
 * User: path
 * Date: Jul 20, 2011
 * Time: 6:17:53 PM
 * To change this template use File | Settings | File Templates.
 */
public class EmployeeTest {
    @Test
    public void testShortNamesAreAllowed() {
        new Employee().setName("Max Payne");
    }

    @Test(expected = RuntimeException.class)
    public void testNamesAbove50CharsAreNotAllowed() {
        new Employee().setName("pneumonoultramicroscopicsilicovolcanokoniosis smith");
    }

    @Test(expected = RuntimeException.class)
    public void testSalaryOf0IsInvalid() {
        new Employee().setSalary(0);
    }

    @Test
    public void testSalaryOf100IsValid() {
        new Employee().setSalary(100);
    }

    @Test(expected = RuntimeException.class)
    public void testSalaryOf1001IsInvalid() {
        new Employee().setSalary(1001);
    }

    @Test(expected = RuntimeException.class)
    public void testMonthsSpentIsNotNegative() {
        new Employee().setMonthsSpent(-1);
    }

    @Test
    public void testMonthsSpentCanBe1() {
        new Employee().setMonthsSpent(1);
    }

    @Test(expected = RuntimeException.class)
    public void testMaxAllowedLeavesIsAtleast1() {
        new Employee().setMaxAllowedLeaves(0);
    }

    @Test(expected = RuntimeException.class)
    public void testManagerNameIsNotEmpty() {
        new Employee().setManagerName("");
    }
}
