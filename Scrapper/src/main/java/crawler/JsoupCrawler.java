package crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class JsoupCrawler {
    public static void main(String[] args) throws IOException {
        Document document = Jsoup.connect("https://www.flipkart.com/realme-3-dynamic-black-64-gb/p/itmfe68wrbfnzqwz?pid=MOBFFVFDJCCUZDGT").get();
        System.out.println(document.outerHtml());
    }
}