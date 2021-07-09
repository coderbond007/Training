package sample;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Checker {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader("/Users/pradyumn.ag/IdeaProjects/SmartMobilePriceComparator/src/main/java/sample/temp.txt"));
        List<String> links = new ArrayList<>();
        while (bufferedReader.ready()) {
            String line = bufferedReader.readLine();
            String[] parts = line.split("\t");
            String link = parts[parts.length - 1];
            if (line.contains("iphone")) continue;
            links.add("\"" + link + "\"");
        }
        System.out.println(links.toString().replace("[", "{").replace("]", "}"));
    }
}
