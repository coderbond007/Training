package net.media.training.designpattern.strategy;


import org.junit.Test;

/**
 * Created by IntelliJ IDEA.
 * User: path
 * Date: Jul 20, 2011
 * Time: 6:49:49 PM
 * To change this template use File | Settings | File Templates.
 */
public class SeniorEmployeeTest {
    @Test
    public void testShortNamesAreAllowed() {
        new SeniorEmployee().setName("Max Payne");
    }

    @Test(expected = RuntimeException.class)
    public void testNamesAbove50CharsAreNotAllowed() {
        new SeniorEmployee().setName("pneumonoultramicroscopicsilicovolcanokoniosis smith");
    }

    @Test(expected = RuntimeException.class)
    public void testSalaryOf100IsInValid() {
        new SeniorEmployee().setSalary(100);
    }

    @Test(expected = RuntimeException.class)
    public void testSalaryOf1001IsInvalid() {
        new SeniorEmployee().setSalary(1001);
    }

    @Test(expected = RuntimeException.class)
    public void testMonthsSpentIsNotNegative() {
        new SeniorEmployee().setMonthsSpent(-1);
    }

    @Test(expected = RuntimeException.class)
    public void testMonthsSpentCanNotBeLessThan60() {
        new SeniorEmployee().setMonthsSpent(59);
    }

    @Test(expected = RuntimeException.class)
    public void testBonusIsAtleast1() {
        new SeniorEmployee().setMaxBonus(0);
    }

    @Test(expected = RuntimeException.class)
    public void testManagerNameIsNotEmpty() {
        new SeniorEmployee().setManagerName("");
    }
}
