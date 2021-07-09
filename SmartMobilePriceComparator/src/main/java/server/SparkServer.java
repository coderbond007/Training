package server;

import bean.MobileDetailsResult;
import bean.MobileResponseBean;
import clients.DBClient;
import clients.GsonClient;
import clients.PrintStreamClient;
import search.FastSearchFuzzySearch;
import search.FetchMobileWithID;
import search.FetchMobiles;
import spark.Response;

import java.io.FileNotFoundException;
import java.net.Inet4Address;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static search.FetchMobiles.FETCH_MOBILE_DETAILS_PROC_CALL;
import static search.FetchMobiles.getResponseBean;
import static spark.Spark.get;
import static spark.Spark.port;

public class SparkServer {
    private static final HashMap<String, String> corsHeaders = new HashMap<String, String>();
    private static final int SUCCESS_RESPONSE_CODE = 200;
    private static final int NOT_FOUND_RESPONSE_CODE = 404;
    private static final String APPLICATION_JSON = "application/json";

    private static final String ALL_MOBILES_KEY = "allMobiles";

    static {
        corsHeaders.put("Access-Control-Allow-Methods", "GET,PUT,POST,DELETE,OPTIONS");
        corsHeaders.put("Access-Control-Allow-Origin", "*");
        corsHeaders.put("Access-Control-Allow-Headers", "Content-Type,Authorization,X-Requested-With,Content-Length,Accept,Origin,");
        corsHeaders.put("Access-Control-Allow-Credentials", "true");
    }

    public static void main(String[] args) throws Exception {
        System.out.println(Inet4Address.getLoopbackAddress());
        System.out.println(Inet4Address.getLocalHost());
        fetchHardCodedIdsData();
        BuildEnvironment.build();
//        BuildEnvironment.runCRONS();
        port(4567);
        get("/fetchData/:q", (request, response) -> {
            try {
                setHeaders(response);
                String searchKeyword = request.params(":q");
//                List<MobileResponseBean> mobileResponseBeans = new ArrayList<>();
//                mobileResponseBeans.add(MobileResponseBean.getSample());
//                mobileResponseBeans.add(MobileResponseBean.getSample());
//                mobileResponseBeans.add(MobileResponseBean.getSample());
//                Map<String, List<MobileResponseBean>> map = new HashMap<>();
//                map.put("allMobiles", mobileResponseBeans);
//                String result = GsonClient.getClient().toJson(map);

                List<MobileResponseBean> responseBeans = FetchMobiles.fetchAllMobiles_Version2(searchKeyword);
                Map<String, List<MobileResponseBean>> map = new HashMap<>();
                map.put(ALL_MOBILES_KEY, responseBeans);
                String result = GsonClient.getClient().toJson(map);
                response.body(result);
                response.type(APPLICATION_JSON);
                response.status(SUCCESS_RESPONSE_CODE);
            } catch (Exception e) {
                response.status(NOT_FOUND_RESPONSE_CODE);
                try {
                    e.printStackTrace(PrintStreamClient.getClient());
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
            return response.body();
        });
        get("/fetchMobile/:id", (request, response) -> {
            try {
                setHeaders(response);
                long id = Long.parseLong(request.params(":id"));
                MobileDetailsResult mobileDetailsResult = FetchMobileWithID.fetchAllMobiles(id);
                // System.out.println(mobileDetailsResult.mobile_Name);
                String result = GsonClient.getClient().toJson(mobileDetailsResult);
                response.body(result);
                response.type(APPLICATION_JSON);
                response.status(SUCCESS_RESPONSE_CODE);
            } catch (Exception e) {
                response.status(NOT_FOUND_RESPONSE_CODE);
            }
            return response.body();
        });

        get("/fetchExtensionData/:q", (request, response) -> {
            try {
                final long startTime = System.currentTimeMillis();
                setHeaders(response);
                String query = request.params(":q");
//                System.out.println("{{{{" + query +"}}}}");
                List<Long> ids = FastSearchFuzzySearch.getResultForExtension(query);
                MobileDetailsResult mobileDetailsResult = FetchMobileWithID.fetchDetailsForExtension(ids);
                String result = GsonClient.getClient().toJson(mobileDetailsResult);
                response.body(result);
                System.out.println(result);
                response.type(APPLICATION_JSON);
                response.status(SUCCESS_RESPONSE_CODE);
                final long endTime = System.currentTimeMillis();
                System.out.println("time taken : " + (endTime - startTime) + "ms");
            } catch (Exception e) {
                response.status(NOT_FOUND_RESPONSE_CODE);
            }
            return response.body();
        });


        get("/trendingPhones", (request, response) -> {
            try {
                setHeaders(response);
//                List<MobileDetails> mobileDetails = TrendingCron.getTrendingPhonesCaches();
//                List<MobileResponseBean> mobileResponseBeans = new ArrayList<>();
//                mobileResponseBeans.add(MobileResponseBean.getSample());
//                mobileResponseBeans.add(MobileResponseBean.getSample());
//                mobileResponseBeans.add(MobileResponseBean.getSample());
                Map<String, List<MobileResponseBean>> map = new HashMap<>();
                List<MobileResponseBean> hardCodedBeans = fetchHardCodedIdsData();
                map.put("allMobiles", hardCodedBeans);
                String result = GsonClient.getClient().toJson(map);

                response.body(result);
                response.type(APPLICATION_JSON);
                response.status(SUCCESS_RESPONSE_CODE);
            } catch (Exception e) {
                response.status(NOT_FOUND_RESPONSE_CODE);
            }
            return response.body();
        });
    }

    public static void setHeaders(Response response) {
        for (String key : corsHeaders.keySet()) {
            response.header(key, corsHeaders.get(key));
        }
    }

    private static List<MobileResponseBean> hardCodedBeans = null;

    public static List<MobileResponseBean> fetchHardCodedIdsData() throws Exception {
        if (hardCodedBeans != null)
            return hardCodedBeans;

        List<String> titles = new ArrayList<>();
        titles.add("Xiaomi Mi A3 (Not Just Blue, 4GB RAM, 64GB Storage)");
        titles.add("Redmi Y2 (Gold, 4GB RAM, 64GB Storage)");
        titles.add("Xiaomi Mi A3 (More Than White, 6GB RAM, 128GB Storage)");
        titles.add("OnePlus 7 (Mirror Blue, 6GB RAM, 128GB Storage)");
        titles.add("Samsung Galaxy M10 (Ocean Blue, 3+32GB)");
        titles.add("Xiaomi Mi A3 (Kind of Grey, 4GB RAM, 64GB Storage)");
        titles.add("OnePlus 7 (Mirror Grey, 6GB RAM, 128GB Storage)");
        titles.add("Samsung Galaxy M20 (Ocean Blue, 3+32GB)");
        titles.add("Samsung Galaxy M30 (Gradation Blue, 4+64 GB)");
        titles.add("Nokia 6.1 Plus (Black, 6GB RAM, 64GB Storage)");
        titles.add("Samsung Galaxy M10 (Charcoal Black, 3+32GB)");

        List<Long> ids = new ArrayList<>();
        for (String currentTitle : titles) {
            List<Long> res = FastSearchFuzzySearch.getResultForExtension(currentTitle);
            if (res.size() > 0) {
                ids.add(res.get(0));
            }
        }

        hardCodedBeans = new ArrayList<>();
        for (final Long currentID : ids) {
            PreparedStatement preparedStatement = DBClient.getPreparedStatement(FETCH_MOBILE_DETAILS_PROC_CALL);
            preparedStatement.setLong(1, currentID);
            MobileResponseBean mobileResponseBean = getResponseBean(preparedStatement);
            hardCodedBeans.add(mobileResponseBean);
        }
        return hardCodedBeans;
    }
}