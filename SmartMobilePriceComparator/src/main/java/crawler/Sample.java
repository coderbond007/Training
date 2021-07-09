package crawler;

import bean.MobilePhoneBean;
import clients.CrawlClient;
import parsers.AmazonParserModified;

public class Sample {
    public static void main(String[] args) throws Exception {
//        PrintWriter out = new PrintWriter(new FileOutputStream(new File(PATH + "/data/output.html")));
//        GoogleSearchQueryData queryData = new GoogleSearchQueryData("amazon.in", new String[]{"samsung", "m20"});
//        List<String> res = GoogleCrawler.fetchSearchResults(queryData);
//        if (res == null) throw new RuntimeException();
//        for (String r : res) {
//            System.out.println(r);
//        }
//        out.close();
//        WebDriver webDriver = SeleniumCrawlClient.getWebDriverClient();
//        for (Map.Entry<Object, Object> entry : System.getProperties().entrySet()) {
//            System.out.println(entry.getKey() + " :: " + entry.getValue());
//        }
        String pageSource = CrawlClient.getCrawlClient().crawl("https://www.amazon.in/Panasonic-Eluga-Ray-530-Black/dp/B07HM8BB23").getContent();
        MobilePhoneBean mobilePhoneBean = new AmazonParserModified().parseData(pageSource);
        System.out.println(mobilePhoneBean.getInternalStorage());
    }
}