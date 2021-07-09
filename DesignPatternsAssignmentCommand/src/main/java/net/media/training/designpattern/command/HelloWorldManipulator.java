package net.media.training.designpattern.command;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by IntelliJ IDEA.
 * User: goyalamit
 * Date: Jul 20, 2011
 * Time: 4:55:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class HelloWorldManipulator {

    public void fixManipulate(final String fileName, final String newPath) {
        int cnt = 0;
        try {
            create(fileName, "hello world");
            cnt = 1;
            update(fileName, "new hello world");
            cnt = 2;
            move(fileName, newPath);
            cnt = 3;
        } catch (Exception e) {
            switch (cnt) {
                case 3:
                    move(newPath, fileName);
                case 2:
                    update(fileName, "hello world");
                case 1:
                    delete(fileName);

            }
        }
    }

    private void delete(String fileName) {
        File file = new File(fileName);
        file.delete();
    }

    private void move(String source, String destination) {
        File file = new File(source);
        if (!file.exists())
            throw new RuntimeException("File does not exist");
        if (!file.renameTo(new File(destination)))
            throw new RuntimeException("Rename failed");
    }

    private void update(String fileName, String content) {
        File file = new File(fileName);
        if (!file.exists())
            throw new RuntimeException("File: " + fileName + " does not exist");
        try {

            PrintWriter writer = new PrintWriter(file);
            writer.write(content);
            writer.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void brokenManipulate(String fileName, String newPath) {
        int cnt = 0;
        try {
            create(fileName, "hello world");
            cnt = 1;
            update(fileName, "new hello world");
            cnt = 2;
            move(fileName, newPath);
            cnt = 3;
            create(fileName, "hello world");
            cnt = 4;
            create(fileName, "hello world");
            cnt = 5;
        } catch (Exception e) {
            switch (cnt) {
                case 5:
                    delete(fileName);
                case 4:
                    delete(fileName);
                case 3:
                    move(newPath, fileName);
                case 2:
                    update(fileName, "hello world");
                case 1:
                    delete(fileName);

            }
        }


    }

    private void create(String fileName, String content) {
        File file = new File(fileName);
        if (file.exists())
            throw new RuntimeException("File already exist");
        try {
            file.createNewFile();
            PrintWriter writer = new PrintWriter(file);
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
