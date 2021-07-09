package net.media.training.designpattern.abstractfactory;

/**
 * Created by IntelliJ IDEA.
 * User: goyalamit
 * Date: Jul 18, 2011
 * Time: 6:34:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class PhoneMaker {
    public Case buildPhone(String phoneType) {
        AbstractPhoneFactory abstractPhoneFactory;
        if (phoneType.equals("Android")) {
            abstractPhoneFactory = new AndroidPhoneFactory();
        } else {
            abstractPhoneFactory = new IphonePhoneFactory();
        }

        Case phoneCase = abstractPhoneFactory.getCase();
        MotherBoard motherBoard = abstractPhoneFactory.getMotherBoard();
        Battery battery = abstractPhoneFactory.getBattery();
        Processor processor = abstractPhoneFactory.getProcessor();
        Screen screen = abstractPhoneFactory.getScreen();
        motherBoard.attachBattery(battery);
        motherBoard.attachProcessor(processor);
        phoneCase.attachScreen(screen);
        phoneCase.attachMotherBoard(motherBoard);
        return phoneCase;
    }
}
