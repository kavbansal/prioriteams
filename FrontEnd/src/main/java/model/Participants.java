package model;

public class Participants {
    private int tableId;
    private int eId;
    private String emails;

    public Participants(int eId, String emails) {
        this.eId=eId;
        this.emails=emails;
    }
    public int geteId() {
        return this.eId;
    }

    public void seteId(int id) {
        this.eId = id;
    }

    public String getEmails() {
        return this.emails;
    }

    public void setEmails(String str) {
        this.emails=str;
    }

    public int getTableId() {
        return this.tableId;
    }

    public void setTableId(int i) {
        this.tableId=i;
    }

}
