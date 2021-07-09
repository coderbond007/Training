package net.media.training.sample;

import java.nio.CharBuffer;

public class Main {
    public static void main(String[] args) {
        CharBuffer buffer = CharBuffer.allocate(8);
        String text = "apple";
        System.out.println("Input text: " + text);
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            buffer.put(c);
        }
        int buffPos = buffer.position();
        System.out.println("Position after data is written into buffer: " + buffPos);
        buffer.flip();
        System.out.println("Reading buffer contents:");
        while (buffer.hasRemaining()) {
            System.out.println(buffer.get());
        }
    }
}
