package net.media.training.designpattern.decorator;

public class GREDecorator extends DecoratorList {

    GREDecorator(Criteria criteria) {
        super(criteria);
    }

    public boolean evaluate(Application theApp) {
        if (super.evaluate(theApp)) {
            System.out.println("GREEval called");
            return theApp.getGre() > 1450;
        } else {
            return false;
        }
    }
}
