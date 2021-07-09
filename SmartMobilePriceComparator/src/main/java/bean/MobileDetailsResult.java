package bean;

import java.util.ArrayList;

public class MobileDetailsResult {

    private ArrayList<MobileDetails> list;

    public MobileDetailsResult() {
        list = new ArrayList<>();
    }

    public void addMobileDetails(long price, String mobile_Name, String link, String seller_Name, String color, String ram, String imageURL, String avail) {
        list.add(new MobileDetails(price, mobile_Name, link, seller_Name, color, ram, imageURL, avail));
    }

    @Deprecated
    public void addMobileDetails(long price, String mobile_Name, String link, String seller_Name, String color, String ram) {
        addMobileDetails(price, mobile_Name, link, seller_Name, color, ram, null, "Wrong");
    }

    public ArrayList<MobileDetails> getList() {
        return list;
    }
}
