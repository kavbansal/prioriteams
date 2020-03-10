package dao;

import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import model.Availability;
import model.Event;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class UnireastEventDao implements EventDao {

    public final static String BASE_URL = "http://127.0.0.1:7000/";
    private static Gson gson = new Gson();

    @Override
    public void add(Event event) { ;
        final String URL = BASE_URL + "events";
        try {
            Unirest.post(URL).body(gson.toJson(event)).asJson();
        } catch (UnirestException e) {
            // TODO error
            e.printStackTrace();
        }
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
            return null;
        }

        Random rand = new Random();
        Availability bestAvailability = aList.get(rand.nextInt(aList.size()));

        event.setOptimalTime(bestAvailability.getStartTime());

        return eList;
    }
}
