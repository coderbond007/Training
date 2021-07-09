package net.media.training.designpattern.adapter;

/**
 * Created by IntelliJ IDEA.
 * User: goyalamit
 * Date: Jul 21, 2011
 * Time: 5:16:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class FrontDoorRegister {
    private LeaveRecord leaveRecord;

    public FrontDoorRegister(LeaveRecord leaveRecord) {
        this.leaveRecord = leaveRecord;
    }

    public String getDisplayWallOfShame() {
        String name = "";

        name = leaveRecord.getMostAbsentEmployee();
        return name + " has been the most absent employee, shame!";
    }
}
