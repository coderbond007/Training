package bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static constants.Constants.IMAGE_LINKS;
import static constants.Constants.MATCHER;

public class MobileResponseBean implements Serializable {
    private String name;
    private String minPrice;
    private Map<String, String> specs;
    private List<StoreDetails> stores;
    private String imageURL;

    public static MobileResponseBean getSample() {
        MobileResponseBean mobileResponseBean = new MobileResponseBean();
        mobileResponseBean.name = "OnePlus 7";
        mobileResponseBean.imageURL = "https://images-na.ssl-images-amazon.com/images/I/512xYZ5OjGL._SL1000_.jpg";
        mobileResponseBean.minPrice = "Rs. 40,199";
        mobileResponseBean.specs = new HashMap<>();
        mobileResponseBean.specs.put("Screen-Size", "5\" inch");
        mobileResponseBean.specs.put("Camera", "Dual");

        mobileResponseBean.stores = new ArrayList<>();
        mobileResponseBean.stores.add(new StoreDetails("Rs. 40, 199", "Available", "https://www.amazon.in/Test-Exclusive-608/dp/B07HGBMJT6", "../image/amazon.png"));
        mobileResponseBean.stores.add(new StoreDetails("Rs. 41, 199", "Available", "https://www.amazon.in/Test-Exclusive-608/dp/B07HGBMJT6", "../image/flipkart.jpg"));
        mobileResponseBean.stores.add(new StoreDetails("Rs. 43, 199", "Available", "https://www.amazon.in/Test-Exclusive-608/dp/B07HGBMJT6", "../image/snapdeal.png"));
        mobileResponseBean.stores.add(new StoreDetails("Rs. 41, 199", "Available", "https://www.amazon.in/Test-Exclusive-608/dp/B07HGBMJT6", "../image/tata_cliq.png"));
        mobileResponseBean.stores.add(new StoreDetails("Rs. 45, 199", "Available", "https://www.amazon.in/Test-Exclusive-608/dp/B07HGBMJT6", "../image/paytm.png"));
        return mobileResponseBean;
    }

    public void addStoreDetails(String price, String availability, String link) {
        if (stores == null) {
            stores = new ArrayList<>();
        }
        if (price == null || availability == null || link == null)
            return;
        String imgLink = null;
        for (int currentIndex = 0; currentIndex < MATCHER.length; ++currentIndex) {
            if (link.contains(MATCHER[currentIndex])) {
                imgLink = IMAGE_LINKS[currentIndex];
                break;
            }
        }
        stores.add(new StoreDetails(price, availability, link, imgLink));
    }

    static class StoreDetails implements Serializable {
        String price;
        String availability;
        String link;
        String imgLink;

        public StoreDetails(String price, String availability, String link, String imgLink) {
            this.price = price;
            this.availability = availability;
            this.link = link;
            this.imgLink = imgLink;
        }

        @Override
        public String toString() {
            return "StoreDetails{" +
                    "price='" + price + '\'' +
                    ", availability='" + availability + '\'' +
                    ", link='" + link + '\'' +
                    ", imgLink='" + imgLink + '\'' +
                    '}';
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(String minPrice) {
        this.minPrice = minPrice;
    }

    public Map<String, String> getSpecs() {
        return specs;
    }

    public void setSpecs(Map<String, String> specs) {
        this.specs = specs;
    }

    public List<StoreDetails> getStores() {
        return stores;
    }

    public void setStores(List<StoreDetails> stores) {
        this.stores = stores;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    @Override
    public String toString() {
        return "MobileResponseBean{" +
                "name='" + name + '\'' +
                ", minPrice='" + minPrice + '\'' +
                ", specs=" + specs +
                ", stores=" + stores +
                ", imageURL='" + imageURL + '\'' +
                '}';
    }
}
