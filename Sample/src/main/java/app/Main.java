package app;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.put;

public class Main {
    public static Gson gson = new Gson();

    public static void main(String[] arg) {
        Map<Integer, List<String>> listMap = new HashMap<>();
        Map<String, Integer> userID = new HashMap<>();
        put("/addTODO", (request, response) -> {
            response.header("Content-Type", "application/json");
            Map<String, Object> requestMap = gson.fromJson(request.body(), Map.class);

            try {
                String user = (String) requestMap.get("user");
                String task = (String) requestMap.get("todo");
                Integer id = userID.get(user);
                if (id == null) id = userID.size();
                userID.put(user, id);

                List<String> list = listMap.get(id);
                if (list == null) list = new ArrayList<>();
                list.add(task);
                listMap.put(id, list);

                response.status(200);
                response.body(gson.toJson(list));
                return gson.toJson(list);
            } catch (Exception e){
                response.status(404);
            } finally {
                response.status();
            }
            return null;
        });

        get("/getTODO", (request, response) -> {
            response.header("Content-Type", "application/json");
            Map<String, Object> requestMap = gson.fromJson(request.body(), Map.class);
            Integer id = Integer.parseInt((String) requestMap.get("id"));
            List<String> tasks = listMap.get(id);
            if (tasks == null) {
                tasks = new ArrayList<>();
                tasks.add("No task");
            }
            response.status(200);
            return gson.toJson(tasks);
        });

        delete("/deleteTODO", (request, response) -> {
            response.header("Content-Type", "application/json");
            Map<String, Object> requestMap = gson.fromJson(request.body(), Map.class);
            Integer id = Integer.parseInt((String) requestMap.get("id"));
            List<String> tasks = listMap.get(id);
            String removeTODOName = (String) requestMap.get("todoName");

            int index = 0;
            int removeIndex = -1;
            for (String currentTODOName : tasks) {
                if (currentTODOName.equals(removeTODOName)) {
                    removeIndex = index;
                }
                ++index;
            }
            if (removeIndex != -1)
                tasks.remove(removeIndex);
            return gson.toJson(tasks);
        });
    }
}