package model;

import java.util.ArrayList;
import java.util.List;

public class Professor {
    public int id;
    public String name;
    public String email;
    public List<Event> eventList;

    public Professor(String name, String email) {
        this.name = name;
        this.email = email;
        this.eventList = new ArrayList<Event>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void addEvent(Event e) {
        eventList.add(e);
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }




}
