package net.media.training.designpattern.strategy;

import org.junit.Test;

/**
 * Created by IntelliJ IDEA.
 * User: path
 * Date: Jul 20, 2011
 * Time: 9:14:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class ManagerTest {
    @Test
    public void testShortNamesAreAllowed() {
        new Manager().setName("Max Payne");
    }

    @Test(expected = RuntimeException.class)
    public void testNamesAbove50CharsAreNotAllowed() {
        new Manager().setName("pneumonoultramicroscopicsilicovolcanokoniosis smith");
    }

    @Test(expected = RuntimeException.class)
    public void testSalaryOf0IsInvalid() {
        new Manager().setSalary(0);
    }

    @Test
    public void testSalaryOf100IsValid() {
        new Manager().setSalary(100);
    }

    @Test
    public void testSalaryOf1001IsValid() {
        new Manager().setSalary(1001);
    }

    @Test
    public void testMonthsSpentCanBe1() {
        new Manager().setMonthsSpent(1);
    }

    @Test(expected = RuntimeException.class)
    public void testManagementBandIsNotEmpty() {
        new Manager().setManagementBand("");
    }

}
