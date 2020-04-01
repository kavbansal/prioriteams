package model;

import java.util.Objects;

public class Event {

    private int duration;
    private String eventName;
    private String location;
    private int id;
    private double optimalTime;

    public Event(int d, String e, String l, int optimalTime) {
        this.duration = d;
        this.eventName = e;
        this.location = l;
        this.optimalTime = optimalTime;
    }



    public int getDuration() {
        return this.duration;
    }

    public String getEventName() {
        return this.eventName;
    }

    public String getLocation() {
        return this.location;
    }

    public int getId() {
        return this.id;
    }

    public void setDuration(int d) {
        this.duration = d;
    }

    public void setEventName(String e) {
        this.eventName = e;
    }

    public void setLocation(String l) {
        this.location = l;
    }

    public void setId(int i) {
        this.id = i;
    }

    public double getOptimalTime() {return this.optimalTime;}

    public void setOptimalTime(double i) {this.optimalTime = i;}

    @Override
    public int hashCode() {
        return Objects.hash(id, eventName, location, duration);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Event event = (Event) obj;
        return id == event.id && this.eventName.equals(event.eventName) &&
                Objects.equals(this.location, event.location) &&
                Objects.equals(this.duration, event.duration);
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", eventName='" + eventName + '\'' +
                ", location='" + location + '\'' +
                ", duration='" + duration + '\'' +
                '}';
    }
}
