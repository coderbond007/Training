package tp;

import java.util.ArrayList;
import java.util.List;

public class LoginPortal {
    public static void main(String[] args) {
        List<Organization> organizations = loadDataBase();

    }

    private static List<Organization> loadDataBase() {
        List<Organization> organizations = new ArrayList<Organization>();
        Organization mediaNet = new Organization("Media.net");
        mediaNet.addManager("Ramesh");

        Organization zeta = new Organization("Zeta");
        return organizations;
    }
}