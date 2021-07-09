package FormattingParser;

public class MobileCompleteDetails {
    public String ram;
    public String rom;
    public String actual_mobile_name;
    public String image_url;
    public String brand;
    public int price;
    public String mobile_link;
    public int available_status;
    public String seller_name;
    public String compressed;

    public MobileCompleteDetails(String ram, String rom, String actual_mobile_name, String image_url, String brand, int price, String mobile_link, int available_status, String seller_name) {
        this.ram = ram;
        this.rom = rom;
        this.actual_mobile_name = actual_mobile_name;
        this.image_url = image_url;
        this.brand = brand;
        this.price = price;
        this.mobile_link = mobile_link;
        this.available_status = available_status;
        this.seller_name = seller_name;
    }

    public MobileCompleteDetails(String ram, String actual_mobile_name, String image_url, String brand, int price, String mobile_link, int available_status, String seller_name) {
        this.ram = ram;
        this.actual_mobile_name = actual_mobile_name;
        this.image_url = image_url;
        this.brand = brand;
        this.price = price;
        this.mobile_link = mobile_link;
        this.available_status = available_status;
        this.seller_name = seller_name;
    }

    @Override
    public String toString() {
        return "MobileCompleteDetails{" +
                "ram='" + ram + '\'' +
                ", rom='" + rom + '\'' +
                ", actual_mobile_name='" + actual_mobile_name + '\'' +
                ", image_url='" + image_url + '\'' +
                ", brand='" + brand + '\'' +
                ", price=" + price +
                ", mobile_link='" + mobile_link + '\'' +
                ", available_status=" + available_status +
                ", seller_name='" + seller_name + '\'' +
                '}';
    }

    // MobileCompleteDetails(String ram,String rom,String actual_mobile_name,String actual_mobile_name,String image_url,String br)
}