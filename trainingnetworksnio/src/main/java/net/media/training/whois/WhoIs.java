package net.media.training.whois;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

class WhoIs {
    public static void main(String[] args) throws Exception {
        int c;
        Socket s = new Socket("whois.iana.org", 43); // whois.iana.org:43
        InputStream in = s.getInputStream();
        OutputStream out = s.getOutputStream();
        String str = "directi.com\n";
        byte[] buf = str.getBytes();
        out.write(buf);
        while ((c = in.read()) != -1) {
            System.out.print((char) c);
        }
        s.close();

        // have you heard of bulk whois servers ???
    }
}