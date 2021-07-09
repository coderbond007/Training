package net.media.training.designpattern.abstractfactory;

public abstract class AbstractPhoneFactory {
    public abstract MotherBoard getMotherBoard();

    public abstract Processor getProcessor();

    public abstract Screen getScreen();

    public abstract Case getCase();

    public Battery getBattery() {
        return new Battery();
    }

}
