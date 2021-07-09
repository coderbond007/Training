package sample;

public class Dog extends Animal {
    public Dog(String name) {
        super(name);
    }

    public void run() {
        System.out.println("Dog is running");
    }

    public void callMyName() {
        System.out.println("I am dog and my name is : " + getName());
    }

    @Override
    public String toString() {
        return getName();
    }
}
