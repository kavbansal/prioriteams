package dao;

import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import exception.DaoException;
import model.Availability;
import model.CourseAssistant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UnireastCADao implements CADao {
    public final static String BASE_URL = "http://127.0.0.1:7000/";
    private static Gson gson = new Gson();


    @Override
    public void add(CourseAssistant ca) throws DaoException {
        final String URL = BASE_URL + "CourseAssistants";
        try {
            Unirest.post(URL).body(gson.toJson(ca)).asJson();
        } catch (UnirestException e) {
            // TODO error
            e.printStackTrace();
        }
    }

    @Override
    public List<CourseAssistant> findCA(String username, String password) {
        final String URL = BASE_URL + "CourseAssistants/:username/:password";
        HttpResponse<JsonNode> jsonResponse = null;
        try {
            jsonResponse = Unirest.get(URL).asJson();
            CourseAssistant[] CAs = gson.fromJson(jsonResponse.getBody().toString(), CourseAssistant[].class);
            return new ArrayList<>(Arrays.asList(CAs));
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        return null;
    }
}
