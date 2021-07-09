package FormattingParser;

import clients.DBClient;

import java.io.FileReader;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;


public class InsertionApple {
    private static final String INSERT_MOBILE_PROC = "CALL INSERT_MOBILE(?, ?, ?, ?, ?, ?, ?,?,?);";
    private static final String INSERT_MOBILE_Apple_PROC = "CALL INSERT_MOBILE_APPLE(?, ?, ?, ?, ?, ?, ?,?,?);";
    private static final int RAM_NOW_INDEX = 1;
    private static final int ACTUAL_MOBILE_NAME_NOW_INDEX = 2;
    private static final int SELLER_NAME_NOW_INDEX = 3;
    private static final int PRICE_NOW_INDEX = 4;
    private static final int MOBILE_LINK_NOW_INDEX = 5;
    private static final int IMAGE_URL_NOW_INDEX = 6;
    private static final int ROM_NOW_INDEX = 7;
    private static final int av_ind = 8;
    private static final int comp = 9;
    private static final String INSERT_Specs = "CALL specs_insert(?, ?, ?, ?);";
    private static final String[] VALS_KEYS = new String[]{"Camera", "Battery", "Screen"};


    public static void main(String[] args) throws IOException, SQLException {
//        FileReader reader = new FileReader("/Users/pradyumn.ag/IdeaProjects/SmartMobilePriceComparator/src/main/java/FormattingParser/Tatadata.txt");
//        Scanner scanner = new Scanner(reader);
//        while (scanner.hasNext()) {
//            String row = scanner.nextLine();
//            String[] words = row.split("\t");
//            String ram = words[0];
//            String name = words[1];
//            String img_link = words[2];
//            String rom = words[3];
//            String compr = words[4];
//            int price = Integer.parseInt(words[5]);
//            String url = words[6];
//            int status = Integer.parseInt(words[7]);
//            String seller_name = words[8];
//            try {
//                MobileCompleteDetails mb = new MobileCompleteDetails(ram, rom, name, img_link, "", price, url, status, seller_name);
//                mb.compressed = compr;
//                PreparedStatement preparedStatement;
//                if (mb.actual_mobile_name.contains("apple")) {
//                    preparedStatement = getPreparedStatementFromBeanApple(mb);
//                } else {
//                    preparedStatement = getPreparedStatementFromBean(mb);
//                }
//                preparedStatement.execute();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
        InsertSpecs();
//        test();
    }

//    public static void test() throws IOException {
//        FileReader fl = new FileReader("/Users/pradyumn.ag/IdeaProjects/SmartMobilePriceComparator/src/main/java/FormattingParser/MobilesDetails.txt");
//        BufferedReader bufferedReader = new BufferedReader(fl);
//        Set<String> set = new HashSet<>();
//        while (bufferedReader.ready()) {
//            String x = bufferedReader.readLine();
//            String[] parts = x.split("\t");
//            for (String part : parts) {
//                if (part.toLowerCase().contains("camera")) {
//                    set.add(part);
//                }
//            }
//        }
//        System.out.println(set);
//    }

    private static PreparedStatement getPreparedStatementFromBean(final MobileCompleteDetails phoneBean) throws
            SQLException, IOException {
        final long startTime = System.currentTimeMillis();
        PreparedStatement preparedStatement = DBClient.getPreparedStatement(INSERT_MOBILE_PROC);
        //final String mobileName = phoneBean.getGenericName();
        //   preparedStatement.setString(MOBILE_NAME_COMPRESSED_NOW_INDEX, mobileName.replace(SPACE_SYMBOL, EMPTY_STRING).trim());
        preparedStatement.setString(RAM_NOW_INDEX, phoneBean.ram);
        preparedStatement.setString(ACTUAL_MOBILE_NAME_NOW_INDEX, phoneBean.actual_mobile_name);
        preparedStatement.setString(SELLER_NAME_NOW_INDEX, phoneBean.seller_name);
        preparedStatement.setInt(PRICE_NOW_INDEX, phoneBean.price);
        preparedStatement.setString(MOBILE_LINK_NOW_INDEX, phoneBean.mobile_link);
        preparedStatement.setString(IMAGE_URL_NOW_INDEX, phoneBean.image_url);
        preparedStatement.setString(ROM_NOW_INDEX, phoneBean.rom);
        preparedStatement.setInt(av_ind, phoneBean.available_status);
        preparedStatement.setString(comp, phoneBean.compressed);
        final long endTime = System.currentTimeMillis();
        // logger.info("Time Taken for the getPreparedStatementFromBean : " + (endTime - startTime) + "ms");
        return preparedStatement;
    }

    static public PreparedStatement getPreparedStatementFromBeanApple(final MobileCompleteDetails phoneBean) throws
            SQLException, IOException {
        final long startTime = System.currentTimeMillis();
        PreparedStatement preparedStatement = DBClient.getPreparedStatement(INSERT_MOBILE_Apple_PROC);
        //final String mobileName = phoneBean.getGenericName();
        //   preparedStatement.setString(MOBILE_NAME_COMPRESSED_NOW_INDEX, mobileName.replace(SPACE_SYMBOL, EMPTY_STRING).trim());
        preparedStatement.setString(RAM_NOW_INDEX, phoneBean.ram);
        preparedStatement.setString(ACTUAL_MOBILE_NAME_NOW_INDEX, phoneBean.actual_mobile_name);
        preparedStatement.setString(SELLER_NAME_NOW_INDEX, phoneBean.seller_name);
        preparedStatement.setInt(PRICE_NOW_INDEX, phoneBean.price);
        preparedStatement.setString(MOBILE_LINK_NOW_INDEX, phoneBean.mobile_link);
        preparedStatement.setString(IMAGE_URL_NOW_INDEX, phoneBean.image_url);
        preparedStatement.setString(ROM_NOW_INDEX, phoneBean.rom);
        preparedStatement.setInt(av_ind, phoneBean.available_status);
        preparedStatement.setString(comp, phoneBean.compressed);

        final long endTime = System.currentTimeMillis();
        System.err.println("Time Taken for the getPreparedStatementFromBean : " + (endTime - startTime) + "ms");
        return preparedStatement;
    }

    private static int nam = 1, sc = 2, cam = 3, bat = 4;

    static void InsertSpecs() throws IOException, SQLException {
        FileReader fl = new FileReader("/Users/pradyumn.ag/IdeaProjects/SmartMobilePriceComparator/src/main/java/FormattingParser/MobilesDetails.txt");
        Scanner scanner = new Scanner(fl);
        int counter = 0;
        while (scanner.hasNext()) {
//            String x = scanner.nextLine().trim();
//            String x = "samsung guru music 2\t 2.0\" Screen\t 800mAh Battery\t ";
//            try {
//                String[] spe = x.split("\t");
//                PreparedStatement preparedStatement = DBClient.getPreparedStatement(INSERT_Specs);
//                preparedStatement.setString(1, spe[0]);
//                String xx = spe[1] + " " + spe[2] + spe[3];
//                String[] key = xx.split("\t");
//                HashMap<String, String> hm = new HashMap<>();
//                for (int i = 0; i < key.length - 1; i++) {
//                    hm.put(key[i + 1].toLowerCase(), key[i].toLowerCase());
//                }
//
//                for (int i = 1; i < spe.length; ++i) {
//                    for (String keyNow : VALS_KEYS) {
//                        if (spe[i].contains(keyNow)) {
//                            String rem = spe[i].replace(keyNow, "");
//                            hm.put(keyNow.trim(), rem.trim());
//                        }
//                    }
//                }
//                System.out.println(hm);
//                preparedStatement.setString(nam, spe[0].trim());
//                preparedStatement.setString(cam, hm.get("camera"));
//                preparedStatement.setString(bat, hm.get("battery"));
//                preparedStatement.setString(sc, hm.get("screen"));
//                preparedStatement.execute();
//            } catch (Exception e) {
//                System.err.println("Failed for : " + x);
//                e.printStackTrace();
//            }
//            ++counter;
//            if (counter == 1) break;
            String x = scanner.nextLine().trim().toLowerCase();
            try {
                String spe[] = x.split("\t");

                PreparedStatement preparedStatement = DBClient.getPreparedStatement(INSERT_Specs);
                preparedStatement.setString(1, spe[0]);
                ArrayList<String> list = new ArrayList<>();

                for (int i = 1; i < spe.length; i++) {
                    list.add(spe[i]);
                }
                String camm = "camera";
                String battery = "battery";
                String screen = "screen";
                HashMap<String, String> hm = new HashMap<>();
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).contains("camera")) {
                        String cam = list.get(i).replace("camera", " ");
                        hm.put(camm, cam);
                    }
                    if (list.get(i).contains("battery")) {
                        String cam = list.get(i).replace("battery", " ");
                        hm.put(battery, cam);
                    }
                    if (list.get(i).contains(screen)) {
                        String cam = list.get(i).replace("screen", " ");
                        hm.put(screen, cam);
                    }
                }
                preparedStatement.setString(nam, spe[0].trim());
                preparedStatement.setString(cam, hm.get("camera"));
                preparedStatement.setString(bat, hm.get("battery"));
                preparedStatement.setString(sc, hm.get("screen"));
                preparedStatement.execute();
            } catch (Exception e) {
                System.err.println("Failed for x : " + x);
                e.printStackTrace();
            }
        }
    }
}