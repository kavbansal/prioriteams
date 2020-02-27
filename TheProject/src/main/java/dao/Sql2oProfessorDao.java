package dao;

import exception.DaoException;
import model.Professor;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

public class Sql2oProfessorDao implements ProfessorDao {
    private Sql2o sql2o;

    public Sql2oProfessorDao(Sql2o sql2o) { this.sql2o = sql2o; }

    @Override
    public void add(Professor professor) throws DaoException {
        try (Connection conn = sql2o.open()) {
            String sql = "INSERT INTO Professors(name, email) VALUES(:name, :email);";
            int id = (int) conn.createQuery(sql)
                    .bind(professor)
                    .executeUpdate()
                    .getKey();
            professor.setId(id);
        } catch (Sql2oException ex) {
            throw new DaoException("Unable to add the event", ex);
        }
    }

    @Override
    public List<Professor> findAllProfessors() {
        String sql = "SELECT * FROM Professors;";
        try (Connection conn = sql2o.open()) {
            return conn.createQuery(sql)
                    .executeAndFetch(Professor.class);
        }
    }
}

