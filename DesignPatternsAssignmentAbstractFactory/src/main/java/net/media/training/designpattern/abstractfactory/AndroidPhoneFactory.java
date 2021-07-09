package net.media.training.designpattern.abstractfactory;

public class AndroidPhoneFactory extends AbstractPhoneFactory {
    @Override
    public MotherBoard getMotherBoard() {
        return new AndroidMotherBoard();
    }

    @Override
    public Processor getProcessor() {
        return new AndroidProcessor();
    }

    @Override
    public Screen getScreen() {
        return new AndroidScreen();
    }

    @Override
    public Case getCase() {
        return new AndroidCase();
    }
}
