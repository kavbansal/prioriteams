package api;

import com.google.gson.Gson;
import dao.*;
import exception.ApiError;
import exception.DaoException;
import io.javalin.Javalin;
import io.javalin.plugin.json.JavalinJson;
import model.Availability;
import model.CourseAssistant;
import model.Event;
import model.Professor;
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
        createProfessorsTable(sql2o);
        createAvailabilityTable(sql2o);
        createCAsTable(sql2o);
        EventDao eventDao = getEventDao(sql2o);
        ProfessorDao professorDao = getProfessorDao(sql2o);
        AvailabilityDao availDao = getAvailabilityDao(sql2o);
        CourseAssistantDao caDao = getCADao(sql2o);
        initData(eventDao);
        initData(professorDao);
        initAvails(availDao);
        initCAs(caDao);
        app = startServer();
        app.get("/", ctx -> ctx.result("Welcome to the Lads' App"));
        getEvents(eventDao);
        postEvents(eventDao);
        getAvailabilities(availDao);
        getCA(caDao);
        postAvailabilities(availDao);
        getProfessors(professorDao);
        postProfessors(professorDao);


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

    private static ProfessorDao getProfessorDao(Sql2o sql2o) { return new Sql2oProfessorDao(sql2o); }
    private static EventDao getEventDao(Sql2o sql2o) { return new Sql2oEventDao(sql2o); }
    private static AvailabilityDao getAvailabilityDao(Sql2o sql2o) { return new Sql2oAvailabilityDao(sql2o);}
    private static CourseAssistantDao getCADao(Sql2o sql2o) { return new Sql2oCADao(sql2o);}

    private static void getEvents(EventDao eventDao) {
        app.get("/events", ctx -> {
            List<Event> events = eventDao.findAllEvents();
            ctx.json(events);
            ctx.status(200); // everything ok!
        });
    }
    private static void getProfessors(ProfessorDao pdao) {
        app.get("/professors", ctx -> {
            List<Professor> professors = pdao.findAllProfessors();
            ctx.json(professors);
            ctx.status(200); // everything ok!
        });
    }

    private static void getCA(CourseAssistantDao caDao) {
        app.get("/CourseAssistants/:namePW", ctx-> {
            String namePW = ctx.pathParam("namePW");
            String username=namePW.substring(0,namePW.indexOf(':'));
            String password=namePW.substring(namePW.indexOf(':'));
            List<CourseAssistant> CAs = caDao.findCA(username,password);
            ctx.json(CAs);
            ctx.status(200);
        });
    }


    private static void getAvailabilities(AvailabilityDao aDao) {
        app.get("/availabilities", ctx->{
            List<Availability> avails = aDao.findAllAvails();
            ctx.json(avails);
            ctx.status(200);
        });
    }

    private static void getAvailabilitiesByEventId(AvailabilityDao aDao) {
        app.get("/availabilities/:eventId", ctx->{
            List<Availability> avails = aDao.findAvailabilitiesbyEventId(Integer.parseInt(ctx.pathParam(("eventId"))));
            ctx.json(avails);
            ctx.status(200);
        });
    }

    private static void getAvailabilitiesByPersonId(AvailabilityDao aDao) {
        app.get("/availabilities/:personId", ctx->{
            List<Availability> avails = aDao.findAvailabilitiesbyEventId(Integer.parseInt(ctx.pathParam(("personId"))));
            ctx.json(avails);
            ctx.status(200);
        });
    }

    private static void getAvailabilitiesByEventIdandPersonId(AvailabilityDao aDao) {
        app.get("/availabilities/:eventId/:personId", ctx->{
            List<Availability> avails = aDao.findAvailabilitiesbyPersonandEvent(Integer.parseInt(ctx.pathParam(("eventId"))), Integer.parseInt(ctx.pathParam(("personId"))));
            ctx.json(avails);
            ctx.status(200);
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



    private static void postProfessors(ProfessorDao pdao) {
        // client adds a course through HTTP POST request
        app.post("/professors", ctx -> {
            Professor professor = ctx.bodyAsClass(Professor.class);
            try {
                pdao.add(professor);
                ctx.status(201); // created successfully
                ctx.json(professor);
            } catch (DaoException ex) {
                throw new ApiError(ex.getMessage(), 500); // server internal error
            }
        });
    }

    private static void postAvailabilities(AvailabilityDao aDao) {
        app.post("/availabilities", ctx->{
           Availability availability = ctx.bodyAsClass(Availability.class);
           try {
               aDao.addAvailability(availability);
               ctx.status(201);
               ctx.json(availability);
           } catch (DaoException ex) {
               throw new ApiError(ex.getMessage(),500);
           }
        });
    }

    private static void initData(EventDao eventDao) {
        eventDao.add(new Event(60, "Oose Staff Meeting", "Malone"));
        eventDao.add(new Event(30, "DS Staff Meeting", "Hackerman"));
    }

    private static void initAvails(AvailabilityDao aDao) {
        aDao.addAvailability(new Availability(1,1,9,12,1));
        aDao.addAvailability(new Availability(1,2,10,11,1));
        aDao.addAvailability(new Availability(1,3,9,1,1));
    }



    private static Javalin startServer() {
        Gson gson = new Gson();
        JavalinJson.setFromJsonMapper(gson::fromJson);
        JavalinJson.setToJsonMapper(gson::toJson);
        final int PORT = 7000;
        return Javalin.create().start(PORT);
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

    private static void createProfessorsTable(Sql2o sql2o) {
        dropPersonsTableIfExists(sql2o);
        String sql = "CREATE TABLE IF NOT EXISTS Professors(" +
                "id INTEGER PRIMARY KEY," +
                "name VARCHAR(100) NOT NULL," +
                "email VARCHAR(100) NOT NULL" +
                ");";
        try (Connection conn = sql2o.open()) {
            conn.createQuery(sql).executeUpdate();
        }
    }

    private static void createAvailabilityTable(Sql2o sql2o) {
        dropAvailsTableIfExists(sql2o);
        String sql= "CREATE TABLE IF NOT EXISTS Availabilities(" +
                "id INTEGER PRIMARY KEY," +
                "eventId Integer NOT NULL," +
                "personId Integer NOT NULL," +
                "startTime Integer NOT NULL," +
                "endTime Integer NOT NULL," +
                "dow Integer NOT NULL" +
                ");";
        try (Connection conn = sql2o.open()) {
            conn.createQuery(sql).executeUpdate();
        }
    }

    private static void createCAsTable(Sql2o sql2o) {
        dropCATableIfExists(sql2o);
        String sql="CREATE TABLE IF NOT EXISTS CourseAssistants(" +
                "id Integer Primary Key," +
                "name VARCHAR(100)," +
                "email VARCHAR(100)," +
                "username VARCHAR(100)," +
                "password VARCHAR(100)"+
                ");";
        try (Connection conn = sql2o.open()) {
            conn.createQuery(sql).executeUpdate();
        }
    }

    private static void dropCATableIfExists(Sql2o sql2o) {
        String sql="Drop TABLE if exists CourseAssistants;";
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

    private static void dropPersonsTableIfExists(Sql2o sql2o) {
        String sql = "DROP TABLE IF EXISTS Professors;";
        try (Connection conn = sql2o.open()) {
            conn.createQuery(sql).executeUpdate();
        }
    }

    private static void dropAvailsTableIfExists(Sql2o sql2o) {
        String sql="Drop Table If Exists Availabilities;";
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

    private static void initData(ProfessorDao pdao) {
        pdao.add(new Professor("Sarah More", "smore1@jhu.edu"));
        pdao.add(new Professor("Michael Dinitz", "mdinitz1@jhu.edu"));
    }
    private static void initCAs(CourseAssistantDao caDao) {
        caDao.add(new CourseAssistant("Irfan Jamil","ijamil1@jhu.edu", "ijamil1", "irfan"));
        caDao.add(new CourseAssistant("Vishnu Joshi", "vjoshi1@jhu.edu", "vjoshi6", "vishnu"));
        caDao.add(new CourseAssistant("Ryan Hubley","rhubley1@jhu.edu", "rhubley1", "ryan"));
        caDao.add(new CourseAssistant("Dara Moini", "dmoini1@jhu.edu", "dmoini1", "dara"));
        caDao.add(new CourseAssistant("Kavan Bansal","kbansal1@jhu.edu", "kbansal1", "kavan"));
        caDao.add(new CourseAssistant("Justin Song","LauFalls69@jhu.edu", "jsong1", "justin"));
    }
}
