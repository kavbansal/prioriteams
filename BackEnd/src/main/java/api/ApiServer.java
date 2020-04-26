package api;

import com.google.gson.Gson;
import dao.*;
import exception.ApiError;
import exception.DaoException;
import io.javalin.Javalin;
import io.javalin.plugin.json.JavalinJson;
import model.*;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApiServer {

    private static Javalin app;

    public static void main(String[] args) {

        app = startServer();

        Sql2o sql2o = getSql2o();
        createEventTable(sql2o);
        createAvailabilityTable(sql2o);
        createPeopleTable(sql2o);
        EventDao eventDao = getEventDao(sql2o);
        AvailabilityDao availDao = getAvailabilityDao(sql2o);
        PersonDao personDao = getPersonDao(sql2o);
        initData(eventDao);
        initAvails(availDao);
        initPeople(personDao);
        app.get("/", ctx -> ctx.result("Welcome to the Lads' App"));
        getEvents(eventDao);
        getEventbyId(eventDao);
        getUpdate(eventDao);
        postEvents(eventDao);
        postPeople(personDao);
        updatePriority(personDao);
        getAvailabilities(availDao);
        getAllPeople(personDao);
        getPersonbyPersonId(personDao);
        getProfessors(personDao);
        getPerson(personDao);
        getNonProfs(personDao);
        getAvailabilitiesByEventId(availDao);
        //getCA(caDao);
        postAvailabilities(availDao);


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

    private static int getHerokuAssignedPort() {
        String herokuPort = System.getenv("PORT");
        if (herokuPort != null) {
            return Integer.parseInt(herokuPort);
        }
        return 7000;
    }

    private static EventDao getEventDao(Sql2o sql2o) { return new Sql2oEventDao(sql2o); }
    private static AvailabilityDao getAvailabilityDao(Sql2o sql2o) { return new Sql2oAvailabilityDao(sql2o);}
    private static PersonDao getPersonDao(Sql2o sql2o) { return new Sql2oPersonDao(sql2o);}

    private static void getEvents(EventDao eventDao) {
        app.get("/events", ctx -> {
            List<Event> events = eventDao.findAllEvents();
            ctx.json(events);
            ctx.status(200); // everything ok!
        });
    }

    private static void getEventbyId(EventDao eventDao) {
        app.get("/events/:id", ctx -> {
            List<Event> event = eventDao.findEventbyId(Integer.parseInt(ctx.pathParam("id")));
            ctx.json(event);
            ctx.status(200);
        });
    }

    private static void getNonProfs(PersonDao personDao) {
        app.get("/nonProfs", ctx->{
            List<Person> people = personDao.findAllNonProfs();
            ctx.json(people);
            ctx.status(200);
        });
    }

    private static void getAllPeople(PersonDao personDao) {
        app.get("/People",ctx->{
            List<Person> persons= personDao.findAllPeople();
            ctx.json(persons);
            ctx.status(200);
        });
    }
    private static void postPeople(PersonDao pdao) {
        // client adds a course through HTTP POST request
        app.post("/Person", ctx -> {
            Person person = ctx.bodyAsClass(Person.class);
            try {
                pdao.add(person);
                ctx.status(201); // created successfully
                ctx.json(person);
            } catch (DaoException ex) {
                throw new ApiError(ex.getMessage(), 500); // server internal error
            }
        });
    }


    private static void getProfessors(PersonDao pdao) {
        app.get("/professors", ctx -> {
            List<Person> professors = pdao.findAllProfessors();
            ctx.json(professors);
            ctx.status(200); // everything ok!
        });
    }

    private static void getPerson(PersonDao pDao) {
        app.get("/Person/:username/:password", ctx-> {
            String username = ctx.pathParam("username");
            String password=ctx.pathParam("password");
            List<Person> person = pDao.findPerson(username,password);
            ctx.json(person);
            ctx.status(200);
        });
    }

    private static void getPersonbyUsername(PersonDao pDao) {
        app.get("/Person/:username",ctx->{
           String username=ctx.pathParam("username");
           List<Person> person = pDao.findPersonbyUsername(username);
           ctx.json(person);
           ctx.status(200);
        });
    }

    private static void getPersonbyPersonId(PersonDao pDao) {
        app.get("/Person/:pId",ctx->{
           int pId = Integer.parseInt(ctx.pathParam("pId"));
           List<Person> person = pDao.findPersonbyPersonId(pId);
           ctx.json(person);
           ctx.status(200);
        });
    }

    private static void updatePriority(PersonDao personDao) {
        app.post("Person/:pId/:priority", ctx->{
           int pId = Integer.parseInt(ctx.pathParam("pId"));
           int priority = Integer.parseInt(ctx.pathParam("priority"));
           personDao.updatePriority(pId,priority);
           ctx.status(201);
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

    private static void getUpdate(EventDao eventDao) {
        app.get("/events/:eId/:optTime/:optDay",ctx->{
            int eId = Integer.parseInt(ctx.pathParam("eId"));
            int optTime = Integer.parseInt(ctx.pathParam("optTime"));
            int optDay = Integer.parseInt(ctx.pathParam("optDay"));
            eventDao.update(optTime, eId, optDay);
            List<Event> temp = eventDao.findEventbyId(eId);
            ctx.json(temp);
            ctx.contentType("application/json");
            ctx.status(200);
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
        eventDao.add(new Event(60, "Oose Staff Meeting", "Malone",-1, -1));
        eventDao.add(new Event(30, "DS Staff Meeting", "Hackerman",-1, -1));
    }

    private static void initAvails(AvailabilityDao aDao) {
        aDao.addAvailability(new Availability(1,1,9,12,1));
        aDao.addAvailability(new Availability(1,2,10,11,1));
        aDao.addAvailability(new Availability(1,3,9,1,1));
        aDao.addAvailability((new Availability(2, 4, 10, 11, 2)));
    }

    private static Javalin startServer() {
        Gson gson = new Gson();
        JavalinJson.setFromJsonMapper(gson::fromJson);
        JavalinJson.setToJsonMapper(gson::toJson);
        final int PORT = getHerokuAssignedPort();
        return Javalin.create().start(PORT);
    }

    private static void createEventTable(Sql2o sql2o) {
        dropEventsTableIfExists(sql2o);
        String sql = "CREATE TABLE IF NOT EXISTS Events(" +
                "id INTEGER PRIMARY KEY," +
                "duration INTEGER," +
                "eventName VARCHAR(100) NOT NULL," +
                "location VARCHAR(100)," +
                "optimalTime INTEGER, " +
                "optimalDay INTEGER);";
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

    private static void createPeopleTable(Sql2o sql2o) {
        dropPersonsTableIfExists(sql2o);
        String sql="CREATE TABLE IF NOT EXISTS People(" +
                "id Integer Primary Key," +
                "name VARCHAR(100)," +
                "email VARCHAR(100)," +
                "username VARCHAR(100)," +
                "password VARCHAR(100),"+
                "priority Integer" +
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
        String sql = "DROP TABLE IF EXISTS People;";
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


    private static void initPeople(PersonDao personDao) {
        personDao.add(new Person("Prof Madooei", "madooei@jhu.edu","amadooei", "ali", 1));
        personDao.add(new Person("Yash", "yash@jhu.edu", "yash", "kumar",2));
        personDao.add(new Person("Adeshola", "adeshola@jhu.edu", "adeshola1", "adesholaJHU", 2));
        personDao.add(new Person("Shreya", "shreya@jhu.edu", "shreya123", "shreya_hopkins",2));
        personDao.add(new Person("Irfan Jamil","ijamil1@jhu.edu", "ijamil1", "irfan",3));
        personDao.add(new Person("Vishnu Joshi", "vjoshi1@jhu.edu", "vjoshi6", "vishnu",3));
        personDao.add(new Person("Ryan Hubley","rhubley1@jhu.edu", "rhubley1", "ryan",3));
        personDao.add(new Person("Dara Moini", "dmoini1@jhu.edu", "dmoini1", "dara",3));
        personDao.add(new Person("Kavan Bansal","kbansal2@jhu.edu", "kbansal2", "kavan",3));
        personDao.add(new Person("Justin Song","LauFalls69@jhu.edu", "jsong1", "justin",3));
    }
}
