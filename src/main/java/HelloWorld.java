import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;
import java.util.HashMap;
import java.util.Map;
import static spark.Spark.*;
import static spark.route.HttpMethod.post;

public class HelloWorld {

    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
    }

    public static void main(String[] args) {

        staticFiles.location("/public"); // Static files

        port(getHerokuAssignedPort());

        get("/", (req, res) -> {
            return new ModelAndView(new HashMap<>(), "index.hbs");
        }, new HandlebarsTemplateEngine());

        post("/greet", (req, res) -> {
            String name = req.queryParams("firstName");

            Map<String, String> dataMap = new HashMap<>();
            dataMap.put("name", name);
            return new ModelAndView(dataMap, "hello.hbs");

        }, new HandlebarsTemplateEngine());
    }
}
