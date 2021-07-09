package net.media.training.designpattern.adapter;

import net.media.training.designpattern.adapter.thirdparty.ThirdPartyLeaveRecord;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by IntelliJ IDEA.
 * User: goyalamit
 * Date: Jul 21, 2011
 * Time: 5:40:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class AdapterTest {

    /*Uncomment the code and make it compilable*/
    @Test
    public void testGetDisplayWallOfShame() throws Exception {
        FrontDoorRegister frontDoorRegister = new FrontDoorRegister(new ThirdPartyLeaveRecord());
        assertEquals("Super Commando Dhruv has been the most absent employee, shame!", frontDoorRegister.getDisplayWallOfShame());
    }

    @Test
    public void salaryDisbersmentTest() {
      SalaryDisberser salaryDisberser = new SalaryDisberser();
      assertEquals(10000, salaryDisberser.salary("James Bond"));
      assertEquals(8000, salaryDisberser.salary("Austin Powers"));
      assertEquals(5000, salaryDisberser.salary("Super Commando Dhruv"));
    }
}
