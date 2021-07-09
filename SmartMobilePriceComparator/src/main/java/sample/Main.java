package sample;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(new File("PATH")));
        Gson gson = new Gson();
        StringBuilder stringBuilder = new StringBuilder();
        while (bufferedReader.ready()) {
            stringBuilder.append(bufferedReader.readLine());
        }
        List<MyData> data = gson.fromJson(stringBuilder.toString(), List.class);
        for (MyData myData : data) {
            System.out.println(myData.toString());
        }
    }

    static class MyData implements Serializable {
        String actual_mobile_name;
        String variant;
        String color;
        String brand;

        public MyData(String actual_mobile_name, String variant, String color, String brand) {
            this.actual_mobile_name = actual_mobile_name;
            this.variant = variant;
            this.color = color;
            this.brand = brand;
        }

        @Override
        public String toString() {
            return "MyData{" +
                    "actual_mobile_name='" + actual_mobile_name + '\'' +
                    ", variant='" + variant + '\'' +
                    ", color='" + color + '\'' +
                    ", brand='" + brand + '\'' +
                    '}';
        }
    }
}
