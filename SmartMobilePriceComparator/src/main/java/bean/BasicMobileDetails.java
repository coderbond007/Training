package bean;

public class BasicMobileDetails {
    private String name;
    private long id;
    private String brandName;
    private String color;

    public BasicMobileDetails(String name, long id, String brandName, String color) {
        this.name = name;
        this.id = id;
        this.brandName = brandName;
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }
}