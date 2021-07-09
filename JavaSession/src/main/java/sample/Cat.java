package sample;

public class Cat extends Animal {

    public Cat(String name) {
        super(name);
    }

    public void run() {
        System.out.println("Cat is running");
    }

    public void callMyName() {
        System.out.println("I am cat and my name is : " + getName());
    }
}
