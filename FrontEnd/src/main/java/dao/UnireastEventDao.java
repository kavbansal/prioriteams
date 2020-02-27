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
}
