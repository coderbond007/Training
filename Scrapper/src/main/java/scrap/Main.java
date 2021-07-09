package scrap;

import com.google.gson.Gson;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.SplittableRandom;

public class Main {
    public static final String PATH = System.getProperty("user.dir") + "/data/";
    public static final String LINK_TO_WEBSITE = "https://www.flipkart.com/realme-3-dynamic-black-32-gb/p/itmfe68wrbfnzqwz?pid=MOBFE68WZM7UFMDA&srno=b_1_1&otracker=clp_omu_Mobile%2BNew%2BLaunches_1_5.dealCard.OMU_mobile-phones-store_mobile-phones-store_X95WZDUDIR3U_5&otracker1=clp_omu_PINNED_neo%2Fmerchandising_Mobile%2BNew%2BLaunches_NA_dealCard_cc_1_NA_view-all_5&lid=LSTMOBFE68WZM7UFMDAHFVR99&fm=neo%2Fmerchandising&iid=2f4a5aa3-fb10-4c27-8d13-030eefd97d53.MOBFE68WZM7UFMDA.SEARCH&ppt=browse&ppn=browse&ssid=x4vfq3kyjk0000001565688031767";
    public static final SplittableRandom gen = new SplittableRandom(System.currentTimeMillis());

    public static void main(String[] args) throws IOException, InterruptedException {
        PrintWriter out = new PrintWriter(new FileOutputStream(new File(PATH + "output.html"), false));
        Document document = Jsoup
                .connect(LINK_TO_WEBSITE)
//                .userAgent("Mozilla/5.0 (Linux; Android 7.0; \\ SM-A520F Build/NRD90M; wv) AppleWebKit/537.36 \\ (KHTML, like Gecko) Version/4.0 \\ Chrome/{$IP} Mobile Safari/537.36".replace("{$IP}", getRandomIP()))
                .get();
        out.println(document);
        Elements elements = document.select("class._3Rrcbo");
        for (Element element : elements) {
            System.out.println(element.html());
            out.println(element.html());
        }
    }

    public static String getRandomIP() {
        return gen.nextInt(256) + "." + gen.nextInt(256) + "." + gen.nextInt(256) + "." + gen.nextInt(256);
    }
}