package net.media.training.live.dip;

import java.util.Hashtable;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: goyalamit
 * Date: Jul 19, 2011
 * Time: 4:47:27 PM
 * To change this template use File | Settings | File Templates.
 */
public class MyDatabase {
    private static Map<Integer, String> data = new Hashtable<Integer, String>();
    private static int count = 0;

    public int write(String inputString) {
        count = count + 1;
        data.put(count, inputString);
        return count;
    }
}
