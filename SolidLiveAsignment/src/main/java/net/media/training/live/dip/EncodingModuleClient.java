package net.media.training.live.dip;

import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: goyalamit
 * Date: Jul 13, 2011
 * Time: 10:20:57 AM
 * To change this template use File | Settings | File Templates.
 */
public class EncodingModuleClient {
    public static void main(String[] args) throws IOException {
        EncodingInterface encodeFileBased = new EncodeFileBased();
        encodeFileBased.encode();

        EncodingInterface encodeNetworkBased = new EncodeNetworkBased();
        encodeNetworkBased.encode();
    }
}
