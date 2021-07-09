package bean;

public class MobileDetails {
    public long price;
    public String mobile_Name;
    public String link;
    public String seller_Name;
    public String color;
    public String ram;
    public String imageURL;
    public String avail;

    public MobileDetails(long price, String mobile_Name, String link, String seller_Name, String color, String ram, String imageURL, String avail) {
        this.price = price;
        this.mobile_Name = mobile_Name;
        this.link = link;
        this.seller_Name = seller_Name;
        this.color = color;
        this.ram = ram;
        this.imageURL = imageURL;
        this.avail = avail;
    }

}