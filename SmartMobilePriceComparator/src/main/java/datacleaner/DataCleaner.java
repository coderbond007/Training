//package datacleaner;
//
//import clients.DBClient;
//
//import java.io.FileNotFoundException;
//import java.io.FileReader;
//import java.io.IOException;
//import java.sql.PreparedStatement;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.Scanner;
//
//public class DataCleaner {
//    private static final String INSERT_MOBILE_PROC = "CALL INSERT_MOBILE(?, ?, ?, ?, ?, ?, ?,?);";
//    private static final String INSERT_MOBILE_Apple_PROC = "CALL INSERT_MOBILE_APPLE(?, ?, ?, ?, ?, ?, ?,?);";
//    private static final int RAM_NOW_INDEX = 1;
//    private static final int ACTUAL_MOBILE_NAME_NOW_INDEX = 2;
//    private static final int SELLER_NAME_NOW_INDEX = 3;
//    private static final int PRICE_NOW_INDEX = 4;
//    private static final int MOBILE_LINK_NOW_INDEX = 5;
//    private static final int IMAGE_URL_NOW_INDEX = 6;
//    private static final int ROM_NOW_INDEX = 7;
//    private static final int av_ind = 8;
//    private static final String INSERT_Specs = "CALL specs_insert(?, ?, ?, ?);";
//    private static int nam = 1, sc = 2, cam = 3, bat = 4;
//    /*  IN RAM_NOW varchar(30), IN ACTUAL_MOBILE_NAME_NOW varchar(500),
//      IN SELLER_NAME_NOW varchar(400), IN PRICE_NOW bigint,
//      IN MOBILE_LINK_NOW longtext, IN IMAGE_URL_NOW varchar(1000),
//      IN rom_now varchar(50))*/
//    static String colors[] = new String[]{"gold, blue\n" +
//            "white;silver\n" +
//            "black;silver\n" +
//            "black/white/grey\n" +
//            "blue & black / grey & black\n" +
//            "space grey/silver/gold/rose gold\n" +
//            "white-silver\n" +
//            "blue / silver / gold / red\n" +
//            "white & orange\n" +
//            "black & champagne\n" +
//            "gold/ silver/ space grey\n" +
//            "blue/lake blue\n" +
//            "white+gold/grey+silver\n" +
//            "black & blue\n" +
//            "titanium-grey\n" +
//            "black;gold\n" +
//            "black & gold\n" +
//            "black-champagne\n" +
//            "glossy black/white/glacier blue/red\n" +
//            "white-gold\n" +
//            "blue & black\n" +
//            "-\n" +
//            "black;blue\n" +
//            "moonlight white / shimmer gold / sapphire black\n" +
//            "silver/dark grey/gold\n" +
//            "white, black, copper, aqua green\n" +
//            "white-dark grey\n" +
//            "red, black\n" +
//            "silver/rose gold/gold/space grey\n" +
//            "champagne-gold\n" +
//            "black, white, golden\n" +
//            "white/black\n" +
//            "silver, black\n" +
//            "white + champagne\n" +
//            "black / white\n" +
//            "champagne,grey\n" +
//            "white;black\n" +
//            "black / gold / silver\n" +
//            "white/black+silver\n" +
//            "milky-way grey\n" +
//            "gold/black/silver\n" +
//            "titanium grey / glacier silver / sand gold\n" +
//            "gold/black\n" +
//            "white/rainbow white\n" +
//            "white & champagne\n" +
//            "champagne / champ\n" +
//            "black;champagne\n" +
//            "black+silver/white\n" +
//            "black&yellow\n" +
//            "black&red\n" +
//            "blue/gold\n" +
//            "blue;silver\n" +
//            "black-sandstone\n" +
//            "gold/royal gold\n" +
//            "black/champagne\n" +
//            "blue & champagne\n" +
//            "black/white/gold\n" +
//            "coffee & blue\n" +
//            "black-red\n" +
//            "grey / silver\n" +
//            "black;brown\n" +
//            "champagne/grey\n" +
//            "black/white/blue/red\n" +
//            "black/silver/red/gold\n" +
//            "white, grey\n" +
//            "white-grey\n" +
//            "black & grey\n" +
//            "black;grey\n" +
//            "white, champange\n" +
//            "grey/space grey\n" +
//            "black/white\n" +
//            "gold/silver\n" +
//            "blue-copper\n" +
//            "white/silver/yellow/red/gold/silver blue\n" +
//            "blue-silver\n" +
//            "black/tuxedo black\n" +
//            "white/grey\n" +
//            "black & red\n" +
//            "champagne & black\n" +
//            "white & golden\n" +
//            "black-green\n" +
//            "black/silver/white/gold\n" +
//            "black & silver\n" +
//            "black sandstone/black gold\n" +
//            "grey & silver\n" +
//            "black/red\n" +
//            "white/silver/blue/red/pink/gold\n" +
//            "sheer gold / chic pink / pure white / aqua blue / glacier grey / silver\n" +
//            "gold, black\n" +
//            "black & white\n" +
//            "grey / silver / gold\n" +
//            "red & black\n" +
//            "bluem/blue\n" +
//            "silver & grey\n" +
//            "black/silver/gold/jet black/rose gold\n" +
//            "white & blue\n" +
//            "multi-coloured\n" +
//            "pink /black/gold\n" +
//            "white & gold\n" +
//            "black-gold\n" +
//            "white+silver\n" +
//            "black & green\n" +
//            "meteor gray, white luxury, roast chestnut\n" +
//            "black-blue\n" +
//            "champagne-white"};
//
//    public static void main(String[] args) throws IOException, SQLException {
//        System.out.println(clearName("Samsung galaxy m20"));
//        try {
//            FileReader fileReader = new FileReader("/Users/ankur.y/Downloads/SmartMobilePriceComparator/data/completeDataFromPrad.txt");
//            Scanner scanner = new Scanner(fileReader);
//            HashMap<String, Integer> hm = new HashMap<>();
//            ArrayList<String>[] specs = new ArrayList[2000];
//            for (int i = 0; i < 2000; i++) {
//                specs[i] = new ArrayList<>();
//            }
//            while (scanner.hasNext()) {
//                String x = scanner.next().toLowerCase();
//                if (!hm.containsKey(x))
//                    hm.put(x, 1);
//                else
//                    hm.put(x, hm.get(x) + 1);
//            }
//            HashSet<String> lnames = new HashSet<>();
//            ArrayList<String> list = new ArrayList<>();
//            int cnt = 0;
//            for (String x : hm.keySet()) {
//                if (x.contains("+")) {
//                    list.add(x);
//                }
//            }
//            if (hm.containsKey("edition"))
//                hm.remove("edition");
//            for (String x1 : list) {
//                hm.remove(x1);
//            }
//
//            hm.remove("ram");
//            for (int i = 0; i <= 512; i++) {
//                if (hm.containsKey(i + "gb")) {
//                    hm.remove(i + "gb");
//                    hm.remove(i + " " + "mb");
//                }
//            }
//            if (hm.containsKey("gb"))
//                hm.remove("gb");
//            for (String col : colors) {
//                col = col.replace("-", " ");
//                col = col.replace("+", " ");
//                col = col.replace("/", " ");
//                col = col.replace("&", " ");
//                col = col.replace(";", " ");
//                col = col.replace(",", " ");
//                String coll[] = col.split(" ");
//                for (String xxx : coll) {
//                    xxx = xxx.toLowerCase();
//                    if (hm.containsKey(xxx)) ;
//                    hm.remove(xxx);
//                }
//            }
//            for (int i = 2000; i < 2020; i++) {
//                if (hm.containsKey("" + i)) {
//                    hm.remove("" + i);
//                }
//            }
//            FileReader fileReaderdata = new FileReader("/Users/ankur.y/Downloads/SmartMobilePriceComparator/.idea/dataSources/allData.txt");
//            Scanner scanner1 = new Scanner(fileReaderdata);
//            ArrayList<MobileCompleteDetails> clearedDetails = new ArrayList<>();
//            ArrayList<MobileCompleteDetails> dataMobiles = new ArrayList<>();
//            while (scanner1.hasNext()) {
//                String row = scanner1.nextLine();
//                String arrr[] = row.split("\t");
//                String ram = arrr[0];
//                String actual_mobile_name = arrr[1];
//                String image_url = arrr[2];
//                String brand = arrr[3];
//                int price = Integer.parseInt(arrr[4]);
//                String mobile_link = arrr[5];
//                int available_status = Integer.parseInt(arrr[6]);
//                String seller_name = (arrr[7]);
//                dataMobiles.add(new MobileCompleteDetails(ram, actual_mobile_name, image_url, brand, price, mobile_link, available_status, seller_name));
//            }
//            for (MobileCompleteDetails mobileCompleteDetails : dataMobiles) {
//                String actual_name = mobileCompleteDetails.actual_mobile_name.toLowerCase().trim();
//                String ram = mobileCompleteDetails.ram.toLowerCase();
//                String brand = mobileCompleteDetails.brand.toLowerCase().trim();
//                if (brand != null)
//                    actual_name = actual_name + " " + brand;
//                {
//                    int i = actual_name.indexOf("gb");
//                    if (i > 0) {
//                        if (actual_name.charAt(i - 1) == ' ') {
//                            System.out.println(i);
//                            actual_name = actual_name.substring(0, i - 1) + "" + actual_name.substring(i);
//                        }
//                    }
//                    i = actual_name.indexOf("gb");
//                    if (i > 0) {
//                        if (actual_name.charAt(i - 1) == ' ') {
//                            System.out.println(i);
//                            actual_name = actual_name.substring(0, i - 1) + "" + actual_name.substring(i);
//                        }
//                    }
//                }
//                int index11 = actual_name.indexOf("(");
//                if (index11 >= 0)
//                    actual_name = actual_name.substring(0, index11);
//                index11 = actual_name.indexOf("gb");
//                if (index11 >= 0) {
//                    actual_name = actual_name.substring(0, index11 + 2);
//                }
//                actual_name = actual_name.replace(")", " ");
//                actual_name = actual_name.replace("(", " ");
//                actual_name = actual_name.replace(",", " ");
//                actual_name = actual_name.replace("-", " ");
//                actual_name = actual_name.trim();
//                String ar1[] = actual_name.split(" ");
//                HashSet<String> set = new HashSet<>();
//                ArrayList<String> list1 = new ArrayList<>();
//                HashMap<String, Integer> keyw = new HashMap();
//                for (int i = 0; i < ar1.length; i++) {
//                    if (hm.containsKey(ar1[i].toLowerCase()) && hm.get(ar1[i].toLowerCase()) >= 0) {
//                        if (ar1[i].equals("mi") || ar1[i].equals("xiaomi")) {
//                            ar1[i] = "redmi";
//                        }
//                        if (i != 0) {
//                            if (ar1[i].equals(ar1[0]))
//                                continue;
//                        }
//                        if (keyw.containsKey(ar1[i]))
//                            continue;
//                        // keyw.put(ar1[i],1);
//                        list1.add(ar1[i].toLowerCase());
//                    }
//                }
//                String name1 = "";
//                for (String xx : list1) {
//                    name1 = name1 + " " + xx;
//                }
//
//                String ra = ram;
//                int pos = 0;
//                while (true) {
//                    if (ra.charAt(pos) == '+') {
//                        break;
//                    }
//                    pos++;
//                }
//                String ramm = ra.substring(0, pos);
//                String rom = ra.substring(pos + 1);
//                if (ramm.equals(""))
//                    ramm = "0";
//                if (rom.equals(""))
//                    rom = "0";
//                name1 = name1.trim().toLowerCase();
//                HashSet<String> compressed = new HashSet<>();
//                String par[] = name1.split(" ");
//                for (int i = 0; i < par.length; i++) {
//                    par[i] = par[i].trim();
//                    if (par[i].length() > 0) {
//                        compressed.add(par[i]);
//                    }
//                }
//                if (name1.contains("moto ") || name1.contains("motorola")) {
//                    compressed.add("moto");
//                    compressed.add("motorola");
//                }
//                ArrayList<String> comp = new ArrayList<>();
//                for (String x : compressed) {
//                    comp.add(x);
//                }
//                Collections.sort(comp);
//                String compres = "";
//                for (String x : comp) {
//                    compres = compres + x;
//                }
//                if (name1.contains("apple")) {
//                    if (name1.length() > 0 && name1.split(" ").length > 0) {
//                        lnames.add(name1);
//                        System.out.println(mobileCompleteDetails.actual_mobile_name.toLowerCase() + " " + name1);
//                        clearedDetails.add(new MobileCompleteDetails(ramm, rom, name1.trim().toLowerCase(), mobileCompleteDetails.image_url, mobileCompleteDetails.brand, mobileCompleteDetails.price, mobileCompleteDetails.mobile_link, mobileCompleteDetails.available_status, mobileCompleteDetails.seller_name));
//                        continue;
//                    }
//                }
//                if (ramm.equals("0")) {
//                    System.out.println("zero ram" + " " + mobileCompleteDetails.mobile_link + " " + mobileCompleteDetails.actual_mobile_name);
//                    continue;
//                }
//                if (rom.equals("0")) {
//                    System.out.println("zero rom" + " " + mobileCompleteDetails.mobile_link + " " + mobileCompleteDetails.actual_mobile_name);
//                    continue;
//                }
//
//
//                if (ramm.length() >= 3) {
//
//                } else {
//                    if (name1.length() > 0 && name1.split(" ").length > 0) {
//                        lnames.add(name1);
//                        System.out.println(mobileCompleteDetails.actual_mobile_name.toLowerCase() + " " + name1);
//                        clearedDetails.add(new MobileCompleteDetails(ramm, rom, name1.trim(), mobileCompleteDetails.image_url, mobileCompleteDetails.brand, mobileCompleteDetails.price, mobileCompleteDetails.mobile_link, mobileCompleteDetails.available_status, mobileCompleteDetails.seller_name));
//                    }
//                }
//            }
//            System.out.println(lnames.size());
//            int cn = 0;
//            for (int j = 0; j < clearedDetails.size(); j++) {
//                MobileCompleteDetails de = clearedDetails.get(j);
//                cn++;
//                if (de.mobile_link.toLowerCase().contains("refurbished"))
//                    continue;
//                if (de.mobile_link.toLowerCase().contains("renewed"))
//                    continue;
//                if (de.actual_mobile_name.equals("apple")) {
//                    PreparedStatement preparedStatement = getPreparedStatementFromBeanApple(de);
//                    boolean status = preparedStatement.execute();
//                } else {
//                    PreparedStatement preparedStatement = getPreparedStatementFromBean(de);
//                    boolean status = preparedStatement.execute();
//                }
//            }
//        } catch (Exception e) {
//
//
//        }
//    }
//
//    static void InsertSpecs() throws IOException, SQLException {
//        FileReader fl = new FileReader("/Users/ankur.y/Downloads/SmartMobilePriceComparator/data/alldata/Realme X/amazon.in/MobilesDetails.txt");
//        Scanner scanner = new Scanner(fl);
//        while (scanner.hasNext()) {
//            try {
//                String x = scanner.nextLine();
//                String spe[] = x.split("\t");
//
//                PreparedStatement preparedStatement = DBClient.getPreparedStatement(INSERT_Specs);
//                preparedStatement.setString(1, spe[0]);
//                String xx = spe[1] + " " + spe[2] + spe[3];
//                String key[] = xx.split(" ");
//                HashMap<String, String> hm = new HashMap<>();
//                for (int i = 0; i < key.length - 1; i++) {
//                    hm.put(key[i + 1].toLowerCase(), key[i].toLowerCase());
//                }
//                preparedStatement.setString(nam, spe[0].trim());
//                preparedStatement.setString(cam, hm.get("camera"));
//                preparedStatement.setString(bat, hm.get("battery"));
//                preparedStatement.setString(sc, hm.get("screen"));
//                preparedStatement.execute();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//        }
//    }
//
//    static public String clearName(String name) throws FileNotFoundException {
//        FileReader fileReader = new FileReader("/Users/ankur.y/Downloads/samplespark/data/mobilenames.txt");
//        Scanner scanner = new Scanner(fileReader);
//        HashMap<String, Integer> hm = new HashMap<>();
//        hm.put("5t", 1);
//        while (scanner.hasNext()) {
//            String x = scanner.next().toLowerCase();
//            if (!hm.containsKey(x))
//                hm.put(x, 1);
//            else
//                hm.put(x, hm.get(x) + 1);
//
//        }
//        hm.remove("ram");
//        for (int i = 0; i <= 512; i++) {
//            if (hm.containsKey(i + "gb")) {
//                hm.remove(i + "gb");
//                hm.remove(i + " " + "mb");
//            }
//        }
//        if (hm.containsKey("gb"))
//            hm.remove("gb");
//        for (String col : colors) {
//            col = col.replace("-", " ");
//            col = col.replace("+", " ");
//            col = col.replace("/", " ");
//            col = col.replace("&", " ");
//            col = col.replace(";", " ");
//            col = col.replace(",", " ");
//            String coll[] = col.split(" ");
//            for (String xxx : coll) {
//                xxx = xxx.toLowerCase();
//                if (hm.containsKey(xxx)) ;
//                hm.remove(xxx);
//            }
//        }
//
//        String actual_name = name.toLowerCase();
//        {
//            int i = actual_name.indexOf("gb");
//            if (i > 0) {
//
//                if (actual_name.charAt(i - 1) == ' ') {
//                    System.out.println(i);
//                    actual_name = actual_name.substring(0, i - 1) + "" + actual_name.substring(i);
//                }
//            }
//            i = actual_name.indexOf("gb");
//            if (i > 0) {
//                if (actual_name.charAt(i - 1) == ' ') {
//                    System.out.println(i);
//                    actual_name = actual_name.substring(0, i - 1) + "" + actual_name.substring(i);
//                }
//            }
//        }
//        int index11 = actual_name.indexOf("(");
//        if (index11 >= 0)
//            actual_name = actual_name.substring(0, index11);
//        index11 = actual_name.indexOf("gb");
//        if (index11 >= 0) {
//            actual_name = actual_name.substring(0, index11 + 2);
//        }
//        actual_name = actual_name.replace(")", " ");
//        actual_name = actual_name.replace("(", " ");
//        actual_name = actual_name.replace(",", " ");
//        actual_name = actual_name.replace("-", " ");
//        actual_name = actual_name.trim();
//        String ar1[] = actual_name.split(" ");
//        HashSet<String> set = new HashSet<>();
//        ArrayList<String> list1 = new ArrayList<>();
//        HashMap<String, Integer> keyw = new HashMap();
//        for (int i = 0; i < ar1.length; i++) {
//            if (hm.containsKey(ar1[i].toLowerCase()) && hm.get(ar1[i].toLowerCase()) >= 1) {
//                if (ar1[i].equals("mi") || ar1[i].equals("xiaomi")) {
//                    ar1[i] = "redmi";
//                }
//                if (i != 0) {
//                    if (ar1[i].equals(ar1[0]))
//                        continue;
//                }
//                if (keyw.containsKey(ar1[i]))
//                    continue;
//                // keyw.put(ar1[i],1);
//                list1.add(ar1[i].toLowerCase());
//            }
//        }
//        String name1 = "";
//        for (String xx : list1) {
//            name1 = name1 + " " + xx;
//        }
//        return name1.trim();
//    }
//
//    static PreparedStatement getPreparedStatementFromBean(final MobileCompleteDetails phoneBean) throws
//            SQLException, IOException {
//        final long startTime = System.currentTimeMillis();
//        PreparedStatement preparedStatement = DBClient.getPreparedStatement(INSERT_MOBILE_PROC);
//        //final String mobileName = phoneBean.getGenericName();
//        //   preparedStatement.setString(MOBILE_NAME_COMPRESSED_NOW_INDEX, mobileName.replace(SPACE_SYMBOL, EMPTY_STRING).trim());
//        preparedStatement.setString(RAM_NOW_INDEX, phoneBean.ram);
//        preparedStatement.setString(ACTUAL_MOBILE_NAME_NOW_INDEX, phoneBean.actual_mobile_name);
//        preparedStatement.setString(SELLER_NAME_NOW_INDEX, phoneBean.seller_name);
//        preparedStatement.setInt(PRICE_NOW_INDEX, phoneBean.price);
//        preparedStatement.setString(MOBILE_LINK_NOW_INDEX, phoneBean.mobile_link);
//        preparedStatement.setString(IMAGE_URL_NOW_INDEX, phoneBean.image_url);
//        preparedStatement.setString(ROM_NOW_INDEX, phoneBean.rom);
//        preparedStatement.setInt(av_ind, phoneBean.available_status);
//        final long endTime = System.currentTimeMillis();
//        // logger.info("Time Taken for the getPreparedStatementFromBean : " + (endTime - startTime) + "ms");
//        return preparedStatement;
//    }
//
//    static public PreparedStatement getPreparedStatementFromBeanApple(final MobileCompleteDetails phoneBean) throws
//            SQLException, IOException {
//        final long startTime = System.currentTimeMillis();
//        PreparedStatement preparedStatement = DBClient.getPreparedStatement(INSERT_MOBILE_Apple_PROC);
//        //final String mobileName = phoneBean.getGenericName();
//        //   preparedStatement.setString(MOBILE_NAME_COMPRESSED_NOW_INDEX, mobileName.replace(SPACE_SYMBOL, EMPTY_STRING).trim());
//        preparedStatement.setString(RAM_NOW_INDEX, phoneBean.ram);
//        preparedStatement.setString(ACTUAL_MOBILE_NAME_NOW_INDEX, phoneBean.actual_mobile_name);
//        preparedStatement.setString(SELLER_NAME_NOW_INDEX, phoneBean.seller_name);
//        preparedStatement.setInt(PRICE_NOW_INDEX, phoneBean.price);
//        preparedStatement.setString(MOBILE_LINK_NOW_INDEX, phoneBean.mobile_link);
//        preparedStatement.setString(IMAGE_URL_NOW_INDEX, phoneBean.image_url);
//        preparedStatement.setString(ROM_NOW_INDEX, phoneBean.rom);
//        preparedStatement.setInt(av_ind, phoneBean.available_status);
//        final long endTime = System.currentTimeMillis();
//        // logger.info("Time Taken for the getPreparedStatementFromBean : " + (endTime - startTime) + "ms");
//        return preparedStatement;
//    }
//
//}