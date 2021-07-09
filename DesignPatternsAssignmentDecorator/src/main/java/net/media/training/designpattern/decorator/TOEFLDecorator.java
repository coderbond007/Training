package net.media.training.designpattern.decorator;

public class TOEFLDecorator extends DecoratorList {

    TOEFLDecorator(Criteria criteria) {
        super(criteria);
    }

    public boolean evaluate(Application theApp) {
        if (super.evaluate(theApp)) {
            System.out.println("GREEval called");
            return theApp.getToefl() > 100;
        } else {
            return false;
        }
    }
}
