package threads;

public class MyThread1 extends Thread {
    String name;
    static Counter counter = new Counter();
    final long TIME_CONSTANT = 2;
    final int ITERATE_CONSTANT = 200;

    public MyThread1(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        func1();
        System.out.println("Done func1 for " + name);
        func2();
        System.out.println("Done func2 for " + name);
    }

    private void func1() {
        for (int it = 0; it < ITERATE_CONSTANT; ++it) {
            synchronized (counter) {
                counter.increaseCount();
                try {
                    Thread.sleep(TIME_CONSTANT);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
//                System.out.println(name + " counter : " + counter.getCount());
            }
        }
    }

    private void func2() {
        for (int it = 0; it < ITERATE_CONSTANT; ++it) {
            synchronized (counter) {
                counter.decreaseCount();
                try {
                    Thread.sleep(TIME_CONSTANT);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
//                System.out.println(name + " counter : " + counter.getCount());
            }
        }
    }
}
