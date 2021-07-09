package sample;

import clients.CrawlClient;

public class Now {
    public static void main(String[] args) throws Exception {
        String pageSource = CrawlClient.getCrawlClient().crawl("https://paytmmall.com/realme-3-pro-6-gb-64-gb-purple-CMPLXMOBREALME-3-PRODUMM202569F2740EE-pdp").getContent();
        System.out.println(pageSource);
    }
}
