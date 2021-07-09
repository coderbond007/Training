package net.media.training.designpattern.observer;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by IntelliJ IDEA.
 * User: joelrosario
 * Date: Jul 20, 2011
 * Time: 9:33:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class GameTests {
    GameTestClient gameTestClient;

    @Before
    public void setup() {
        gameTestClient = new GameTestClient();
        gameTestClient.setup();
    }

    @Test
    public void onGameTickAllPersonsOutdoorsShouldBeNotifiedOfSunrise() {
        gameTestClient.everyoneGoesOut();
        gameTestClient.tickOnce();
        assertTrue("Everyone should be feeling warm after sunrise.", gameTestClient.outdoorsCharactersFeelWarm());
    }

    @Test
    public void onTwoGameTicksAllPersonsOutdoorsShouldBeNotifiedOfSunset() {
        gameTestClient.everyoneGoesOut();
        gameTestClient.tickTwice();
        assertTrue("Noone should be feeling warm after sunset.", gameTestClient.allFeelCold());
    }
}