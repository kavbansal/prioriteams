package dao;

import com.google.gson.Gson;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import model.Participants;

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

}
