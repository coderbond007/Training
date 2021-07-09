package net.media.training.live.dip;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Base64;

public class EncodeFileBased implements EncodingInterface{

    @Override
    public void encode() {
        try {
            final String SOURCE_DIRECTORY = System.getProperty("user.dir");
            final String INPUT_PATH = SOURCE_DIRECTORY + "/src/main/java/net/media/training/live/dip/beforeEncryption.txt";
            final String OUTPUT_PATH = SOURCE_DIRECTORY + "/src/main/java/net/media/training/live/dip/afterEncryption.txt";
//            BufferedReader reader = new BufferedReader(new FileReader("/Users/goyalamit/Sandbox/training/src/solid_2011/live/dip/beforeEncryption.txt"));
//            BufferedWriter writer = new BufferedWriter(new FileWriter("/Users/goyalamit/Sandbox/training/src/solid_2011/live/dip/afterEncryption.txt"));

            BufferedReader reader = new BufferedReader(new FileReader(INPUT_PATH));
            BufferedWriter writer = new BufferedWriter(new FileWriter(OUTPUT_PATH));
            String aLine;
            while ((aLine = reader.readLine()) != null) {
                String encodedLine = Base64.getEncoder().encodeToString(aLine.getBytes());
                writer.write(encodedLine);
            }
            writer.flush();
            writer.close();
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
