package dao;

import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import model.Event;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UnireastEventDao implements EventDao {

    public final static String BASE_URL = "http://127.0.0.1:7000/";
    private static Gson gson = new Gson();

    @Override
    public int add(Event event) { ;
        final String URL = BASE_URL + "events";
        HttpResponse<JsonNode> jsonResponse = null;
        try {
            jsonResponse=Unirest.post(URL).body(gson.toJson(event)).asJson();
            Event e = gson.fromJson(jsonResponse.getBody().toString(), Event.class);
            return e.getId();
        } catch (UnirestException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public Event findEventbyId(int id) {
        final String URL = BASE_URL + "events/" + id;
        HttpResponse<JsonNode> jsonResponse = null;
        try {
            jsonResponse = Unirest.get(URL).asJson();
            Event[] events = gson.fromJson(jsonResponse.getBody().toString(), Event[].class);
            ArrayList<Event> EventList = new ArrayList<>(Arrays.asList(events));
            if (EventList.size() == 0) {
                return null;
            }
            return EventList.get(0);
        }
        catch (UnirestException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public List<Event> findAllEvents() {
        final String URL = BASE_URL + "events";
        HttpResponse<JsonNode> jsonResponse = null;
        try {
            jsonResponse = Unirest.get(URL).asJson();
            Event[] events = gson.fromJson(jsonResponse.getBody().toString(), Event[].class);
            return new ArrayList<>(Arrays.asList(events));
        } catch (UnirestException e) {
            // TODO Error
            e.printStackTrace();
        }

        return null;
    }



    @Override
    public List<Event> removeAndUpdateOptTime(int eventId, int optTime, int optDay) {
        final String URL = BASE_URL + "events/" + eventId + "/" + optTime + "/" + optDay;
        HttpResponse<JsonNode> jsonResponse = null;
        try {
            jsonResponse = Unirest.get(URL).asJson();
            Event[] events = gson.fromJson(jsonResponse.getBody().toString(), Event[].class);
            return new ArrayList<>(Arrays.asList(events));
        } catch (UnirestException e) {
            // TODO error
            e.printStackTrace();
        }
        return null;
    }




/*
    @Override
    public List<Event> calcOTime(int id, List<Availability> aList) {
        final String URL = BASE_URL + "events";
        List<Event> eList = null;
        HttpResponse<JsonNode> jsonResponse = null;
        try {
            jsonResponse = Unirest.get(URL).asJson();
            Event[] events = gson.fromJson(jsonResponse.getBody().toString(), Event[].class);
            eList = new ArrayList<>(Arrays.asList(events));
        } catch (UnirestException e) {
            // TODO Error
            e.printStackTrace();
        }
        if (eList == null) {
            return null;
        }

        Event event = null;
        for (Event e: eList) {
            if (e.getId() == id) {
                event = e;
            }
        }
        if (event == null) {
            return eList;
        }
        // event variable holds the event for which we want to calculate the optimal time
        // aList is a list of availabilities associated with that event

        Random rand = new Random();
        if (aList.size() == 0) {
            return eList;
        }
        //Write method to find availability by associated persons priority, temporarily use this by selecting
        //a person in priority 1's time as bestAvailability. Later use this in algorithm.








        Availability bestAvailability = aList.get(rand.nextInt(aList.size()));

        event.setOptimalTime(bestAvailability.getStartTime());

        return eList;
    }

 */
}
