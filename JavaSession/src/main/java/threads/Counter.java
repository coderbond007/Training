package threads;

public class Counter {
    private int count = 0;

    public void increaseCount() {
        count = count + 1;
    }

    public int getCount() {
        return count;
    }

    public void decreaseCount() {
        count = count - 1;
    }
}