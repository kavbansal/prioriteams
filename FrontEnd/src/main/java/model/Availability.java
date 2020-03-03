package model;

public class Availability {

    private int eventId;
    private int personId;
    private int availabilityId;
    private int startTime;
    private int endTime;
    private int dow;

    public Availability(int eId, int pId, int st, int et, int dow) {
        this.eventId=eId;
        this.personId=pId;
        this.startTime=st;
        this.endTime=et;
        this.dow=dow;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public int getAvailabilityId() {
        return availabilityId;
    }

    public void setAvailabilityId(int availabilityId) {
        this.availabilityId = availabilityId;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    public int getDow() {
        return dow;
    }

    public void setDow(int dow) {
        this.dow = dow;
    }

    public void setId(int id) {
        this.availabilityId=id;
    }
}
