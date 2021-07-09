package threads;

import java.util.concurrent.Callable;

public class MyThread2 implements Callable {
    String name;

    public MyThread2(String name) {
        this.name = name;
    }

    @Override
    public Object call() throws Exception {
        for (int it = 0; it < 100; ++it) {
            System.out.println("Print iter number : " + it + " for name : " + name);
        }
        return null;
    }
}
