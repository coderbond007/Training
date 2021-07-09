package net.media.training.designpattern.observer;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by IntelliJ IDEA.
 * User: joelrosario
 * Date: Jul 20, 2011
 * Time: 9:53:39 PM
 * To change this template use File | Settings | File Templates.
 */
public class CharacterTests {
    @Test
    public void sanityChecks() {
        CharacterTestClient client = new CharacterTestClient();
        assertTrue(client.testOutdoors());
        assertTrue(client.testSunRose());
        assertTrue(client.testSunSet());
        assertTrue(client.testIndoors());
    }
}