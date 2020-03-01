package dao;

import exception.DaoException;
import model.Availability;
import model.Event;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

public class Sql2oAvailabilityDao implements AvailabilityDao {

    private Sql2o sql2o;

    public Sql2oAvailabilityDao(Sql2o sql2o) {
        this.sql2o=sql2o;
    }

    @Override
    public void addAvailability(Availability a) throws DaoException {
        try (Connection conn = sql2o.open()) {
            String sql = "INSERT INTO Availabilities(eventId,personId,startTime,endTime,dayOfweek) VALUES(:eventId, :personId, :startTime, :endTime, :dayOfweek);";
            int id = (int) conn.createQuery(sql)
                    .bind(a)
                    .executeUpdate()
                    .getKey();
            a.setId(id);
        } catch (Sql2oException ex) {
            throw new DaoException("Unable to add the event", ex);
        }
    }

    @Override
    public List<Availability> findAvailabilitiesbyEventId(int eId) {
        String sql = "SELECT * FROM Availabilities where Availabilities.eventId=:eId";
        try (Connection conn = sql2o.open()) {
            return conn.createQuery(sql)
                    .executeAndFetch(Availability.class);
        }
    }

    @Override
    public List<Availability> findAvailabilitiesbyPersonId(int pId) {
        String sql = "SELECT * FROM Availabilities where Availabilities.personId=:pId;";
        try (Connection conn = sql2o.open()) {
            return conn.createQuery(sql)
                    .executeAndFetch(Availability.class);
        }
    }


    @Override
    public List<Availability> findAvailabilitiesbyPersonandEvent(int pId, int eId) {
        String sql = "SELECT * FROM Availabilities where Availabilities.personId=:pId AND Availabilities.eventId=:eId";
        try (Connection conn = sql2o.open()) {
            return conn.createQuery(sql)
                    .executeAndFetch(Availability.class);
        }
    }

}
