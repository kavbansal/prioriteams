package dao;

import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import exception.DaoException;
import model.Availability;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UnireastAvailabilityDao implements AvailabilityDao {

    public final static String BASE_URL = "http://127.0.0.1:7000/";
    private static Gson gson = new Gson();

    @Override
    public void addAvailability(Availability a) throws DaoException {
        final String URL = BASE_URL + "availabilities";
        try {
            Unirest.post(URL).body(gson.toJson(a)).asJson();
        } catch (UnirestException e) {
            // TODO error
            e.printStackTrace();
        }
    }

    @Override
    public List<Availability> findAvailabilitiesbyEventId(int eventId) {
        final String URL = BASE_URL + "availabilities/" + eventId;
        HttpResponse<JsonNode> jsonResponse = null;
        try {
            jsonResponse = Unirest.get(URL).asJson();
            Availability[] avails = gson.fromJson(jsonResponse.getBody().toString(), Availability[].class);
            return new ArrayList<>(Arrays.asList(avails));
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Availability> findAvailabilitiesbyPersonId(int personId) {
        final String URL = BASE_URL + "availabilities/" + personId;
        HttpResponse<JsonNode> jsonResponse = null;
        try {
            jsonResponse = Unirest.get(URL).asJson();
            Availability[] avails = gson.fromJson(jsonResponse.getBody().toString(), Availability[].class);
            return new ArrayList<>(Arrays.asList(avails));
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Availability> findAvailabilitiesbyPersonandEvent(int personId, int eventId) {
        final String URL = BASE_URL + "availabilities/" + eventId + "/" + personId;
        HttpResponse<JsonNode> jsonResponse = null;
        try {
            jsonResponse = Unirest.get(URL).asJson();
            Availability[] avails = gson.fromJson(jsonResponse.getBody().toString(), Availability[].class);
            return new ArrayList<>(Arrays.asList(avails));
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        return null;

    }

    @Override
    public List<Availability> findAllAvails() {
        final String URL = BASE_URL + "availabilities";
        HttpResponse<JsonNode> jsonResponse = null;
        try {
            jsonResponse = Unirest.get(URL).asJson();
            Availability[] avails = gson.fromJson(jsonResponse.getBody().toString(), Availability[].class);
            return new ArrayList<>(Arrays.asList(avails));
        } catch (UnirestException e) {
            // TODO Error
            e.printStackTrace();
        }

        return null;
    }
}
