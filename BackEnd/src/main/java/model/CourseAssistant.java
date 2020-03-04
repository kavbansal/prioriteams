package model;

public class CourseAssistant implements Person {

    private String name;
    private String email;
    private String username;
    private String password;
    private int id;

    public CourseAssistant(String name, String email, String username, String password) {
        this.name= name;
        this.email=email;
        this.username=username;
        this.password=password;
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

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public String getPwd() {
        return this.password;
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public void setPwd(String pwd) {
        this.password = pwd;
    }
}
