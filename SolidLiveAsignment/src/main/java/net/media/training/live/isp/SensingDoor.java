package net.media.training.live.isp;

/**
 * Created by IntelliJ IDEA.
 * User: goyalamit
 * Date: Jul 11, 2011
 * Time: 4:21:11 PM
 * To change this template use File | Settings | File Templates.
 */
public class SensingDoor extends Door implements SensorClient {

    public SensingDoor(Sensor sensor) {
        sensor.register(this);
    }

    public void proximityCallback() {
        this.isOpened = true;
    }
}
