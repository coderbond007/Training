package sample;

public class Check implements Face1, Face2, AutoCloseable {

  @Override
  public void close() {
    System.out.println("abcvdsjcd");
  }
}
