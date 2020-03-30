import dao.*;
import model.Availability;
import model.Event;
import model.Person;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.*;

public class WebServer {
  public static void main(String[] args) {

    EventDao eventDao = new UnireastEventDao();
    AvailabilityDao aDao = new UnireastAvailabilityDao();
    PersonDao personDao = new UnireastPersonDao();
    staticFiles.location("/public");
    get("/", (req, res) -> {
      return new ModelAndView(null, "index.hbs");
    }, new HandlebarsTemplateEngine());

    get("/main", (req, res) -> {
      return new ModelAndView(null, "main.hbs");
    }, new HandlebarsTemplateEngine());

    get("/create", (req, res) -> {
      return new ModelAndView(null, "create.hbs");
    }, new HandlebarsTemplateEngine());

//    post("/", (req,res)-> {
//      String username = req.queryParams("username");
//      String password=req.queryParams("password");
//      List<Person> people = personDao.findPerson(username, password);
//      if (people.size() == 0) {
//        res.redirect("/");
//        return null;
//      }
//      res.redirect("/events");
//      return null;
//    }, new HandlebarsTemplateEngine());

    post("/create", ((request, response) -> {
      // TODO Capture client's input
      String eventName = request.queryParams("eventname");
      String location = request.queryParams("location");
      int duration = Integer.parseInt(request.queryParams("duration"));
      // TODO create (and add) a event
      eventDao.add(new Event(duration, eventName, location));
      // TODO refresh create page to show the new addition
      response.redirect("/create");
      return null;
    }), new HandlebarsTemplateEngine());

    get("/events", ((request, response) -> {
      Map<String, Object> model = new HashMap<>();
      model.put("eventList", eventDao.findAllEvents());
      return new ModelAndView(model, "events.hbs");
    }),  new HandlebarsTemplateEngine());

    post("/events", ((request, response) -> {
      Map<String, Object> model = new HashMap<>();
      int eventId = Integer.parseInt(request.queryParams("eId"));
      //call helper function
      int optimalTime = calculateOptimalTime(eventId, eventDao, aDao, personDao);
      model.put("eventList", eventDao.calcOTime(Integer.parseInt(request.queryParams("eId")), aDao.findAvailabilitiesbyEventId(Integer.parseInt(request.queryParams("eId")))));
      return new ModelAndView(model, "events.hbs");
    }), new HandlebarsTemplateEngine());

    get("/register",((request,response)->{
      Map<String, Object> model = new HashMap<>();
      model.put("eventList", eventDao.findAllEvents());
      model.put("AvailList", aDao.findAllAvails());
      return new ModelAndView(model,"register.hbs");
    }),new HandlebarsTemplateEngine());

    post("/register", ((request,response)->{
      int eId=Integer.parseInt(request.queryParams("eId"));
      //int pId=1; //default
      int pId=Integer.parseInt(request.queryParams("pId"));
      int st=Integer.parseInt(request.queryParams("st"));
      int et = Integer.parseInt(request.queryParams("et"));
      int dow = Integer.parseInt(request.queryParams("dow"));
      Availability a = new Availability(eId,pId,st,et,dow);
      aDao.addAvailability(a);
      response.redirect("/register");
      return null;
    }), new HandlebarsTemplateEngine());


  }

  private static int calculateOptimalTime(int eventId, EventDao eDao, AvailabilityDao aDao, PersonDao pDao) {
    List<Event> event = eDao.findEventbyId(eventId);
    List<Availability> availabilities = null;
    Map<Integer, Integer> personIdtoPriority = new HashMap<>();
    Map<Integer, List<Availability>> personIdtoAvailabilities = new HashMap<>();
    if (event.size()!=1) {
      //event does not exist!
      return -1;
    }
    else {
      //event does indeed exist

      availabilities = aDao.findAvailabilitiesbyEventId(eventId);

      if (availabilities.size()==0) {
        //no one has registered for it!!!
      }

      int pId;
      List<Person> p = null;
      Person person = null;
      for (int i = 0; i < availabilities.size(); ++i) {
        pId = availabilities.get(i).getPersonId();
        if (personIdtoPriority.containsKey(new Integer(pId))) {
          // this person is already in our personIdtoPriority so its safe to assume it is also in the personIdtoAvailabilties map
          personIdtoAvailabilities.get(pId).add(availabilities.get(i)); //add this availability to the corresponding person whose availability it is
          continue;
        }
        else {
          // person is not in our map so let's add them by (pId, priority)
          p = pDao.findPersonbyPersonId(pId);
          //ASSUMING THAT pID WILL BE IN THE RESPECTIVE DATABASE
          person = p.get(0);
          personIdtoPriority.put(new Integer(pId),new Integer(person.getPriority()));
          personIdtoAvailabilities.put(new Integer(pId),new ArrayList<Availability>());
          personIdtoAvailabilities.get(pId).add(availabilities.get(i));
        }
      }

      // We have now looped through the availabilities associated with this specific event and extracted the people that
      // have registered for this event and created a map that maps personId to priority
      // Also, we have created a map that maps personId to his or her availabilities for this event


      //Assumption: Professor must be in attendance and is the most important person. So, loop through the map to find
      // the highest priority person (ie lowest priority value)
      int curpId = -1;
      int curHighestPriority = 10000; //intialize to be very high!
      for (Map.Entry<Integer, Integer> entry : personIdtoPriority.entrySet()) {
        if (entry.getValue()< curHighestPriority) {
          curHighestPriority = entry.getValue();
          curpId = entry.getKey();
        }
      }
      int profId = curpId;
      //profId contains the id of the professor
      List<Availability> profAvails = personIdtoAvailabilities.get(profId); //Professors Availabilities
      List<Availability> tempList;
      float cur_Bestscore=-1;
      int best_index=0;
      int st;
      int et;
      int dow;
      int priority;
      float tempScore;
      for (int i = 0; i < profAvails.size(); ++i) {
        // loop thru professors availabilities
        dow = profAvails.get(i).getDow();
        st = profAvails.get(i).getStartTime();
        et = profAvails.get(i).getEndTime();
        tempScore = 0;
        for (Map.Entry<Integer, List<Availability>> entry : personIdtoAvailabilities.entrySet()) {
          //loop through everyone else's availability
          if (entry.getKey().intValue() != profId) {
              tempList = entry.getValue();
              priority = pDao.findPersonbyPersonId(entry.getKey().intValue()).get(0).getPriority();
              for (int j = 0; j < tempList.size(); ++j) {
                if (tempList.get(j).getDow()!=dow) {
                  continue;
                }
                //dow match
                if (tempList.get(j).getStartTime()<= st) {
                  if (tempList.get(j).getEndTime()<=st) {
                    //no overlap
                    continue;
                  }
                  else {
                    //determine which end time is earlier
                    if (tempList.get(j).getEndTime()<=et) {
                      //overlap is [st, tempList(j) end time]
                      tempScore+= (1/priority)*(st-tempList.get(j).getEndTime());
                    }
                    else {
                      //overlap is [st,et]
                      tempScore+=(1/priority)*(st-et);
                    }
                  }
                }
                else if (tempList.get(j).getStartTime() >= et) {
                  continue; //no overlap
                }
                else {
                  // must be some overlap
                  //start in middle of prof's avail so overlap is [tempList(j) st, min(et, tempList(j) et)]
                  if (tempList.get(j).getEndTime()<et) {
                    //overlap is [tempList(j) st, tempList(j) et]
                    tempScore+=(1/priority)*(tempList.get(j).getStartTime()-tempList.get(j).getEndTime());
                  }
                  else {
                    //overlap is [tempList(j) st, et]
                    tempScore+=(1/priority)*(tempList.get(j).getStartTime()-et);
                  }
                }
              }
          } else {
            //entry is at the Professor's own id
            continue;
          }
        }

        if (tempScore>cur_Bestscore) {
          cur_Bestscore=tempScore;
          best_index=i;
        }
      }
      return profAvails.get(best_index).getStartTime();
    }

  }



}