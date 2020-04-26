package dao;

import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import exception.DaoException;
import model.Person;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UnireastPersonDao implements PersonDao {
    public final static String BASE_URL = "http://127.0.0.1:7000/";
    private static Gson gson = new Gson();


    @Override
    public void add(Person p) throws DaoException {
        final String URL = BASE_URL + "Person";
        try {
            Unirest.post(URL).body(gson.toJson(p)).asJson();
        } catch (UnirestException e) {
            // TODO error
            e.printStackTrace();
        }
    }

    @Override
    public void updatePriority(int pId, int priority) {
        final String URL = BASE_URL + "Person/" + pId + "/" + priority;
        try {
            Unirest.post(URL).body(gson.toJson(null)).asJson();
        } catch (UnirestException e) {
            // TODO error
            e.printStackTrace();
        }
    }

    @Override
    public List<Person> findAllPeople() {
        final String URL = BASE_URL + "People";
        HttpResponse<JsonNode> jsonResponse = null;
        try {
            jsonResponse = Unirest.get(URL).asJson();
            Person[] people = gson.fromJson(jsonResponse.getBody().toString(), Person[].class);
            return new ArrayList<>(Arrays.asList(people));
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Person> findPersonbyPersonId(int pId) {
        final String URL = BASE_URL + "Person/"+pId;
        HttpResponse<JsonNode> jsonResponse = null;
        try {
            jsonResponse = Unirest.get(URL).asJson();
            Person[] people = gson.fromJson(jsonResponse.getBody().toString(), Person[].class);
            return new ArrayList<>(Arrays.asList(people));
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public List<Person> findPersonbyUsername(String username) {
        final String URL = BASE_URL + "Person" + "/" + username;
        HttpResponse<JsonNode> jsonResponse = null;
        try {
            jsonResponse = Unirest.get(URL).asJson();
            Person[] people = gson.fromJson(jsonResponse.getBody().toString(),Person[].class);
            return new ArrayList<>(Arrays.asList(people));
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Person> findPerson(String username, String password) {
        final String URL = BASE_URL + "Person" + "/" + username + "/" + password;
        HttpResponse<JsonNode> jsonResponse = null;
        try {
            jsonResponse = Unirest.get(URL).asJson();
            Person[] people = gson.fromJson(jsonResponse.getBody().toString(),Person[].class);
            return new ArrayList<>(Arrays.asList(people));
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Person> findAllProfessors() {
        final String URL = BASE_URL + "professors";
        HttpResponse<JsonNode> jsonResponse = null;
        try {
            jsonResponse = Unirest.get(URL).asJson();
            Person[] people = gson.fromJson(jsonResponse.getBody().toString(), Person[].class);
            return new ArrayList<>(Arrays.asList(people));
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Person> findAllPeopleExceptProfs() {
        final String URL = BASE_URL + "nonProfs";
        HttpResponse<JsonNode> jsonResponse = null;
        try {
            jsonResponse = Unirest.get(URL).asJson();
            Person[] people = gson.fromJson(jsonResponse.getBody().toString(), Person[].class);
            return new ArrayList<>(Arrays.asList(people));
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        return null;
    }
}
