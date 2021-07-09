package tatacliq;

import java.io.Serializable;
import java.util.List;

class ResponseParser {
    List<SearchEntry> searchresult;

    static class SearchEntry implements Serializable {
        String productname;
        String webURL;
        ProductPrice price;
        String imageURL;
        String brandname;
        boolean cumulativeStockLevel;
        String productCategoryType;

        @Override
        public String toString() {
            return "SearchEntry{" +
                    "productname='" + productname + '\'' +
                    ", webURL='" + webURL + '\'' +
                    ", price=" + price +
                    ", imageURL='" + imageURL + '\'' +
                    ", brandname='" + brandname + '\'' +
                    ", cumulativeStockLevel=" + cumulativeStockLevel +
                    ", productCategoryType='" + productCategoryType + '\'' +
                    '}';
        }
    }

    static class ProductPrice implements Serializable {
        PriceDetails mrpPrice;
        PriceDetails sellingPrice;

        @Override
        public String toString() {
            return "ProductPrice{" +
                    "mrpPrice=" + mrpPrice +
                    ", sellingPrice=" + sellingPrice +
                    '}';
        }
    }

    static class PriceDetails implements Serializable {
        String currencyIso;
        String doubleValue;
        String formattedValueNoDecimal;
        String formattedValue;

        @Override
        public String toString() {
            return "PriceDetails{" +
                    "currencyIso='" + currencyIso + '\'' +
                    ", doubleValue='" + doubleValue + '\'' +
                    ", formattedValueNoDecimal='" + formattedValueNoDecimal + '\'' +
                    ", formattedValue='" + formattedValue + '\'' +
                    '}';
        }
    }
}
