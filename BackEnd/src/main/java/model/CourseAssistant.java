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
        return this.id;
    }

    @Override
    public void setId(int id) {
        this.id=id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String name) {
        this.name=name;
    }

    @Override
    public String getEmail() {
        return this.email;
    }

    @Override
    public void setEmail(String email) {
        this.email=email;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public String getPassword() {
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
