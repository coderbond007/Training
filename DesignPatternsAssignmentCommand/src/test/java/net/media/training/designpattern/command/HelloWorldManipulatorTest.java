package net.media.training.designpattern.command;

import org.junit.After;
import org.junit.Test;

import java.io.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Created by IntelliJ IDEA.
 * User: goyalamit
 * Date: Jul 20, 2011
 * Time: 5:57:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class HelloWorldManipulatorTest {


    @Test
    public void testManipulate() throws IOException {
        HelloWorldManipulator x = new HelloWorldManipulator();
        x.fixManipulate("foo.txt", "bar.txt");

        BufferedReader reader = new BufferedReader(new FileReader(new File("bar.txt")));
        String aLine;
        String theContent = "";
        while ((aLine = reader.readLine()) != null) {
            theContent += aLine;
        }
        reader.close();
        assertEquals("new hello world", theContent);
        assertFalse(new File("foo.txt").exists());
    }

    @Test
    public void testBrokenManipulate() throws FileNotFoundException {
        HelloWorldManipulator x = new HelloWorldManipulator();
        x.brokenManipulate("foo.txt", "bar.txt");
        assertFalse(new File("foo.txt").exists());
        assertFalse(new File("bar.txt").exists());
    }

    @After
    public void after() {
        File file = new File("foo.txt");
        file.delete();
        file = new File("bar.txt");
        file.delete();
    }

}
