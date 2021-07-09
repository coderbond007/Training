package temp;

import org.eclipse.jetty.util.StringUtil;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static final String PERCENTAGE_SYMBOL = "%";
    public static final String EMPTY_STRING = "";

    public static void main(String[] args) throws IOException, SQLException {
        System.out.println(processRam("256+256"));
        System.out.println(processRam("+256"));
        System.out.println(processRam("256+"));
        System.out.println(processRam("512+12"));
        System.out.println(processRam("0+"));
        System.out.println(processRam("+0"));

        System.out.println(processRam("0+0"));
        System.out.println(processRam("0+256"));
        System.out.println(processRam("245+0"));
    }

    public static String processRam(final String ram) {
        if (StringUtil.isBlank(ram))
            return null;
//        if (ram.startsWith("+") || ram.endsWith("+")) {
//            List<String> list = splitter(ram);
//            String[] parts = new String[2];
//            parts[0] = list.get(0);
//            parts[1] = list.get(1);
//
//            String ramNow = parts[0];
//            String romNow = parts[1];
//            if (ramNow.equals("0")) {
//                ramNow = EMPTY_STRING;
//            }
//            if (romNow.equals("0")) {
//                romNow = EMPTY_STRING;
//            }
//            if (!StringUtil.isBlank(parts[0])) {
//                return parts[0].length() >= 3 ? parts[0] + "MB" : parts[0] + "GB";
//            } else if (!StringUtil.isBlank(parts[1])) {
//                return parts[1] + "GB";
//            }
//        } else {
            List<String> list = splitter(ram);
            String[] parts = new String[2];
            parts[0] = list.get(0);
            parts[1] = list.get(1);

            String ramNow = parts[0];
            String romNow = parts[1];
            if (ramNow.equals("0")) {
                ramNow = EMPTY_STRING;
            }
            if (romNow.equals("0")) {
                romNow = EMPTY_STRING;
            }
            if (ramNow.length() >= 3) {
                ramNow += "MB";
            } else if (ramNow.length() >= 1) {
                ramNow += "GB";
            }
            if (romNow.length() > 0) {
                romNow += "GB";
            }
            String ans;
            if (!romNow.equals(EMPTY_STRING) && !ramNow.equals(EMPTY_STRING)) {
                ans = ramNow + "+" + romNow;
            } else {
                ans = ramNow + romNow;
            }
            return ans;
//        }
//        return null;
    }

    private static final List<String> splitter(final String s) {
        int ind = s.indexOf("+");
        List<String> ans = new ArrayList<>();
        if (ind >= 0)
            ans.add(s.substring(0, ind));
        if (ind < s.length())
            ans.add(s.substring(ind + 1));
        while (ans.size() < 2) ans.add("");
        return ans;
    }
}
