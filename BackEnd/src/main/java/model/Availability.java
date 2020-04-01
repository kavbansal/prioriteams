package model;

public class Availability {

    private int eventId;
    private int personId;
    private int availabilityId;
    private double startTime;
    private double endTime;
    private int dow;

    public Availability(int eId, int pId, double st, double et, int dow) {
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

    public double getStartTime() {
        return startTime;
    }

    public void setStartTime(double startTime) {
        this.startTime = startTime;
    }

    public double getEndTime() {
        return endTime;
    }

    public void setEndTime(double endTime) {
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
