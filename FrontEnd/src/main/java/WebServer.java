import com.github.jknack.handlebars.Handlebars;
import dao.*;
import model.Availability;
import model.Event;
import model.Person;
import model.Participants;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.*;

import static spark.Spark.*;

public class WebServer {
  public static void main(String[] args) {

    final int PORT = getHerokuAssignedPort();
    port(PORT);

    EventDao eventDao = new UnireastEventDao();
    AvailabilityDao aDao = new UnireastAvailabilityDao();
    PersonDao personDao = new UnireastPersonDao();
    ParticipantsDao participantsDao = new UnireastParticipantsDao();
    staticFiles.location("/public");
    get("/", (req, res) -> {
        Map<String, String> model = new HashMap<>();
        model.put("personid", req.cookie("personid"));
        model.put("priority", req.cookie("priority"));
      return new ModelAndView(null, "index.hbs");
    }, new HandlebarsTemplateEngine());

    get("/createaccount", (req,res)-> {
        return new ModelAndView(null, "createAccount.hbs");
    }, new HandlebarsTemplateEngine());

    post("/createaccount", (req,res)->{
        String name = req.queryParams("personName");
        String username = req.queryParams("personUsername");
        String password = req.queryParams("personPassword");
        String email = req.queryParams("personEmail");
        int priority = Integer.parseInt(req.queryParams("priority"));
        Person p = new Person(name,email,username,password, priority);
        personDao.add(p);
        res.redirect("/");
       return null;
    }, new HandlebarsTemplateEngine());

    get("/main", (req, res) -> {
        Map<String, String> model = new HashMap<>();
        model.put("personid", req.cookie("personid"));
        model.put("priority", req.cookie("priority"));
        List<Person> PersonList = personDao.findPersonbyPersonId(Integer.parseInt(req.cookie("personid")));
        model.put("myName", PersonList.get(0).getName());
      return new ModelAndView(model, "main.hbs");
    }, new HandlebarsTemplateEngine());

    post("/", ((request, response) -> {
          String username = request.queryParams("usernm");
          String password = request.queryParams("passwd");
          System.out.println(username);
          if (username == null || password == null) {
              response.redirect("/");
              return null;
          }
          //int duration = Integer.parseInt(request.queryParams("duration"));
          List<Person> p = personDao.findPerson(username, password);
          if (p == null) {
              response.redirect("/");
          }
          else if (p.size() == 1) {

              response.cookie("personid", Integer.toString(p.get(0).getId()));
              if (p.get(0).getPriority() == 1)
                response.cookie("priority", Integer.toString(p.get(0).getPriority()));
              response.redirect("/main");
          } else {
              response.redirect("/");
          }
          System.out.println(username);
          System.out.println(password);
          return null;
    }), new HandlebarsTemplateEngine());

    get("/create", (req, res) -> {
        Map<String, Object> model = new HashMap<>();
        model.put("priority", req.cookie("priority"));
        model.put("personList", personDao.findAllPeopleExceptProfs());
      return new ModelAndView(model, "create.hbs");
    }, new HandlebarsTemplateEngine());



    post("/create", ((request, response) -> {
      String eventName = request.queryParams("eventname");
      String location = request.queryParams("location");
      int duration = Integer.parseInt(request.queryParams("duration"));
      int eId = eventDao.add(new Event(duration, eventName, location,-1, -1));
      String emailString = request.queryParams("emails");
      Participants participants = new Participants(eId,emailString);
      participantsDao.add(new Participants(eId,emailString));
      response.redirect("/create");
      return null;
    }), new HandlebarsTemplateEngine());

    get("/events", ((request, response) -> {
      Map<String, Object> model = new HashMap<>();
      model.put("priority", request.cookie("priority"));
      model.put("eventList", eventDao.findAllEvents());
      return new ModelAndView(model, "events.hbs");
    }),  new HandlebarsTemplateEngine());

    post("/events", ((request, response) -> {
      Map<String, Object> model = new HashMap<>();
      int eventId = Integer.parseInt(request.queryParams("eId"));
      //call helper function
      int[] optimals = calculateOptimalTime(eventId, eventDao, aDao, personDao);
      eventDao.removeAndUpdateOptTime(eventId, optimals[0], optimals[1]);
      response.redirect("/events");
      return null;
    }), new HandlebarsTemplateEngine());

    get("/register",((request,response)->{
      Map<String, Object> model = new HashMap<>();
      model.put("priority", request.cookie("priority"));
      model.put("eventList", eventDao.findAllEvents());
      model.put("AvailList", aDao.findAllAvails());
      model.put("personList", personDao.findAllPeople());
      return new ModelAndView(model,"register.hbs");
    }),new HandlebarsTemplateEngine());

    get("/register/:id",((request, response) -> {
        Map<String, Object> model = new HashMap<>();
        model.put("priority", request.cookie("priority"));
        model.put("eventList", eventDao.findEventbyId(Integer.parseInt(request.params(":id"))));
        model.put("AvailList", aDao.findAvailabilitiesbyEventId(Integer.parseInt(request.params(":id"))));
        model.put("personList", personDao.findAllPeople());


        List<Availability> thisEventsAvails = aDao.findAvailabilitiesbyEventId(Integer.parseInt(request.params(":id")));
        List<String> printableAvailabilitiesWithNames = new ArrayList<>();


        for(int i = 0; i < thisEventsAvails.size(); i++){
            printableAvailabilitiesWithNames.add(personDao.findPersonbyPersonId(thisEventsAvails.get(i).getPersonId()).get(0).getName()
                  + " can attend from " + convertIntToTimeofDayWithAmPm(thisEventsAvails.get(i).getStartTime()) + " to "
                    + convertIntToTimeofDayWithAmPm(thisEventsAvails.get(i).getEndTime()) + " on " +
                    convertIntToDay(thisEventsAvails.get(i).getDow()) + "s.");
        }

        model.put("printableAvails", printableAvailabilitiesWithNames);


        return new ModelAndView(model, "register1.hbs");
    }), new HandlebarsTemplateEngine());


      post("/specificevent", ((request,response)->{
          int eId=Integer.parseInt(request.queryParams("eId"));

          //int dow = Integer.parseInt(request.queryParams("dow"));

          response.redirect("/register/" + eId);
          return null;
      }), new HandlebarsTemplateEngine());


      post("/register/:id/addnewavail", ((request,response)->{
          //int eId=Integer.parseInt(request.queryParams("eId"));
          int eId=Integer.parseInt(request.params("id"));
          //int pId=Integer.parseInt(request.queryParams("pId"));
          int dow = 1;
          int st = 0;
          int et = 0;
          int curNum = 8;
          String curM = "am";
          String tempString = "";
          String queryString = curNum + curM + dow;
          int temp = 0;

          while (dow <= 7) {
              curNum = 8;
              curM = "am";
              tempString = "";
              queryString = curNum + curM + dow;
              st = 0;
              et = 0;

              while (curNum < 8 || curM.compareTo("am") == 0 || curNum == 12) {

                  queryString = curNum + curM + dow;
                  tempString = request.queryParams(queryString);
                  if (tempString != null) {
                      temp = Integer.parseInt(tempString);
                  } else {
                      temp = 0;
                  }
                  if (temp == 1) {
                      st = curNum;
                      if (st != 12 && curM.compareTo("pm") == 0) {
                          st += 12;
                      }
                      et = curNum + 1;
                      //Find end time for this start time
                      while (temp == 1) {
                          curNum++;
                          if (curNum == 12) {
                              curM = "pm";
                          }
                          if (curNum >= 13) {
                              curNum -= 12;
                          }
                          queryString = curNum + curM + dow;;
                          if (curNum == 8) {
                              break;
                          }
                          tempString = request.queryParams(queryString);
                          if (tempString != null) {
                              temp = Integer.parseInt(tempString);
                          } else {
                              temp = 0;
                          }
                      }
                      et = curNum;
                      if (et != 12 && curM.compareTo("pm") == 0) {
                          et += 12;
                      }
                      Availability a = new Availability(eId, Integer.parseInt(request.cookie("personid")), st, et, dow);
                      aDao.addAvailability(a);
                  }
                  curNum++;
                  if (curNum == 12) {
                      curM = "pm";
                  }
                  if (curNum >= 13) {
                      curNum -= 12;
                  }
              }
              dow++;
          }
          response.redirect("/register/"+eId);
          int[] optimals = calculateOptimalTime(eId, eventDao, aDao, personDao);
          eventDao.removeAndUpdateOptTime(eId, optimals[0], optimals[1]);
          return null;

      }), new HandlebarsTemplateEngine());


      get("/signout", ((request, response) -> {
          response.removeCookie("personid");
          response.removeCookie("priority");
          response.redirect("/");
          return null;
      }), new HandlebarsTemplateEngine());

      get("/adjustPriorities", (req, response)->{
          Map<String, Object> map = new HashMap<>();
          map.put("People", personDao.findAllPeopleExceptProfs());
          return new ModelAndView(map,"adjustPriorities.hbs");
      }, new HandlebarsTemplateEngine());

      post("/adjustPriorities", (req,res)->{
          int pId = Integer.parseInt(req.queryParams("pId"));
          int priority = Integer.parseInt(req.queryParams("priority"));
          personDao.updatePriority(pId, priority);
          res.redirect("/adjustPriorities");
          return null;
      }, new HandlebarsTemplateEngine());

  }

    private static int getHerokuAssignedPort() {
        String herokuPort = System.getenv("PORT");
        if (herokuPort != null) {
            return Integer.parseInt(herokuPort);
        }
        return 4567;
    }

    private static int[] calculateOptimalTime(int eventId, EventDao eDao, AvailabilityDao aDao, PersonDao pDao) {
      //Assumption: Professor has the lowest numeric priority value to signify that he or she is the most
      // important person. The professor needs to be present at the meeting. So, the algorithm is predicated on that assumption.
      int[] returnArray = {-1, -1};
      Event event = eDao.findEventbyId(eventId);
      int eventDuration;
      List<Availability> availabilities = null;
      Map<Integer, Integer> personIdtoPriority = new HashMap<>();
      Map<Integer, List<Availability>> personIdtoAvailabilities = new HashMap<>();
      if (event == null) {
          return returnArray;
      } else {
          //event does indeed exist
          eventDuration = event.getDuration(); //note event duration is taken in with respect to number of minutes the event lasts

          availabilities = aDao.findAvailabilitiesbyEventId(eventId);

          if (availabilities.size() == 0) {
              //no one has registered for it!!!
              return returnArray;
          }

          int pId;
          List<Person> p = null;
          Person person = null;


          //loop through availabilities
          for (int i = 0; i < availabilities.size(); ++i) {
              pId = availabilities.get(i).getPersonId(); //get personId that's associated with each availability

              if (personIdtoPriority.containsKey(new Integer(pId))) {
                  // this person is already in our personIdtoPriority so its safe to assume it is also in the personIdtoAvailabilties map
                  personIdtoAvailabilities.get(pId).add(availabilities.get(i)); //add this availability to the corresponding person whose availability it is
                  continue;
              } else {
                  // person is not in our map so let's add them by (pId, priority)
                  p = pDao.findPersonbyPersonId(pId);
                  //ASSUMING THAT pID WILL BE IN THE RESPECTIVE DATABASE
                  person = p.get(0);
                  personIdtoPriority.put(new Integer(pId), new Integer(person.getPriority()));
                  personIdtoAvailabilities.put(new Integer(pId), new ArrayList<Availability>());
                  personIdtoAvailabilities.get(pId).add(availabilities.get(i));
              }
          }
          // We have now looped through the availabilities associated with this specific event and extracted the people that
          // have registered for this event and created a map that maps personId to priority
          // Also, we have created a map that maps personId to his or her availabilities for this event

          //Assumption: Professor must be in attendance and is the most important person. So, loop through the map to find
          // the highest priority person (ie lowest priority value)
          int curpId = -1;
          int curLowestPriority = 10000; //intialize to be very high!
          for (Map.Entry<Integer, Integer> entry : personIdtoPriority.entrySet()) {
              if (entry.getValue().intValue() < curLowestPriority) {
                  curLowestPriority = entry.getValue().intValue();
                  curpId = entry.getKey().intValue();
              }
          }
          int profId = curpId;
          //profId contains the id of the professor
          List<Availability> profAvails = personIdtoAvailabilities.get(profId); //profAvails contains Professors Availabilities
          List<Availability> tempList;
          float cur_Bestscore = -2;
          int best_index = 0;
          int bestTime = -1;
          int tempBestTime = 0;
          int Profst;
          int Profet;
          int Profdow;
          int latestEventStart;
          int priority;
          float tempAvailScore;
          float tempIntervalScore;
          int bestDOW = -1;

          // note, we  check to see if each of the Professor's availabilities for this event are actually as long as the duration of the event
          //if it is not, we just return a random opt time

          //right now, the availabilities are defined in terms of start time and end time; both of which are integer valued.

          for (int i = 0; i < profAvails.size(); ++i) {
              //loop through all of the Professor's availabilities
              if (pDao.findPersonbyPersonId(profId).get(0).getPriority() != 1) {
                  break;
              }
              tempAvailScore = -1;
              tempBestTime = -1;
              Profst = profAvails.get(i).getStartTime();
              Profet = profAvails.get(i).getEndTime();
              Profdow = profAvails.get(i).getDow();
              if ((Profet - Profst) * 60 < eventDuration) {
                  continue; //in the case that the Prof's availability is not even as long as the event that he/she signed up for
              }
              latestEventStart = Profet - (int) Math.ceil(eventDuration / 60.0);
              for (int j = Profst; j <= latestEventStart; ++j) {
                  //loop thru each of the possible "event duration" sized intervals within the Professor's availability chunk with a stride of 1 hour
                  //evaluating interval [j, j+ceil(eventDuration/60)]
                  tempIntervalScore = 0;
                  for (Map.Entry<Integer, List<Availability>> entry : personIdtoAvailabilities.entrySet()) {
                      //loop thru every person who registered for this event
                      if (entry.getKey().intValue() == profId) {
                          continue;
                      }
                      priority = pDao.findPersonbyPersonId(entry.getKey().intValue()).get(0).getPriority();
                      for (int k = 0; k < entry.getValue().size(); ++k) {
                          //loop thru all of the availabilities for the x person to register for this event
                          //evaluating interval [j, j+ceil(eventDuration/60)]
                          if (entry.getValue().get(k).getDow() != Profdow) {
                              continue; //if the day of the weeks dont match up, skip
                          }
                          if (entry.getValue().get(k).getStartTime() <= j) {
                              if (entry.getValue().get(k).getEndTime() >= (int) j + Math.ceil(eventDuration / 60.0)) {
                                  //then this person is available for the full length of this interval
                                  tempIntervalScore += (float) (100.0 / priority);
                              }
                          }
                      }
                  }

                  if ((tempIntervalScore > tempAvailScore) && (tempIntervalScore != 0)) {
                      tempAvailScore = tempIntervalScore;
                      tempBestTime = j;
                  }
              }
              if (tempAvailScore > cur_Bestscore) {
                  cur_Bestscore = tempAvailScore;
                  bestTime = tempBestTime;
                  bestDOW = Profdow;
              }
          }

          int tempTime = 0;
          double tempScore;
          double maxScore = 0;
          int maxTime = 0;
          int maxDow = 0;

          List<int[]> sorted_stDow = new ArrayList<>();
          List<int[]> sorted_etDow = new ArrayList<>();
          int[] tempAr = new int[]{0, 0};
          int stIdx = 0;
          int etIdx = 0;
          int it;
          List<Integer> intervalAvailIdxs = new ArrayList<>();


          if (bestTime == -1) {
              // there exist no overlapping availabilities with that of the Professor's
              //DEALS WITH NO OVERLAPPING AVAILABILITIES WITH THAT OF PROFESSOR'S by finding the time and day of the week in which the most
              //people can meet at once
              for (int i = 1; i <= 7; ++i) {
                  //loop thru days of the week
                  tempScore = 0;
                  sorted_stDow.clear();
                  sorted_etDow.clear();

                  for (int j = 0; j < availabilities.size(); ++j) {
                      //loop thru availabilities and add start times and end times of the availabilities associated to this specific dow
                      if (availabilities.get(j).getDow() == i) {
                          sorted_stDow.add(new int[]{availabilities.get(j).getStartTime(), j});
                          sorted_etDow.add(new int[]{availabilities.get(j).getEndTime(), j});

                      }
                  }
                  if (sorted_stDow.size() == 0) {
                      continue;
                  }
                  //sorted_stDow int[start time, availabilties index]
                  for (int j = 1; j < sorted_stDow.size(); ++j) {
                      it = j;
                      while ((it > 0) && (sorted_stDow.get(it)[0] < sorted_stDow.get(it - 1)[0])) {
                          tempAr[0] = sorted_stDow.get(it - 1)[0];
                          tempAr[1] = sorted_stDow.get(it - 1)[1];
                          sorted_stDow.get(it - 1)[0] = sorted_stDow.get(it)[0];
                          sorted_stDow.get(it - 1)[1] = sorted_stDow.get(it)[1];
                          --it;
                          sorted_stDow.get(it + 1)[0] = tempAr[0];
                          sorted_stDow.get(it + 1)[1] = tempAr[1];
                      }
                  }

                  for (int j = 1; j < sorted_etDow.size(); ++j) {
                      it = j;
                      while ((it > 0) && (sorted_etDow.get(it)[0] < sorted_etDow.get(it - 1)[0])) {
                          tempAr[0] = sorted_etDow.get(it - 1)[0];
                          tempAr[1] = sorted_etDow.get(it - 1)[1];
                          sorted_etDow.get(it - 1)[0] = sorted_etDow.get(it)[0];
                          sorted_etDow.get(it - 1)[1] = sorted_etDow.get(it)[1];
                          --it;
                          sorted_etDow.get(it + 1)[0] = tempAr[0];
                          sorted_etDow.get(it + 1)[1] = tempAr[1];
                      }
                  }


                  //Now, sorted_stDow and sorted_etDow are sorted array lists w.r.t start time/end times where each index contains an array of size 1
                  //with the first element in the array being the start/end time and the second element is the index in the availability array list that
                  //it corresponds to


                  stIdx = 0;
                  etIdx = 0;
                  intervalAvailIdxs.clear();
                  intervalAvailIdxs.add(sorted_stDow.get(0)[1]);
                  tempScore = calculateMaxPeopleDividedByAveragePriority(pDao, availabilities, intervalAvailIdxs);


                    while (stIdx < sorted_stDow.size()-1) {
                        if (sorted_stDow.get(stIdx+1)[0]<sorted_etDow.get(etIdx)[0]) {
                            ++stIdx;
                            intervalAvailIdxs.add(sorted_stDow.get(stIdx)[1]);
                            tempScore=calculateMaxPeopleDividedByAveragePriority(pDao,availabilities,intervalAvailIdxs);
                            if (tempScore > maxScore) {
                                maxScore = tempScore;
                                maxTime = sorted_stDow.get(stIdx)[0];
                                maxDow = i;
                            }
                        }
                        else {
                            //
                            intervalAvailIdxs = removeAvailIdx(intervalAvailIdxs,sorted_etDow.get(etIdx)[1]);
                            ++etIdx;

                        }
                    }
                }

              if (maxTime==0) {
                  //loop through and just return a Prof's time
                  for (int k=0; k < availabilities.size();++k) {
                      if (personIdtoPriority.get(availabilities.get(k).getPersonId())==1) {
                          returnArray[0] = availabilities.get(k).getStartTime();
                          returnArray[1] = availabilities.get(k).getDow();
                          return returnArray;
                      }
                  }
                  //So no has an overlapping time with anyone else, and there is no Prof available for this event if we get to the following
                  //block so let's find the person with the next lowest priority and just return their first availability and dow submission

                  curLowestPriority=10000;
                  curpId=0;
                  for (int k = 0; k < availabilities.size();++k) {
                      if (personIdtoPriority.get(availabilities.get(k).getPersonId())<curLowestPriority) {
                          curLowestPriority=personIdtoPriority.get(availabilities.get(k).getPersonId());
                          curpId=availabilities.get(k).getPersonId();
                      }
                  }
                  returnArray[0] = personIdtoAvailabilities.get(curpId).get(0).getStartTime();
                  returnArray[1] = personIdtoAvailabilities.get(curpId).get(0).getDow();
                  return returnArray;

              }
                returnArray[0] = maxTime;
                returnArray[1] = maxDow;
                return returnArray;
            }
            returnArray[0] = bestTime;
            returnArray[1] = bestDOW;

/*
      for (int i = 0; i < profAvails.size(); ++i) {
        // loop thru professors availabilities
        dow = profAvails.get(i).getDow();
        st = profAvails.get(i).getStartTime();
        et = profAvails.get(i).getEndTime();
        tempScore = 0;
        for (Map.Entry<Integer, List<Availability>> entry : personIdtoAvailabilities.entrySet()) {
          //loop through everyone's availability (that is not the Prof)
          if (entry.getKey().intValue() != profId) {
              tempList = entry.getValue(); //tempList contains the availabilities of this person for this event
              priority = pDao.findPersonbyPersonId(entry.getKey().intValue()).get(0).getPriority(); //the priority associated with this person
              for (int j = 0; j < tempList.size(); ++j) { //loop thru this persons availabilities
                if (tempList.get(j).getDow()!=dow) {
                  continue; // skip if this specific availability is not on the same day as the Professor's
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
                      tempScore+= (1/priority)*(tempList.get(j).getEndTime()-st);
                    }
                    else {
                      //overlap is [st,et]
                      tempScore+=(1/priority)*(et-st);
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
                    tempScore+=(1/priority)*(tempList.get(j).getEndTime()-tempList.get(j).getStartTime());
                  }
                  else {
                    //overlap is [tempList(j) st, et]
                    tempScore+=(1/priority)*(et-tempList.get(j).getStartTime());
                  }
                }
              }
          } else {
            //entry is at the Professor's own id
            continue;
          }
        }
        //at the end of this for loop, temp score should be the sum of the following terms: each persons's overlapping availability
        // with the Professor's availability weighted by 1/priority

        if (tempScore>cur_Bestscore) {
          cur_Bestscore=tempScore;
          best_index=i;
        }

        List<Availability> sameDayAvails = new ArrayList<>();
        for (Map.Entry<Integer,List<Availability>> entry : personIdtoAvailabilities.entrySet()) {
          // loop through entries of map
          if (entry.getKey()==profId) {
            continue;
          }
          for (int j = 0; j < entry.getValue().size(); ++j) {
            if (entry.getValue().get(j).getDow() == dow) {
              sameDayAvails.add(entry.getValue().get(j));
            }
          }
        }
        if (sameDayAvails.size()==0) {
          //other people are not available on this day
          continue;
        }
        ArrayList<Integer> sorted_st = new ArrayList<>();
        ArrayList<Integer> sorted_et = new ArrayList<>();
        ArrayList<int[]> overlappingIntervalsWithProf = new ArrayList<>();
        for (int j = 0; j < sameDayAvails.size();++j) {
          //note these availabilities are same dow
          if (sameDayAvails.get(j).getStartTime()<= st) {
            //person is available at least as early as prof
            if (sameDayAvails.get(j).getEndTime()<=st) {
              //no overlap
            }
            else if (sameDayAvails.get(j).getEndTime()<=et) {
              //overlap is [st,  persons et]
              overlappingIntervalsWithProf.add(new int[] {st, sameDayAvails.get(j).getStartTime()});
            }
            else {
              //overlap is [st, et]
              overlappingIntervalsWithProf.add(new int[] {st,et});
            }

          }
          else if (sameDayAvails.get(j).getStartTime() >= et) {
            //no overlap; person is available after professors availability
          }
          else {
            //overlap
            //overlap is [persons st, min (persons et, profs et)]
            if (sameDayAvails.get(j).getEndTime() < et) {
              overlappingIntervalsWithProf.add(new int[] {sameDayAvails.get(j).getStartTime(),sameDayAvails.get(j).getEndTime()});
            }
            else {
              overlappingIntervalsWithProf.add(new int[] {sameDayAvails.get(j).getStartTime(),et});
            }
          }
        }
        //populate arraylist with start times and end times
        for (int j = 0; j < overlappingIntervalsWithProf.size();++j) {
          sorted_st.add(new Integer(overlappingIntervalsWithProf.get(j)[0]));
          sorted_et.add(new Integer(overlappingIntervalsWithProf.get(j)[1]));
        }
        Collections.sort(sorted_st);
        Collections.sort(sorted_et);

        //sorted_st and sorted_et now contain, respectively, the sorted start times and sorted end times derived from
        // the set of intervals that represent each student's overlap with the professors availability

        //implement the algorithm to find max overlap

        int bestTime = sorted_st.get(0).intValue();
        int curIdxSt=0;
        int curIdxEt=0;
        int curTotal = 1;
        int bestTotal = 1;
        while (curIdxSt < sorted_st.size()-1) {
          if (sorted_st.get(curIdxSt+1)<sorted_et.get(curIdxEt)) {
            //next person arrives before an exit
            ++curTotal;
            if (curTotal > bestTotal) {
              bestTotal = curTotal;
              bestTime = sorted_st.get(curIdxSt + 1);
            }
            ++curIdxSt;
          }
          else {
            //next person arrives after an exit
            --curTotal;
            ++curIdxEt;
          }

        }
          tempScore= (float) ((0.2 * tempScore) + (0.8 * bestTotal));
          //tempScore is weighted sum
          //first term: 0.2 * (sum of each's person's overall overlap with Prof times 1/priority) + (0.8 * total number people
          //that can meet at once)



        if (tempScore>cur_Bestscore) {
          cur_Bestscore=tempScore;
          best_index=i;
        }


      }



      return profAvails.get(best_index).getStartTime();
*/

              }
      return returnArray;

  }


  private static double calculateMaxPeopleDividedByAveragePriority(PersonDao pDao, List<Availability> avails, List<Integer> intervalAvailIdxs) {
      double numPeople = (double)intervalAvailIdxs.size();
      double cumalativePriority = 0;
      for (int i = 0; i < intervalAvailIdxs.size();++i) {
          cumalativePriority+=pDao.findPersonbyPersonId(avails.get(intervalAvailIdxs.get(i)).getPersonId()).get(0).getPriority();
      }
      double avgPriority = cumalativePriority/numPeople;
      return (numPeople/avgPriority);

  }

  private static List<Integer> removeAvailIdx(List<Integer> intervalAvailIdxs, int idx2remove) {
      boolean iterate = true;
      int i = 0;
      while ((iterate) && (i < intervalAvailIdxs.size())) {
          if (intervalAvailIdxs.get(i).intValue()==idx2remove) {
              intervalAvailIdxs.remove(i);
              iterate=false;
          }
      }
      return intervalAvailIdxs;
  }

    private static String convertIntToDay(int i) {
      switch (i) {
          case 1:
              return "Monday";
          case 2:
              return "Tuesday";
          case 3:
              return "Wednesday";
          case 4:
              return "Thursday";
          case 5:
              return "Friday";
          case 6:
              return "Saturday";
          case 7:
              return "Sunday";
      }
        return " ";
    }

    private static String convertIntToTimeofDayWithAmPm(int time){
      if(time < 12){
          return Integer.toString(time) + "am";
      } else if(time == 12){
          return Integer.toString(time) + "pm";
      }

      return Integer.toString(time - 12) + "pm";
    }



}