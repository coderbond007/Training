package Bean;

import java.util.List;

public class SiteDataParsed {
    private String json;
    private List<String> links;

    public List<String> getLinks() {
        return links;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public void setLinks(List<String> links) {
        this.links = links;
    }
}
