package net.media.training.designpattern.observer;

import java.util.ArrayList;
import java.util.List;

public class GameTestClient {
    public Person person;
    public Sun sun;
    public Dog dog;
    public Cat cat;
    public Robot robot;
    public Game game;

    public void setup() {
        person = new Person();
        dog = new Dog();
        cat = new Cat();
        robot = new Robot();
        List<Observer> observerList = new ArrayList<Observer>();
        observerList.add(robot);
        observerList.add(person);
        observerList.add(dog);
        observerList.add(cat);
        sun = new Sun(observerList);
        game = new Game(sun);
    }

    public void everyoneGoesOut() {
        person.goOutdoors();
        robot.goOutdoors();
        dog.goOutdoors();
        cat.goOutdoors();
    }

    public void everyoneOutdoorsComesIn() {
        if (person.isOutdoors())
            person.goIndoors();
        if (robot.isOutdoors())
            robot.goIndoors();
        if (cat.isOutdoors())
            cat.goIndoors();
        if (dog.isOutdoors())
            dog.goIndoors();
    }

    public void tickOnce() {
        game.tick();
    }

    public void tickTwice() {
        game.tick();
        game.tick();
    }

    public boolean outdoorsCharactersFeelWarm() {
        if (person.isOutdoors() && !person.isFeelingWarm())
            return false;
        if (cat.isOutdoors() && !cat.isFeelingWarm())
            return false;
        if (dog.isOutdoors() && !dog.isFeelingWarm())
            return false;
        if (robot.isOutdoors() && !robot.isFeelingWarm())
            return false;

        if (!person.isOutdoors() && person.isFeelingWarm())
            return false;
        if (!cat.isOutdoors() && cat.isFeelingWarm())
            return false;
        if (!dog.isOutdoors() && dog.isFeelingWarm())
            return false;
        if (!robot.isOutdoors() && robot.isFeelingWarm())
            return false;

        return true;
    }

    public boolean allFeelCold() {
        return !person.isFeelingWarm() && !cat.isFeelingWarm() && !dog.isFeelingWarm() && !robot.isFeelingWarm();
    }
}