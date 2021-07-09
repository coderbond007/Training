package net.media.training.designpattern.observer;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by IntelliJ IDEA.
 * User: joelrosario
 * Date: Jul 21, 2011
 * Time: 9:16:07 AM
 * To change this template use File | Settings | File Templates.
 */
public class SunTests {
    @Test
    public void atSunriseIndoorsCharactersAreNotNotified() {
        SunTestClient sunTestClient = new SunTestClient();
        sunTestClient.aFewCharactersGoOutdoors();
        sunTestClient.sunRise();
        assertTrue("Only outdoors characters are notified on sunrise.", sunTestClient.outdoorsCharactersFeelWarm());

        sunTestClient = new SunTestClient();
        sunTestClient.aFewOtherCharactersGoOutdoors();
        sunTestClient.sunRise();
        assertTrue("Only outdoors characters are notified on sunrise.", sunTestClient.outdoorsCharactersFeelWarm());
    }

    @Test
    public void atSunsetOutdoorsCharactersOnlyAreNotified() {
        SunTestClient suntestClient = new SunTestClient();
        suntestClient.aFewCharactersGoOutdoors();
        suntestClient.sunRise();
        suntestClient.sunSet();
        assertTrue("Characters who were outdoors at sunset feel tired.", suntestClient.charactersWhoWereOutAreFeelTired());
    }
}
