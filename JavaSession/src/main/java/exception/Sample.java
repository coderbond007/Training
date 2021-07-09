package exception;

import java.io.PrintWriter;

public class Sample {
    public static void main(String[] args) throws Exception {
        PrintWriter writer;
        try (Resource resource = new Resource()) {
            throw new MyException("Sample");
        } catch (MyException e) {
            e.printStackTrace();
        }
    }

    static class Resource implements AutoCloseable {
        public void close() throws Exception {
            System.out.println("Close all the open connections or streams");
        }
    }
}
