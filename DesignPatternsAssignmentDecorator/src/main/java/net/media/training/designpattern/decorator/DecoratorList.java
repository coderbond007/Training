package net.media.training.designpattern.decorator;

public class DecoratorList implements Criteria {

    private Criteria criteria;

    DecoratorList(Criteria criteria) {
        this.criteria = criteria;
    }

    public boolean evaluate(Application theApp) {
        return this.criteria.evaluate(theApp);
    }
}
