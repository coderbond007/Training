package net.media.training.designpattern.observer;

/**
 * Created by IntelliJ IDEA.
 * User: joelrosario
 * Date: Jul 20, 2011
 * Time: 9:10:09 PM
 * To change this template use File | Settings | File Templates.
 */
public class Game {
    private Sun sun;

    public Game(Sun sun) {
        this.sun = sun;
    }

    public void tick() {
        if (sun.isUp()) {
            sun.set();
        } else
            sun.rise();
    }
}