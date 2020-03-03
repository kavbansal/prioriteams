package model;

public class CourseAssistant implements Person {

    private String name;
    private String email;
    private int id;

    public CourseAssistant(String Name, String email) {
        this.name= Name;
        this.email=email;
    }

    @Override
    public int getId() {
        return 0;
    }

    @Override
    public void setId(int id) {

    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void setName(String name) {

    }

    @Override
    public String getEmail() {
        return null;
    }

    @Override
    public void setEmail(String email) {

    }
}
