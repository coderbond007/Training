package net.media.training.designpattern.decorator;


/**
 * Created by IntelliJ IDEA.
 * User: goyalamit
 * Date: Jul 18, 2011
 * Time: 10:24:57 AM
 * To change this template use File | Settings | File Templates.
 */
public class Registrar {

    public boolean evaluate(Application theApp, Criteria criteria) {
        return criteria.evaluate(theApp);
    }

}
