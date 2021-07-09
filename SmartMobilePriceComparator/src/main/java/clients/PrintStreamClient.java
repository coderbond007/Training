package clients;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PrintStreamClient {
    private static final String PATH = "/Users/pradyumn.ag/IdeaProjects/SmartMobilePriceComparator/data/log/log[date].log";
    private static File file;
    private static PrintStream out = null;

    public static PrintStream getClient() throws FileNotFoundException {
        if (out == null) {
            synchronized (PrintStream.class) {
                if (out == null) {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("ddMMyyyyhh");
                    String date = simpleDateFormat.format(new Date());

                    file = new File(PATH.replace("[date]", date));
                    out = new PrintStream(file);
                }
            }
        }
        return out;
    }
}
