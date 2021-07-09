package bean;

import java.io.Serializable;
import java.util.List;

public final class MobilePhoneBean implements Serializable {
    private String ram;
    private String internalStorage;
    private String color;
    private Integer price;
    private String modelNumber;
    private String modelName;
    private List<String> links;
    private String mobileStoreLink;
    private String brandName;
    private String genericName;
    private String imageURL;
    private boolean availableStatus;
    private boolean isPhone;

    public String getGenericName() {
        return genericName;
    }

    public void setGenericName(String genericName) {
        this.genericName = genericName;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public List<String> getLinks() {
        return links;
    }

    public void setLinks(final List<String> links) {
        this.links = links;
    }

    public String getRam() {
        return ram;
    }

    public void setRam(final String ram) {
        this.ram = ram;
    }

    public String getInternalStorage() {
        return internalStorage;
    }

    public void setInternalStorage(final String internalStorage) {
        this.internalStorage = internalStorage;
    }

    public String getColor() {
        return color;
    }

    public void setColor(final String color) {
        this.color = color;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(final Integer price) {
        this.price = price;
    }

    public String getModelNumber() {
        return modelNumber;
    }

    public void setModelNumber(final String modelNumber) {
        this.modelNumber = modelNumber;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(final String modelName) {
        this.modelName = modelName;
    }

    public boolean getAvailableStatus() {
        return availableStatus;
    }

    public void setAvailableStatus(final boolean availableStatus) {
        this.availableStatus = availableStatus;
    }

    public boolean isPhone() {
        return isPhone;
    }

    public void setPhone(final boolean phone) {
        isPhone = phone;
    }

    @Override
    public String toString() {
        return "MobilePhoneBean{" +
                "ram='" + ram + '\'' +
                ", internalStorage='" + internalStorage + '\'' +
                ", color='" + color + '\'' +
                ", price=" + price +
                ", modelNumber='" + modelNumber + '\'' +
                ", modelName='" + modelName + '\'' +
                ", links=" + links +
                ", mobileStoreLink='" + mobileStoreLink + '\'' +
                ", brandName='" + brandName + '\'' +
                ", genericName='" + genericName + '\'' +
                ", imageURL='" + imageURL + '\'' +
                ", availableStatus=" + availableStatus +
                ", isPhone=" + isPhone +
                '}';
    }

    public String getMobileStoreLink() {
        return mobileStoreLink;
    }

    public void setMobileStoreLink(String mobileStoreLink) {
        this.mobileStoreLink = mobileStoreLink;
    }
}
