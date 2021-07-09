package datacleaner;

import clients.DBClient;
import clients.GsonClient;
import com.google.gson.internal.LinkedTreeMap;
import org.jsoup.helper.StringUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import static constants.Constants.SPACE_SYMBOL;

public class DataAnalyzer {
    public static final String[] SEPARATORS = new String[]{"&", ";", "+", "-", "/", " and "};
    public static final String[] SEPS_NAMES = {"\"", "&", "(", ")", ",", "-", ".", "/", ";", "?", "_", "|", " and "};
    public static String[] stopwords = {"a", "as", "able", "about", "above", "according", "accordingly", "across", "actually", "after", "afterwards", "again", "against", "aint", "all", "allow", "allows", "almost", "alone", "along", "already", "also", "although", "always", "am", "among", "amongst", "an", "and", "another", "any", "anybody", "anyhow", "anyone", "anything", "anyway", "anyways", "anywhere", "apart", "appear", "appreciate", "appropriate", "are", "arent", "around", "as", "aside", "ask", "asking", "associated", "at", "available", "away", "awfully", "be", "became", "because", "become", "becomes", "becoming", "been", "before", "beforehand", "behind", "being", "believe", "below", "beside", "besides", "best", "better", "between", "beyond", "both", "brief", "but", "by", "cmon", "cs", "came", "can", "cant", "cannot", "cant", "cause", "causes", "certain", "certainly", "changes", "clearly", "co", "com", "come", "comes", "concerning", "consequently", "consider", "considering", "contain", "containing", "contains", "corresponding", "could", "couldnt", "course", "currently", "definitely", "described", "despite", "did", "didnt", "different", "do", "does", "doesnt", "doing", "dont", "done", "down", "downwards", "during", "each", "edu", "eg", "eight", "either", "else", "elsewhere", "enough", "entirely", "especially", "et", "etc", "even", "ever", "every", "everybody", "everyone", "everything", "everywhere", "ex", "exactly", "example", "except", "far", "few", "ff", "fifth", "first", "five", "followed", "following", "follows", "for", "former", "formerly", "forth", "four", "from", "further", "furthermore", "get", "gets", "getting", "given", "gives", "go", "goes", "going", "gone", "got", "gotten", "greetings", "had", "hadnt", "happens", "hardly", "has", "hasnt", "have", "havent", "having", "he", "hes", "hello", "help", "hence", "her", "here", "heres", "hereafter", "hereby", "herein", "hereupon", "hers", "herself", "hi", "him", "himself", "his", "hither", "hopefully", "how", "howbeit", "however", "i", "id", "ill", "im", "ive", "ie", "if", "ignored", "immediate", "in", "inasmuch", "inc", "indeed", "indicate", "indicated", "indicates", "inner", "insofar", "instead", "into", "inward", "is", "isnt", "it", "itd", "itll", "its", "its", "itself", "just", "keep", "keeps", "kept", "know", "knows", "known", "last", "lately", "later", "latter", "latterly", "least", "less", "lest", "let", "lets", "like", "liked", "likely", "little", "look", "looking", "looks", "ltd", "mainly", "many", "may", "maybe", "me", "mean", "meanwhile", "merely", "might", "more", "moreover", "most", "mostly", "much", "must", "my", "myself", "name", "namely", "nd", "near", "nearly", "necessary", "need", "needs", "neither", "never", "nevertheless", "new", "next", "nine", "no", "nobody", "non", "none", "noone", "nor", "normally", "not", "nothing", "novel", "now", "nowhere", "obviously", "of", "off", "often", "oh", "ok", "okay", "old", "on", "once", "one", "ones", "only", "onto", "or", "other", "others", "otherwise", "ought", "our", "ours", "ourselves", "out", "outside", "over", "overall", "own", "particular", "particularly", "per", "perhaps", "placed", "please", "plus", "possible", "presumably", "probably", "provides", "que", "quite", "qv", "rather", "rd", "re", "really", "reasonably", "regarding", "regardless", "regards", "relatively", "respectively", "right", "said", "same", "saw", "say", "saying", "says", "second", "secondly", "see", "seeing", "seem", "seemed", "seeming", "seems", "seen", "self", "selves", "sensible", "sent", "serious", "seriously", "seven", "several", "shall", "she", "should", "shouldnt", "since", "six", "so", "some", "somebody", "somehow", "someone", "something", "sometime", "sometimes", "somewhat", "somewhere", "soon", "sorry", "specified", "specify", "specifying", "still", "sub", "such", "sup", "sure", "ts", "take", "taken", "tell", "tends", "th", "than", "thank", "thanks", "thanx", "that", "thats", "thats", "the", "their", "theirs", "them", "themselves", "then", "thence", "there", "theres", "thereafter", "thereby", "therefore", "therein", "theres", "thereupon", "these", "they", "theyd", "theyll", "theyre", "theyve", "think", "third", "this", "thorough", "thoroughly", "those", "though", "three", "through", "throughout", "thru", "thus", "to", "together", "too", "took", "toward", "towards", "tried", "tries", "truly", "try", "trying", "twice", "two", "un", "under", "unfortunately", "unless", "unlikely", "until", "unto", "up", "upon", "us", "use", "used", "useful", "uses", "using", "usually", "value", "various", "very", "via", "viz", "vs", "want", "wants", "was", "wasnt", "way", "we", "wed", "well", "were", "weve", "welcome", "well", "went", "were", "werent", "what", "whats", "whatever", "when", "whence", "whenever", "where", "wheres", "whereafter", "whereas", "whereby", "wherein", "whereupon", "wherever", "whether", "which", "while", "whither", "who", "whos", "whoever", "whole", "whom", "whose", "why", "will", "willing", "wish", "with", "within", "without", "wont", "wonder", "would", "would", "wouldnt", "yes", "yet", "you", "youd", "youll", "youre", "youve", "your", "yours", "yourself", "yourselves", "zero"};
    public static String[] colors = {"androidphone", "smartphone", "android", "mobile", "phone", "storage with no cost emi additional exchange offers", "white with silver carved in", "moon dust grey", "gold on gold", "white light silver", "genuine leather brown", "black with red", "starry night black", "gold on silver", "gun metallic gray", "bronze gold black", "gun metal silver", "new platinum grey", "metalic earl grey", "gun metal grey", "ink black", "metallic black", "rainbow white", "black champagne", "midnight blue", "royal black", "tulip purple", "dazzling white", "slate black", "amazing silver", "metal gold", "bright red", "metal blue", "blue mb", "white blue", "crown gold", "white gold", "topaz gold", "way grey", "mirror grey", "polar white", "dazzling gold", "gold graphite", "glacier silver", "ivory gold", "ruby red", "special wine", "metallic blue", "polaris blue", "chrome black", "aqua blue", "tempered blue", "saturn gold", "topaz blue", "radiant blue", "flash grey", "rose gold", "slate gray", "sandstone black", "metallic gold", "special gold", "capuccino brown", "brushed grey", "elegant blue", "aurora blue", "mineral black", "slate blue", "red hot", "onyx black", "ceramic white", "luminous chrome", "glaze blue", "fluorite purple", "crystal green", "black onyx", "gold champagne", "black titan", "platinum grey", "porcelain white", "sapphire black", "ambient blue", "classic black", "charcoal blue", "clearly white", "charcoal gray", "galaxy black", "olive black", "crystal purple", "nova gold", "golden black", "fresh mint", "pearl white", "blue lagoon", "grey sandstone", "black grey", "graphite black", "lemonade blue", "jade black", "carbon black", "starry purple", "santorini white", "mellow gold", "royal gold", "white silver", "matte blue", "shadow grey", "sapphire cyan", "royal blue", "wine red", "matte gold", "shine gold", "diamond red", "sakura pink", "astral blue", "volcanics gray", "matte red", "emerald green", "black mist", "electric blue", "mirror black", "cosmic grey", "brave blue", "nile blue", "black silver", "sand black", "caviar black", "titan gray", "sheer gold", "premium gold", "white birch", "phantom black", "energetic blue", "navy blue", "special grey", "rush silver", "black sapphire", "blue gold", "rosso red", "prism white", "black sky", "ceramic black", "midnight black", "russet brown", "mystic black", "red velvet", "aura white", "marble white", "posh black", "metalic gold", "neptune blue", "not pink", "pure white", "burgundy red", "gradation black", "dark blue", "fine gold", "shadow black", "marble green", "iron grey", "sunrise gold", "regal gold", "sand gold", "majestic gold", "sheen black", "quite black", "glaring gold", "glossy black", "autumn gold", "moonlight white", "purple myst", "supernova red", "black gold", "mocha gold", "breathing crystal", "black blue", "dazzle blue", "jet black", "coral red", "bright orange", "gun metal", "brilliant black", "metallic silver", "volcanics grey", "comet blue", "dynamic black", "stellar purple", "green emerald", "thunder black", "pearl black", "ice silver", "space grey", "iris purple", "sunset blue", "ocean blue", "sky blue", "maple wood", "tuxedo black", "titan grey", "light blue", "aqua green", "latter gold", "lapland aurora", "shimmer gold", "blue silver", "dark silver", "icy white", "coral blue", "flame red", "twilight blue", "armoured edition", "starry black", "lime gold", "titanium grey", "milan black", "meteor grey", "glamour red", "dark grey", "blue black", "prism black", "black sandstone", "silver blue", "carbon gray", "glacier grey", "black pearl", "moroccan blue", "iron gray", "nebula blue", "gunmetal grey", "mineral blue", "roast chestnut", "venom black", "lunar grey", "champagne gold", "sand white", "deep blue", "city blue", "nebula purple", "tan brown", "silver white", "black steel", "elegant white", "lilac purple", "crystal blue", "indigo blue", "skyline blue", "space black", "coal black", "arora black", "zinc grey", "champange coffee", "bright green", "bold red", "graphite grey", "just black", "sonic black", "nebula red", "white grey", "grey black", "latte gold", "black red", "prime black", "space blue", "buffed steel", "sapphire gradient", "space gray", "milkyway grey", "sapphire blue", "nitro blue", "cobalt white", "sparkling blue", "champagne white", "indigo black", "rose red", "matte black", "marine blue", "deep black", "nebula black", "glacier blue", "pearl gold", "metallic grey", "ambitious black", "slate grey", "silver titanium", "aura black", "chocolate brown", "mars red", "fashion white", "gamma red", "charcoal black", "moondust grey", "white luxury", "alabaster white", "meteor gray", "aurora black", "super black", "gold platinum", "carbon grey", "titan black", "sprinkle white", "cherry black", "titan silver", "pristine white", "platinum gray", "platinum gold", "diamond blue", "marshmallow white", "gunmetal gray", "deep grey", "solar red", "chic pink", "lunar gray", "charcoal grey", "gradient shine", "eclipse black", "lake blue", "aura glow", "black sea", "champange", "skyblue", "phantomblack", "yellow", "gold", "gray", "copper", "black", "beige", "blue", "purple", "finegold", "red", "metalgrey", "white", "champ", "champions", "brown", "bluem", "india", "twilight", "green", "grey", "coffee", "iron", "cyan", "multi", "carbon", "golden", "slate", "metal", "metallic", "silver", "gun", "steel", "pink", "sandstone", "onion", "milky", "platinum", "coral", "titan", "champagne", "indigo", "titanium", "colored", "camouflage", "coloured", "orange"};
    // TODO : handle + with care

    public static void main(String[] args) throws IOException, SQLException {
        Set<String> names = fetchActualNames();
        List<String> ans = new ArrayList<>();
        for (String name : names) {
            name = SPACE_SYMBOL + name + SPACE_SYMBOL;
            for (String rep : SEPS_NAMES) {
                name = name.replace(rep, SPACE_SYMBOL + SPACE_SYMBOL);
            }
            name = removeExtraSpaces(name.trim());
            name = SPACE_SYMBOL + name + SPACE_SYMBOL;
            for (String col : colors) {
                col = SPACE_SYMBOL + col + SPACE_SYMBOL;
                name = name.replace(col, SPACE_SYMBOL + SPACE_SYMBOL);
            }
            ans.add(removeExtraSpaces(name.trim()));
        }
        Collections.sort(ans, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return Integer.compare(o2.length(), o1.length());
            }
        });
        for (String name : ans) {
            System.out.println(name);
        }
    }

    private static String removeExtraSpaces(String col) {
        Stack<Character> stack = new Stack<>();
        for (char ss : col.toCharArray()) {
            if (stack.isEmpty() || !Character.isSpaceChar(ss)) {
                stack.add(ss);
            } else {
                if (Character.isSpaceChar(stack.peek())) {
                    continue;
                } else {
                    stack.add(ss);
                }
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        while (!stack.isEmpty()) {
            stringBuilder.append(stack.pop());
        }
        return stringBuilder.reverse().toString();
    }

    public static Set<String> fetchActualNames() throws SQLException, IOException {
        // SQL command
        PreparedStatement preparedStatement = DBClient.getPreparedStatement("SELECT actual_mobile_name FROM MOBILE_DETAILS;");
        ResultSet resultSet = preparedStatement.executeQuery();
        Set<String> set = new HashSet<>();
        while (resultSet.next()) {
            String name = resultSet.getString("actual_mobile_name").toLowerCase();
            name = name.trim();
            if (name.length() > 0) set.add(name);
        }
        resultSet.close();
        return set;
    }

    public static Set<String> fetchUniqueColors() throws IOException {
        List<MyData> datas = fetchData();
        Set<String> set = new HashSet<>();
        for (MyData data : datas) {
//            set.addAll(processColor(data.color));
            set.add(data.color);
        }
        set.remove("-");
        return set;
    }

    public static Set<String> processColor(String color) {
        Set<String> strings = new HashSet<>();
        for (int i = 0; i < color.length(); ) {
            int j = i;
            StringBuilder stringBuilder = new StringBuilder();
            while (j < color.length() && Character.isAlphabetic(color.charAt(j))) {
                stringBuilder.append(color.charAt(j++));
            }
            i = j + 1;
            String now = stringBuilder.toString().trim();
            if (!StringUtil.isBlank(now)) strings.add(now);
        }
        return strings;
    }

    public static final List<MyData> fetchData() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(new File("/Users/pradyumn.ag/IdeaProjects/SmartMobilePriceComparator/data/entireData.json")));
        StringBuilder stringBuilder = new StringBuilder();
        while (bufferedReader.ready()) {
            String line = bufferedReader.readLine();
            stringBuilder.append(line);
        }
        List<LinkedTreeMap> maps = GsonClient.getClient().fromJson(stringBuilder.toString(), List.class);
        List<MyData> data = new ArrayList<>();
        for (LinkedTreeMap map : maps) {
            data.add(new MyData((String) map.get("actual_mobile_name"), (String) map.get("variant"), (String) map.get("color"), (String) map.get("brand")));
        }
        return data;
    }

    public static List<String> splitter(String s, char p) {
        List<String> strings = new ArrayList<>();
        int startIndex = 0;
        int endIndex = s.indexOf(p);
        while (endIndex >= 0) {
            strings.add(s.substring(startIndex, endIndex).trim());
            startIndex = endIndex + 1;
            endIndex = s.indexOf(p, startIndex);
        }
        strings.add(s.substring(startIndex).trim());
        return strings;
    }

    static class MyData implements Serializable {
        String actual_mobile_name;
        String variant;
        String color;
        String brand;

        public MyData(String actual_mobile_name, String variant, String color, String brand) {
            this.actual_mobile_name = actual_mobile_name;
            this.variant = variant;
            this.color = color;
            this.brand = brand;
        }

        @Override
        public String toString() {
            return "MyData{" +
                    "actual_mobile_name='" + actual_mobile_name + '\'' +
                    ", variant='" + variant + '\'' +
                    ", color='" + color + '\'' +
                    ", brand='" + brand + '\'' +
                    '}';
        }
    }
}
