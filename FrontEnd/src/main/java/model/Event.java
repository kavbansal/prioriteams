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
    private String optTimeString;
    private String durationString;

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

    public String getOptTimeString() {
        switch(this.optimalTime) {
            case 7:
                return "7:00 am";
            case 8:
                return "8:00 am";
            case 9:
                return "9:00 am";
            case 10:
                return "10:00 am";
            case 11:
                return "11:00 am";
            case 12:
                return "12:00 pm";
            case 13:
                return "1:00 pm";
            case 14:
                return "2:00 pm";
            case 15:
                return "3:00 pm";
            case 16:
                return "4:00 pm";
            case 17:
                return "5:00 pm";
            case 18:
                return "6:00 pm";
            case 19:
                return "7:00 pm";
            case 20:
                return "8:00 pm";
            case 21:
                return "9:00 pm";
            case 22:
                return "10:00 pm";
            case 23:
                return "11:00 pm";
        }
        return "";
    }

    public String getDurationString() {
        switch(this.duration) {
            case 60:
                return "1 hour";
            case 120:
                return "2 hours";
            case 180:
                return "3 hours";
            case 240:
                return "4 hours";
            case 300:
                return "5 hours";
            case 360:
                return "6 hours";
            case 420:
                return "7 hours";
            case 480:
                return "8 hours";
            case 30:
                return "30 minutes";
        }
        return "";
    }

    public void setOptDayString(String optDayString) {
        this.optDayString = optDayString;
    }

    public void setOptTimeString(String optTimeString) { this.optTimeString = optTimeString; }

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
