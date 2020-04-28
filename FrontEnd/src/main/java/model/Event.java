package model;

import java.util.Objects;

public class Event {

    private int duration;
    private String eventName;
    private String location;
    private int id;
    private int optimalTime;
    private int optimalDay;
    private String optDayString;

    public Event(int d, String e, String l,int i,int x) {
        this.duration = d;
        this.eventName = e;
        this.location = l;
        this.optimalTime = i;
        this.optimalDay = x;
    }

    public String getOptDayString() {
        switch(this.optimalDay) {
            case 1:
                return "Monday";
            case 2:
                return "Tuesday";
            case 3:
                return "Wednesday";
            case 4:
                return "Thursday";
            case 5:
                return "Friday";
            case 6:
                return "Saturday";
            case 7:
                return "Sunday";

        }
        return "";
    }

    public void setOptDayString(String optDayString) {
        this.optDayString = optDayString;
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

    public int getId() { return this.id; }

    public Integer getOptimalTime() {
        if (this.optimalTime == -1) {
            return null;
        }
        return this.optimalTime;
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

    public void setOptimalTime(int o) {
        this.optimalTime = o;
    }

    public void setOptimalDay(int d) {
        this.optimalDay = d;
    }

    public int getOptimalDay() {
        return optimalDay;
    }

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
