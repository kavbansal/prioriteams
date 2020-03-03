import dao.EventDao;
import dao.UnireastEventDao;
import model.Availability;
import model.Event;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.get;
import static spark.Spark.post;

public class WebServer {
  public static void main(String[] args) {

    EventDao eventDao = new UnireastEventDao();

    get("/", (req, res) -> {
      return new ModelAndView(null, "create.hbs");
    }, new HandlebarsTemplateEngine());

    post("/", ((request, response) -> {
      // TODO Capture client's input
      String eventName = request.queryParams("eventname");
      String location = request.queryParams("location");
      int duration = Integer.parseInt(request.queryParams("duration"));
      // TODO create (and add) a event
      eventDao.add(new Event(duration, eventName, location));
      // TODO refresh create page to show the new addition
      response.redirect("/");
      return null;
    }), new HandlebarsTemplateEngine());

    get("/events", ((request, response) -> {
      Map<String, Object> model = new HashMap<>();
      model.put("eventList", eventDao.findAllEvents());
      return new ModelAndView(model, "events.hbs");
    }),  new HandlebarsTemplateEngine());

    get("/register",((request,response)->{
      Map<String, Object> model = new HashMap<>();
      model.put("eventList", eventDao.findAllEvents());
      return new ModelAndView(model,"register.hbs");
    }),new HandlebarsTemplateEngine());

    post("/register", ((request,response)->{
      int eId=Integer.parseInt(request.queryParams("eId"));
      int pId=1; //default
      int st=Integer.parseInt(request.queryParams("st"));
      int et = Integer.parseInt(request.queryParams("et"));
      int dow = Integer.parseInt(request.queryParams("dow"));
      Availability a = new Availability(eId,pId,st,et,dow);

      response.redirect("/register");
      return null;
    }), new HandlebarsTemplateEngine());
  }
}