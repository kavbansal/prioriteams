package dao;

import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import model.Participants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UnireastParticipantsDao implements ParticipantsDao {

    public final static String BASE_URL = "http://127.0.0.1:7000/";
    private static Gson gson = new Gson();

    @Override
    public void add(Participants p) {
        final String URL = BASE_URL + "Participants";
        try {
            Unirest.post(URL).body(gson.toJson(p)).asJson();
        } catch (UnirestException e) {
            // TODO error
            e.printStackTrace();
        }
    }

    @Override
    public List<Participants> getAllParticipantsbyEventId(int eId) {
        final String URL = BASE_URL + "Participants/" + eId;
        HttpResponse<JsonNode> jsonResponse = null;
        try {
            jsonResponse = Unirest.get(URL).asJson();
            Participants[] participants = gson.fromJson(jsonResponse.getBody().toString(), Participants[].class);
            return new ArrayList<>(Arrays.asList(participants));
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        return null;
    }

}
