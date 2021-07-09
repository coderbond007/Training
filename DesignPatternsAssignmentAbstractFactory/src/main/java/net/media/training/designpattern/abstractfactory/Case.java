package net.media.training.designpattern.abstractfactory;

/**
 * Created by IntelliJ IDEA.
 * User: goyalamit
 * Date: Jul 18, 2011
 * Time: 6:48:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class Case {
    private MotherBoard motherBoard;
    private Screen screen;

    public void attachMotherBoard(MotherBoard motherBoard) {
        if (motherBoard.State() != "running")
            throw new RuntimeException("Motherboard not functional");
        this.motherBoard = motherBoard;
    }

    public void attachScreen(Screen screen) {
        this.screen = screen;
    }

    public Screen getScreen() {
        return screen;
    }

    public MotherBoard getMotherBoard() {
        return motherBoard;
    }

    public Processor getProcessor() {
        return motherBoard.getProcessor();
    }

    public Battery getBattery() {
        return motherBoard.getBattery();
    }
}
