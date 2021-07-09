package net.media.training.live.dip;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Base64;

public class EncodeNetworkBased implements EncodingInterface {
    private String encodedString = null;


    @Override
    public void encode() {
        String inputString = getInputString();
        encodedString = Base64.getEncoder().encodeToString(inputString.getBytes());
    }

    private String getInputString() {
        URL url = getURLConnection();
        InputStream in = getURLInputStream(url);
        return readStreamToString(in);
    }

    private InputStream getURLInputStream(URL url) {
        InputStream inputStream = null;
        try {
            inputStream = url.openStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return inputStream;
    }

    private URL getURLConnection() {
        URL url = null;
        try {
            url = new URL("http", "myfirstappwith.appspot.com", "index.html");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public String readStreamToString(InputStream inputStream) {
        InputStreamReader reader = new InputStreamReader(inputStream);
        StringBuilder inputString = new StringBuilder();
        try {
            int c;
            c = reader.read();
            while (c != -1) {
                inputString.append((char) c);
                c = reader.read();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return inputString.toString();
    }

    public void writeInDatabase() {
        if (encodedString == null)
            throw new IllegalStateException();
        MyDatabase database = new MyDatabase();
        database.write(encodedString);
    }
}
