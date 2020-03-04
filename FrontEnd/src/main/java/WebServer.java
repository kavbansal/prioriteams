import dao.*;
import model.Availability;
import model.CourseAssistant;
import model.Event;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.get;
import static spark.Spark.post;

public class WebServer {
  public static void main(String[] args) {

    EventDao eventDao = new UnireastEventDao();
    AvailabilityDao aDao = new UnireastAvailabilityDao();
    CADao caDao = new UnireastCADao();

    get("/", (req, res) -> {
      return new ModelAndView(null, "signin.hbs");
    }, new HandlebarsTemplateEngine());

    post("/", (req,res)-> {

      String username = req.queryParams("username");
      String pw=req.queryParams("pwd");
      List<CourseAssistant> CAs = caDao.findCA(username,pw);
      if (CAs.size()==0) {
        res.redirect("/");
      }
      return null;
    }, new HandlebarsTemplateEngine());
/*
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
*/
    get("/events", ((request, response) -> {
      Map<String, Object> model = new HashMap<>();
      model.put("eventList", eventDao.findAllEvents());
      return new ModelAndView(model, "events.hbs");
    }),  new HandlebarsTemplateEngine());

    get("/register",((request,response)->{
      Map<String, Object> model = new HashMap<>();
      model.put("eventList", eventDao.findAllEvents());
      model.put("AvailList", aDao.findAllAvails());
      return new ModelAndView(model,"register.hbs");
    }),new HandlebarsTemplateEngine());

    post("/register", ((request,response)->{
      int eId=Integer.parseInt(request.queryParams("eId"));
      int pId=1; //default
      int st=Integer.parseInt(request.queryParams("st"));
      int et = Integer.parseInt(request.queryParams("et"));
      int dow = Integer.parseInt(request.queryParams("dow"));
      Availability a = new Availability(eId,pId,st,et,dow);
      aDao.addAvailability(a);
      response.redirect("/register");
      return null;
    }), new HandlebarsTemplateEngine());
  }
}