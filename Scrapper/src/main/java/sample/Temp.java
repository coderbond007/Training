package sample;

public class Temp {

  private static final String GOOGLE_SEARCH_QUERY = "https://www.google.com/search?q=";

  public static void main(String[] args) {
    Face1 face1 = new Check();
    face1.close();

    Face2 face2 = new Check();
    face2.close();

    Check check = new Check();
    check.close();

    AutoCloseable autoCloseable = new Check();
    try {
      autoCloseable.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
