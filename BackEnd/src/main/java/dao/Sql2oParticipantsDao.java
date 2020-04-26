package dao;

import exception.DaoException;
import model.Availability;
import model.Participants;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

public class Sql2oParticipantsDao implements ParticipantsDao {
    private Sql2o sql2o;

    public Sql2oParticipantsDao(Sql2o sql2o) { this.sql2o = sql2o; }

    @Override
    public void add(Participants p) {
        try (Connection conn = sql2o.open()) {
            String sql = "INSERT INTO Participants(eId, emails) VALUES(:eId, :emails);";
            int id = (int) conn.createQuery(sql)
                    .bind(p)
                    .executeUpdate()
                    .getKey();
            p.setTableId(id);
        } catch (Sql2oException ex) {
            throw new DaoException("Unable to add the event", ex);
        }
    }

    @Override
    public List<Participants> getAllParticipantsbyEventId(int eId) {
        String sql="Select * From Participants where eId=:eId";
        try (Connection conn = sql2o.open()) {
            return conn.createQuery(sql).addParameter("eId", eId)
                    .executeAndFetch(Participants.class);
        }
    }
}
