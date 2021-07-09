package net.media.training.designpattern.observer;

/**
 * Created by IntelliJ IDEA.
 * User: joelrosario
 * Date: Jul 21, 2011
 * Time: 12:26:53 AM
 * To change this template use File | Settings | File Templates.
 */
public class CharacterTestClient {
    private Cat cat;
    private Person person;
    private Dog dog;
    private Robot robot;

    public CharacterTestClient() {
        robot = new Robot();
        dog = new Dog();
        person = new Person();
        cat = new Cat();
    }

    public boolean testOutdoors() {
        robot.goOutdoors();
        person.goOutdoors();
        cat.goOutdoors();
        dog.goOutdoors();

        return robot.isOutdoors() && person.isOutdoors() && cat.isOutdoors() && dog.isOutdoors();
    }

    public boolean testSunRose() {
        robot.notifySunRose();
        person.notifySunRose();
        cat.notifySunRose();
        dog.notifySunRose();

        return robot.isFeelingWarm() && person.isFeelingWarm() && cat.isFeelingWarm() && dog.isFeelingWarm();
    }

    public boolean testSunSet() {
        robot.notifySunSet();
        person.notifySunSet();
        cat.notifySunSet();
        dog.notifySunSet();

        return !robot.isFeelingWarm() && !person.isFeelingWarm() && !cat.isFeelingWarm() && !dog.isFeelingWarm();
    }

    public boolean testIndoors() {
        robot.goIndoors();
        person.goIndoors();
        cat.goIndoors();
        dog.goIndoors();

        return !robot.isOutdoors() && !person.isOutdoors() && !cat.isOutdoors() && !dog.isOutdoors();
    }
}
