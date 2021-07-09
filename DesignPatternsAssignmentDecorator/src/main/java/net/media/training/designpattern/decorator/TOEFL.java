package net.media.training.designpattern.decorator;

/**
 * Created by IntelliJ IDEA.
 * User: goyalamit
 * Date: Jul 18, 2011
 * Time: 10:41:37 AM
 * To change this template use File | Settings | File Templates.
 */
public class TOEFL extends GPAEval {

    @Override
    public boolean evaluate(Application theApp) {
        return super.evaluate(theApp) && theApp.getToefl() > 100;
    }
}
