package tatacliq;

import clients.GsonClient;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

class DataCounter {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(new File("/Users/pradyumn.ag/IdeaProjects/SmartMobilePriceComparator/src/main/java/tatacliq/searchresults.json")));
        StringBuilder stringBuilder = new StringBuilder();
        while (bufferedReader.ready()) {
            stringBuilder.append(bufferedReader.readLine());
        }
        ResponseParser responseParser = GsonClient.getClient().fromJson(stringBuilder.toString(), ResponseParser.class);
        System.err.println(responseParser.searchresult.size());
    }
}
