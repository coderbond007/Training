package net.media.training;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Random;
import java.io.FileWriter;

/**
 * Created by mettu.r on 24/07/17.
 */
public class InputGenerator {
    private static String words[] = {"Linguistic", "theories", "generally", "regard", "human", "languages", "as", "consisting", "of", "two", "parts", "a", "lexicon", "essentially", "a", "catalogue", "of", "a", "language", "words", "its", "wordstock", "and", "a", "grammar", "a", "system", "of", "rules", "which", "allow", "for", "the", "combination", "of", "those", "words", "into", "meaningful", "sentences", "The", "lexicon", "is", "also", "thought", "to", "include", "bound", "morphemes", "which", "cannot", "stand", "alone", "as", "words", "such", "as", "most", "affixes",
            "In", "some", "analyses", "compound", "words", "and", "certain", "classes", "of", "idiomatic", "expressions", "and", "other", "collocations", "are", "also", "considered", "to", "be", "part", "of", "the", "lexicon"};

    public static void main(String args[]) throws IOException {
        Random random = new Random();
        BufferedWriter bw = null;
        FileWriter fw = null;
        try{
            fw = new FileWriter(args[0]);
            bw = new BufferedWriter(fw);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int noOfLines = Integer.parseInt(args[1]);
        int noOfWords = Integer.parseInt(args[2]);
        for (int i = 0; i < noOfLines; i++) {
            boolean firstWord = true;
            for (int j = 0; j < noOfWords; j++) {
                int idx = random.nextInt(82);
                if( !firstWord )
                    bw.write(",");
                firstWord = false;
                bw.write(words[idx]);
            }
            bw.newLine();
        }
        bw.close();
    }
}
