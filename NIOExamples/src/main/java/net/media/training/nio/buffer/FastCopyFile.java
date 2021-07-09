package net.media.training.nio.buffer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FastCopyFile {


    public static void copyFileUsingDMA(File src, File dest) throws IOException {
        if (!src.exists()) {
            // either return quietly OR throw an exception
            return;
        }
        if (!dest.exists()) {
            dest.createNewFile();
        }

        FileChannel source = new FileInputStream(src).getChannel();
        try {
            FileChannel destination = new FileOutputStream(dest).getChannel();
            try {
                source.transferTo(0, source.size(), destination);
                // destination.transferFrom(source, 0, source.size());
            } finally {
                if (destination != null) {
                    destination.close();
                }
            }
        } finally {
            if (source != null) {
                source.close();
            }
        }
    }

    public static void copyFileNativeLooping(File infile, File outfile) throws IOException {
        FileInputStream fin = new FileInputStream(infile);
        FileOutputStream fout = new FileOutputStream(outfile);

        FileChannel fcin = fin.getChannel();
        FileChannel fcout = fout.getChannel();


        ByteBuffer buffer = ByteBuffer.allocateDirect(1024);

        while (true) {
            buffer.clear();

            int r = fcin.read(buffer);

            if (r == -1) {
                break;
            }

            buffer.flip();
            fcout.write(buffer);
        }
    }

    static public void main(String[] args) throws Exception {
//        if (args.length < 2) {
//            System.err.println("Usage: java FastCopyFile infile outfile");
//            System.exit(1);
//        }

        String infile = "writesomebytes.txt";
        String outfile = "writesomebytes-jfjkdhfkjds.txt";

        copyFileNativeLooping(new File(infile), new File(outfile));

        copyFileUsingDMA(new File(infile), new File(outfile));
        /**
         * Java 7
         * Files.copy(src, dest);
         * OR
         * Files.copy(src, dest, StandardCopyOption.REPLACE_EXISTING);
         */


    }

}
