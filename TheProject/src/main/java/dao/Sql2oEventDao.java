package dao;

import exception.DaoException;
import model.Event;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

public class Sql2oEventDao implements EventDao {
    private Sql2o sql2o;

    public Sql2oEventDao(Sql2o sql2o) { this.sql2o = sql2o; }

    @Override
    public void add(Event event) throws DaoException {
        try (Connection conn = sql2o.open()) {
            String sql = "INSERT INTO Events(duration, eventName, location) VALUES(:duration, :eventName, :location);";
            int id = (int) conn.createQuery(sql)
                    .bind(event)
                    .executeUpdate()
                    .getKey();
            event.setId(id);
        } catch (Sql2oException ex) {
            throw new DaoException("Unable to add the event", ex);
        }
    }

    @Override
    public List<Event> findAllEvents() {
        String sql = "SELECT * FROM Events;";
        try (Connection conn = sql2o.open()) {
            return conn.createQuery(sql)
                    .executeAndFetch(Event.class);
        }
    }
}
