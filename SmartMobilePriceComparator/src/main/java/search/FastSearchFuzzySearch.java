package search;

import bean.BasicMobileDetails;
import me.xdrop.fuzzywuzzy.Applicable;
import me.xdrop.fuzzywuzzy.FuzzySearch;
import me.xdrop.fuzzywuzzy.model.ExtractedResult;
import server.BuildEnvironment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import static constants.Constants.EMPTY_STRING;
import static constants.Constants.GB;
import static constants.Constants.SPACE_SYMBOL;
import static search.FetchMobileWithCompressedName.fetchResult;

public class FastSearchFuzzySearch {
    private static List<BasicMobileDetails> phoneDetails;
    private static String[] NOW_COLORS = new String[]{"gold, blue\n" +
            "white;silver\n" +
            "black;silver\n" +
            "black/white/grey\n" +
            "blue & black / grey & black\n" +
            "space grey/silver/gold/rose gold\n" +
            "white-silver\n" +
            "blue / silver / gold / red\n" +
            "white & orange\n" +
            "black & champagne\n" +
            "gold/ silver/ space grey\n" +
            "blue/lake blue\n" +
            "white+gold/grey+silver\n" +
            "black & blue\n" +
            "titanium-grey\n" +
            "black;gold\n" +
            "black & gold\n" +
            "black-champagne\n" +
            "glossy black/white/glacier blue/red\n" +
            "white-gold\n" +
            "blue & black\n" +
            "-\n" +
            "black;blue\n" +
            "moonlight white / shimmer gold / sapphire black\n" +
            "silver/dark grey/gold\n" +
            "white, black, copper, aqua green\n" +
            "white-dark grey\n" +
            "red, black\n" +
            "silver/rose gold/gold/space grey\n" +
            "champagne-gold\n" +
            "black, white, golden\n" +
            "white/black\n" +
            "silver, black\n" +
            "white + champagne\n" +
            "black / white\n" +
            "champagne,grey\n" +
            "white;black\n" +
            "black / gold / silver\n" +
            "white/black+silver\n" +
            "milky-way grey\n" +
            "gold/black/silver\n" +
            "titanium grey / glacier silver / sand gold\n" +
            "gold/black\n" +
            "white/rainbow white\n" +
            "white & champagne\n" +
            "champagne / champ\n" +
            "black;champagne\n" +
            "black+silver/white\n" +
            "black&yellow\n" +
            "black&red\n" +
            "blue/gold\n" +
            "blue;silver\n" +
            "black-sandstone\n" +
            "gold/royal gold\n" +
            "black/champagne\n" +
            "blue & champagne\n" +
            "black/white/gold\n" +
            "coffee & blue\n" +
            "black-red\n" +
            "grey / silver\n" +
            "black;brown\n" +
            "champagne/grey\n" +
            "black/white/blue/red\n" +
            "black/silver/red/gold\n" +
            "white, grey\n" +
            "white-grey\n" +
            "black & grey\n" +
            "black;grey\n" +
            "white, champange\n" +
            "grey/space grey\n" +
            "black/white\n" +
            "gold/silver\n" +
            "blue-copper\n" +
            "white/silver/yellow/red/gold/silver blue\n" +
            "blue-silver\n" +
            "black/tuxedo black\n" +
            "white/grey\n" +
            "black & red\n" +
            "champagne & black\n" +
            "white & golden\n" +
            "black-green\n" +
            "black/silver/white/gold\n" +
            "black & silver\n" +
            "black sandstone/black gold\n" +
            "grey & silver\n" +
            "black/red\n" +
            "white/silver/blue/red/pink/gold\n" +
            "sheer gold / chic pink / pure white / aqua blue / glacier grey / silver\n" +
            "gold, black\n" +
            "black & white\n" +
            "grey / silver / gold\n" +
            "red & black\n" +
            "bluem/blue\n" +
            "silver & grey\n" +
            "black/silver/gold/jet black/rose gold\n" +
            "white & blue\n" +
            "multi-coloured\n" +
            "pink /black/gold\n" +
            "white & gold\n" +
            "black-gold\n" +
            "white+silver\n" +
            "black & green\n" +
            "meteor gray, white luxury, roast chestnut\n" +
            "black-blue\n" +
            "champagne-white"};

    static {
        try {
            phoneDetails = BuildEnvironment.getMobiles();
        } catch (IOException | SQLException e) {
            try {
                e.printStackTrace();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    //static ArrayList<BasicMobileDetails> sampleList;
    public static void main(String[] args) throws Exception {
        final long startTime = System.currentTimeMillis();
        pair ra = extractRamandRom("Mi A3 4GB RAM, 64GB Storage ");
        //System.out.println(ra.ram+" "+ra.rom);
        getResultForExtension("Samsung Galaxy M20 (Ocean Blue, 4+64GB)");
        //getResult("one plus 7");
//        getResultForExtension("samsung note 3");
        //getResultForExtension("samsung note 3 (12+64gb) space black");
//        getResultForExtension("samsung note 3 (12+64gb) space black");
//        getResultForExtension("samsung note 3 (12+64gb) space black");
//        getResultForExtension("samsung note 3 (12+64gb) space black");
        final long endTime = System.currentTimeMillis();
        System.out.println("Time Taken : " + (endTime - startTime) + "ms");
    }

    public static List<Long> getResult(String query) throws IOException, SQLException {
        try {
            // FileReader fl=new FileReader("/Users/ankur.y/Desktop/listNames.txt");
            List<String> listName = new ArrayList<>();
            //Scanner scanner=new Scanner(fl);
            //while (scanner.hasNext()){
            //  listName.add(scanner.nextLine().trim());
            //}
            MyApplicable applicable = new MyApplicable();
            for (int i = 0; i < phoneDetails.size(); i++) {
                listName.add(phoneDetails.get(i).getName());
            }

            System.out.println(applicable.apply("apple iphone 7", query));
            System.out.println(applicable.apply("apple iphone 6", query));

            List<ExtractedResult> extractedResults = FuzzySearch.extractTop(query.trim().toLowerCase(), listName, applicable, Math.min(10, listName.size()));
            List<Long> resutlIds = new ArrayList<>();
            for (ExtractedResult results : extractedResults) {
                System.out.println(results.getString());
                resutlIds.add(phoneDetails.get(results.getIndex()).getId());
            }
            return resutlIds;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public static List<Long> getResultForExtension(String query) throws Exception {
        query = query.trim().toLowerCase();
        if (!query.contains(GB))
            return new ArrayList<>();
//        System.out.println(query);
        String clearName = clearName(query);
//        System.out.println(clearName + "alas");
        String ram = extractRamandRom(query).ram;
        String rom = extractRamandRom(query).rom;
        String compressed_name = doCompression(clearName);
        return fetchResult(new ExtensionData(compressed_name, ram, rom));
    }

    public static class ExtensionData {
        public String compressed_name;
        public String ram;
        public String rom;

        public ExtensionData(String compressed_name, String ram, String rom) {
            this.compressed_name = compressed_name;
            this.ram = ram;
            this.rom = rom;
        }
    }

    static String doCompression(String word) {
        // remebembert the case of motorola.
        String name1 = word.toLowerCase().trim();
        HashSet<String> compressed = new HashSet<>();
        String par[] = name1.split(" ");
        for (int i = 0; i < par.length; i++) {
            par[i] = par[i].trim();
            if (par[i].length() > 0) {
                compressed.add(par[i]);
            }
        }
        if (name1.contains("moto ") || name1.contains("motorola")) {
            compressed.add("moto");
            compressed.add("motorola");
        }
        ArrayList<String> comp = new ArrayList<>();
        for (String x : compressed) {
            comp.add(x);
        }
        Collections.sort(comp);
        String compres = "";
        for (String x : comp) {
            compres = compres + x;
        }
        compres = compres.trim();
        compres = compres.toLowerCase();
        return compres;
    }

    static boolean validator(String name) {
        if (name.contains("gb")) {
            return true;
        } else {
            return false;
        }
    }
    //public static List<Long> getResultForExtension(String query) {
    //  try {
          /*  query = query.toLowerCase();
            query = query.replace("gb", " ");
            query = query.replace("(", " ");
            query = query.replace(")", " ");
            query = query.replace(",", " ");
            query = query.replace("  ", " ");
            query = query.replace("  ", " ");
            query = query.replace("+", " ");
//            query = query + " gb";
            query = query.trim();
            System.out.println(query.split(" ").length);
            System.out.println(query);
            List<BasicMobileDetails> mylist = BuildEnvironment.getMobiles();
            HashSet<String> colors = new HashSet<>();
            for (int i = 0; i < mylist.size(); i++) {
                colors.add(mylist.get(i).getColor().trim().toLowerCase());
            }
            HashSet<String> brand = new HashSet<>();
            for (int i = 0; i < mylist.size(); i++) {
                brand.add(mylist.get(i).getBrandName().trim().toLowerCase());
            }
            String[] keywords = query.split(" ");
            boolean isfound = false;
            for (int i = 0; i < keywords.length - 1; i++) {
                if (colors.contains(keywords[i] + " " + keywords[i + 1])) {
                    String[] keywordsWithoutColors = new String[keywords.length - 2];
                    int cnt = 0;
                    for (int j = 0; j < keywords.length; j++) {
                        if (j != i && j != i + 1) {
                            keywordsWithoutColors[cnt++] = keywords[j];
                        }
                    }
                    keywords = keywordsWithoutColors;
                    isfound = true;
                    break;
                }
            }
            if (!isfound) {
                for (int i = 0; i < keywords.length; i++) {
                    if (colors.contains(keywords[i])) {
                        String keywordsWithoutColors[] = new String[keywords.length - 1];
                        int cnt = 0;
                        for (int j = 0; j < keywords.length; j++) {
                            if (j != i) {
                                keywordsWithoutColors[cnt++] = keywords[j];
                            }
                        }
                        keywords = keywordsWithoutColors;
                        isfound = true;
                        break;
                    }
                }
            }
            for (int i = 0; i < keywords.length; i++) {
                System.out.print(keywords[i] + " ");
            }
            String bname = null;
            for (int i = 0; i < keywords.length; i++) {
                if (brand.contains(keywords[i].toLowerCase().trim())) {
                    bname = keywords[i].toLowerCase().trim();
                    break;
                }
            }
            String currQuery = "";
            for (int i = 0; i < keywords.length; i++) {
                currQuery = currQuery + " " + keywords[i];
            }
            ArrayList<String> list = new ArrayList<>();
            ArrayList<Long> indexes = new ArrayList<>();
            for (int i = 0; i < mylist.size(); i++) {
                if (bname != null) {
                    if (mylist.get(i).getBrandName().trim().toLowerCase().equals(bname)) {
                        list.add(mylist.get(i).getName());
                        indexes.add(mylist.get(i).getId());
                    }
                } else {
                    list.add(mylist.get(i).getName());
                    indexes.add(mylist.get(i).getId());
                }
            }
            List<ExtractedResult> result = FuzzySearch.extractTop(currQuery, list, 5);
            System.out.println(result.size());
            for (int i = 0; i < result.size(); i++) {
                System.out.println(result.get(i).getString());
            }
            List<Long> findexes = new ArrayList<>();
            for (int i = 0; i < result.size(); i++) {
                findexes.add(indexes.get(result.get(i).getIndex()));
            }
            return findexes;*/

      /*  } catch (Exception e) {
          e.printStackTrace();
        }
        return new ArrayList<>();
    }
*/

    static class MyApplicable implements Applicable {
        @Override
        public int apply(String s1, String s2) {
            // System.out.println(s1);
            s1 = s1.toLowerCase();
            String[] keywords = s1.split(" ");
            String words = "";
            for (int i = 0; i < keywords.length; i++) {
                words = words + keywords[i];
            }
            s2 = s2.toLowerCase();
            String wordsS2 = "";
            String[] keyWordsS2 = s2.split(SPACE_SYMBOL);
            String s3 = s2.toLowerCase();
            s3 = s3.replace(SPACE_SYMBOL, EMPTY_STRING);
            int w1 = FuzzySearch.ratio(words, s3);
            HashSet<String> set1 = new HashSet<>();
            HashSet<String> set2 = new HashSet<>();
            HashSet<String> setUnion = new HashSet<>();

            for (String x : keywords) {
                if (x.trim().length() == 0) {
                    continue;
                }
                set1.add(x);
                setUnion.add(x);
            }
            for (String x : keyWordsS2) {
                if (x.trim().length() == 0) {
                    continue;
                }
                set2.add(x);
                setUnion.add(x);
            }
            double w2 = set1.size() + set2.size() - setUnion.size();
            w2 = w2 / (Math.max(set1.size(), set2.size()));
            w2 = w2 * 100;
            //   System.out.println(w1+" "+w2);
            double w3 = w1 * .5 + w2 * .5;
            return (int) w3;
        }
    }

    public static class pair {
        public String ram, rom;

        public pair(String ram, String rom) {
            this.ram = ram;
            this.rom = rom;
        }
    }

    public static pair extractRamandRom(String name) {
        String ram1 = "";
        String ram2 = "";
        name = name.toLowerCase();
        name = name.replace(",", " ");
        int i1 = -1;
        for (int i = 0; i < name.length() - 1; i++) {
            if (name.substring(i, i + 2).equals("gb")) {
                i1 = i - 1;
                break;
            }
        }
        int pos1 = i1;
        int prev = -1;
        while (pos1 >= 0) {
            if (Character.isDigit(name.charAt(pos1)) || Character.isSpaceChar(name.charAt(pos1))) {
                if (Character.isDigit(name.charAt(pos1))) {
                    ram1 = name.charAt(pos1) + ram1;
                    prev = 1;
                } else if (prev == 1) {
                    break;
                }
                pos1--;

            } else {
                break;
            }
        }
        int i2 = -1;
        for (int i = i1 + 2; i < name.length() - 1; i++) {
            if (name.substring(i, i + 2).equals("gb")) {
                i2 = i - 1;
                break;
            }
        }
        pos1 = i2;
        prev = -1;
        while (pos1 >= 0) {
            if (Character.isDigit(name.charAt(pos1)) || Character.isSpaceChar(name.charAt(pos1))) {
                if (Character.isDigit(name.charAt(pos1))) {
                    ram2 = name.charAt(pos1) + ram2;
                    prev = 1;
                } else if (prev == 1) {
                    break;
                }
                pos1--;
            } else {
                break;
            }
        }
        int index = name.indexOf('+');
        if (index >= 0) {
            int p1 = index - 1;
            int p2 = index + 1;
            while (p1 >= 0 && Character.isDigit(name.charAt(p1))) {
                p1--;
            }
            while (p2 < name.length() && Character.isDigit(name.charAt(p2))) {
                p2++;
            }
            ram1 = name.substring(p1 + 1, index);
            ram2 = name.substring(index + 1, p2);
        }
        if (ram1.length() == 0 && ram2.length() == 0) {
            return new pair("0", "0");
        }
        if (ram1.length() == 0) {
            return new pair("0", ram2);
        }
        if (ram2.length() == 0) {
            return new pair("0", ram1);
        }
        return new pair(Math.min(Integer.parseInt(ram1), Integer.parseInt(ram2)) + "", Math.max(Integer.parseInt(ram1), Integer.parseInt(ram2)) + "");
    }

    static public String clearName(String name) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(new File("/Users/pradyumn.ag/IdeaProjects/Archive/SmartMobilePriceComparator/src/main/java/search/mobileNames.txt")));
        HashMap<String, Integer> hm = new HashMap<>();
        hm.put("5t", 1);
        while (bufferedReader.ready()) {
            String x = bufferedReader.readLine().toLowerCase();
            String[] parts = x.split(" ");
            for (String part : parts) {
                hm.put(part, hm.getOrDefault(part, 0) + 1);
            }
        }
        System.out.println(hm.get("galaxy"));
        hm.remove("ram");
        for (int i = 0; i <= 512; i++) {
            if (hm.containsKey(i + "gb")) {
                hm.remove(i + "gb");
                hm.remove(i + " " + "mb");
            }
        }
        if (hm.containsKey("gb"))
            hm.remove("gb");
        for (String col : NOW_COLORS) {
            col = col.replace("-", " ");
            col = col.replace("+", " ");
            col = col.replace("/", " ");
            col = col.replace("&", " ");
            col = col.replace(";", " ");
            col = col.replace(",", " ");
            String coll[] = col.split(" ");
            for (String xxx : coll) {
                xxx = xxx.toLowerCase();
                if (hm.containsKey(xxx)) ;
                hm.remove(xxx);
            }
        }

        String actual_name = name.toLowerCase();
        {
            int i = actual_name.indexOf("gb");
            if (i > 0) {

                if (actual_name.charAt(i - 1) == ' ') {
                    System.out.println(i);
                    actual_name = actual_name.substring(0, i - 1) + "" + actual_name.substring(i);
                }
            }
            i = actual_name.indexOf("gb");
            if (i > 0) {
                if (actual_name.charAt(i - 1) == ' ') {
                    System.out.println(i);
                    actual_name = actual_name.substring(0, i - 1) + "" + actual_name.substring(i);
                }
            }
        }
        int index11 = actual_name.indexOf("(");
        if (index11 >= 0)
            actual_name = actual_name.substring(0, index11);
        index11 = actual_name.indexOf("gb");
        if (index11 >= 0) {
            actual_name = actual_name.substring(0, index11 + 2);
        }
        actual_name = actual_name.replace(")", " ");
        actual_name = actual_name.replace("(", " ");
        actual_name = actual_name.replace(",", " ");
        actual_name = actual_name.replace("-", " ");
        actual_name = actual_name.trim();
        String ar1[] = actual_name.split(" ");
        HashSet<String> set = new HashSet<>();
        ArrayList<String> list1 = new ArrayList<>();

        System.out.println(hm.get("galaxy"));

        for (int i = 0; i < ar1.length; i++) {
            if (hm.containsKey(ar1[i].toLowerCase()) && hm.get(ar1[i].toLowerCase()) >= 1) {
                if (ar1[i].equals("mi") || ar1[i].equals("xiaomi")) {
                    ar1[i] = "redmi";
                }
                if (i != 0) {
                    if (ar1[i].equals(ar1[0]))
                        continue;
                }
                list1.add(ar1[i].toLowerCase());
            }
        }
        StringBuilder name1 = new StringBuilder();
        for (String xx : list1) {
            name1.append(" ").append(xx);
        }
        System.out.println(name1);
        return name1.toString().trim();
    }
}