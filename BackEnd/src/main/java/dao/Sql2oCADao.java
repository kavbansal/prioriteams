package dao;

import exception.DaoException;
import model.CourseAssistant;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;
import java.util.List;

public class Sql2oCADao implements CourseAssistantDao {

    private Sql2o sql2o;

    public Sql2oCADao(Sql2o sql2o) {
        this.sql2o=sql2o;
    }

    @Override
    public void add(CourseAssistant ca) throws DaoException {
        try (Connection conn = sql2o.open()) {
            String sql = "INSERT INTO CourseAssistants(name, email, username, password) VALUES(:name, :email, :username, :password);";
            int id = (int) conn.createQuery(sql)
                    .bind(ca)
                    .executeUpdate()
                    .getKey();
            ca.setId(id);
        } catch (Sql2oException ex) {
            throw new DaoException("Unable to add the CA", ex);
        }
    }

    @Override
    public List<CourseAssistant> findAllCAs() {
        String sql = "SELECT * FROM CourseAssistants;";
        try (Connection conn = sql2o.open()) {
            return conn.createQuery(sql)
                    .executeAndFetch(CourseAssistant.class);
        }
    }

    public List<CourseAssistant> findCA(String username, String password) {
        String sql="Select * FROM CourseAssistants where CourseAssistants.username=:username AND CourseAssistants.password=:password";
        try (Connection conn = sql2o.open()) {
            return  conn.createQuery(sql).executeAndFetch(CourseAssistant.class);
        }
    }
}
