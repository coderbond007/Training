package clients;

import com.google.gson.Gson;

public class GsonClient {
    private static Gson gson = null;

    public static Gson getClient() {
        if (gson == null) {
            synchronized (Gson.class) {
                if (gson == null) {
                    gson = new Gson();
                }
            }
        }
        return gson;
    }
}
