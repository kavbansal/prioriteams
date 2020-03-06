package dao;

import exception.DaoException;
import model.CourseAssistant;
import model.Person;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;
import java.util.List;

public class Sql2oPersonDao implements PersonDao {

    private Sql2o sql2o;

    public Sql2oPersonDao(Sql2o sql2o) {
        this.sql2o=sql2o;
    }

    @Override
    public void add(Person p) throws DaoException {
        try (Connection conn = sql2o.open()) {
            String sql = "INSERT INTO People(name, email, username, password, priority) VALUES(:name, :email, :username, :password,:priority);";
            int id = (int) conn.createQuery(sql)
                    .bind(p)
                    .executeUpdate()
                    .getKey();
            p.setId(id);
        } catch (Sql2oException ex) {
            throw new DaoException("Unable to add the Person", ex);
        }
    }

    @Override
    public List<Person> findAllPeople() {
        String sql = "SELECT * FROM People;";
        try (Connection conn = sql2o.open()) {
            return conn.createQuery(sql)
                    .executeAndFetch(Person.class);
        }
    }


    public List<Person> findPerson(String username, String password) {
        String sql="Select * FROM People where username=:username AND password=:password";
        try (Connection conn = sql2o.open()) {
            return  conn.createQuery(sql).addParameter("username",username).addParameter("password",password).executeAndFetch(Person.class);
        }
    }

    public List<Person> findPersonbyUsername(String username) {
        String sql="Select * From People where username=:username";
        try (Connection conn = sql2o.open()) {
            return conn.createQuery(sql).addParameter("username",username).executeAndFetch(Person.class);
        }
    }

    public List<Person> findAllProfessors() {
        String sql="Select * From People where priority=1";
        try (Connection conn = sql2o.open()) {
            return conn.createQuery(sql).executeAndFetch(Person.class);
        }
    }

}
