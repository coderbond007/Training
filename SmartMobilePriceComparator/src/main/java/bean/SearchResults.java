package bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Deprecated
public class SearchResults implements Serializable {
    private List<BasicMobileDetails> allMobiles = new ArrayList<>();

    public List<BasicMobileDetails> getAllMobiles() {
        return allMobiles;
    }

    public void addMobileDetails(final String name, final Long price, final Long id, final String ram, final String color, final String imageURL) {
        BasicMobileDetails mobileDetails = new BasicMobileDetails(name, price, id, ram, color, imageURL);
        allMobiles.add(mobileDetails);
    }

    public void addMobileDetails(final String name, final Long price, final Long id, final String ram, final String color) {
        addMobileDetails(name, price, id, ram, color, null);
    }

    public static class BasicMobileDetails implements Serializable {
        String name;
        Long price;
        Long id;
        String ram;
        String color;
        String imageURL;

        BasicMobileDetails(String name, Long price, Long id, String ram, String color, String imageURL) {
            this.name = name;
            this.price = price;
            this.id = id;
            this.ram = ram;
            this.color = color;
            this.imageURL = imageURL;
        }

        BasicMobileDetails(String name, Long price, Long id, String ram, String color) {
            this(name, price, id, ram, color, null);
        }
    }
}
