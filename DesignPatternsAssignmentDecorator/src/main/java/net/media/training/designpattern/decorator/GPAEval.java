package net.media.training.designpattern.decorator;

/**
 * Created by IntelliJ IDEA.
 * User: goyalamit
 * Date: Jul 18, 2011
 * Time: 10:26:23 AM
 * To change this template use File | Settings | File Templates.
 */
public class GPAEval implements Criteria {
    public boolean evaluate(Application theApp) {
        System.out.println("GPAEval called");
        return theApp.getGpa() > 8;
    }
}
