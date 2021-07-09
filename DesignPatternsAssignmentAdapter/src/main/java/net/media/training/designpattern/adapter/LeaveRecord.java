package net.media.training.designpattern.adapter;

/**
 * Created with IntelliJ IDEA.
 * User: joelrosario
 * Date: 03/07/13
 * Time: 7:35 PM
 * To change this template use File | Settings | File Templates.
 */
public interface LeaveRecord {
    String getMostAbsentEmployee();

    int getEmployeeAbsences(String employeeName);
}
