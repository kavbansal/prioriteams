package api;

import com.google.gson.Gson;
import dao.EventDao;
import dao.Sql2oEventDao;
import exception.ApiError;
import exception.DaoException;
import io.javalin.Javalin;
import io.javalin.plugin.json.JavalinJson;
import model.Event;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApiServer {

    private static Javalin app;

    public static void main(String[] args) {
        Sql2o sql2o = getSql2o();
        createEventTable(sql2o);
        EventDao eventDao = getEventDao(sql2o);
        initData(eventDao);

        app = startServer();
        app.get("/", ctx -> ctx.result("Welcome to the Lads' App"));
        getEvents(eventDao);
        postEvents(eventDao);

        app.exception(ApiError.class, (exception, ctx) -> {
            ApiError err = (ApiError) exception;
            Map<String, Object> jsonMap = new HashMap<>();
            jsonMap.put("status", err.getStatus());
            jsonMap.put("errorMessage", err.getMessage());
            ctx.status(err.getStatus());
            ctx.json(jsonMap);
        });

        // runs after every request (even if an exception occurred)
        app.after(ctx -> {
            // run after all requests
            ctx.contentType("application/json");
        });
    }

    private static void getEvents(EventDao eventDao) {
        app.get("/events", ctx -> {
            List<Event> events = eventDao.findAllEvents();
            ctx.json(events);
            ctx.status(200); // everything ok!
        });
    }

    private static void postEvents(EventDao eventDao) {
        // client adds a course through HTTP POST request
        app.post("/events", ctx -> {
            Event event = ctx.bodyAsClass(Event.class);
            try {
                eventDao.add(event);
                ctx.status(201); // created successfully
                ctx.json(event);
            } catch (DaoException ex) {
                throw new ApiError(ex.getMessage(), 500); // server internal error
            }
        });
    }

    private static void initData(EventDao eventDao) {
        eventDao.add(new Event(60, "Oose Staff Meeting", "Malone"));
        eventDao.add(new Event(30, "DS Staff Meeting", "Hackerman"));
    }

    private static Javalin startServer() {
        Gson gson = new Gson();
        JavalinJson.setFromJsonMapper(gson::fromJson);
        JavalinJson.setToJsonMapper(gson::toJson);
        final int PORT = 7000;
        return Javalin.create().start(PORT);
    }

    private static EventDao getEventDao(Sql2o sql2o) {
        return new Sql2oEventDao(sql2o);
    }

    private static void createEventTable(Sql2o sql2o) {
        dropEventsTableIfExists(sql2o);
        String sql = "CREATE TABLE IF NOT EXISTS Events(" +
                "id INTEGER PRIMARY KEY," +
                "duration INTEGER," +
                "eventName VARCHAR(100) NOT NULL," +
                "location VARCHAR(100)" +
                ");";
        try (Connection conn = sql2o.open()) {
            conn.createQuery(sql).executeUpdate();
        }
    }

    private static void dropEventsTableIfExists(Sql2o sql2o) {
        String sql = "DROP TABLE IF EXISTS Events;";
        try (Connection conn = sql2o.open()) {
            conn.createQuery(sql).executeUpdate();
        }
    }

    private static Sql2o getSql2o() {
        final String URI = "jdbc:sqlite:./Store.db";
        final String USERNAME = "";
        final String PASSWORD = "";
        return new Sql2o(URI, USERNAME, PASSWORD);
    }
}
