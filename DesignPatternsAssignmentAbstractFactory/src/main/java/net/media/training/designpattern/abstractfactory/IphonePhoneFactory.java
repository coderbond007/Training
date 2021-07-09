package net.media.training.designpattern.abstractfactory;

public class IphonePhoneFactory extends AbstractPhoneFactory{
    @Override
    public MotherBoard getMotherBoard() {
        return new IphoneMotherBoard();
    }

    @Override
    public Processor getProcessor() {
        return new IphoneProcessor();
    }

    @Override
    public Screen getScreen() {
        return new IphoneScreen();
    }

    @Override
    public Case getCase() {
        return new IphoneCase();
    }
}
