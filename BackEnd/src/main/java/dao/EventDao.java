package dao;

import exception.DaoException;
import model.Event;

import java.util.List;

public interface EventDao {
    void add(Event event) throws DaoException;

    List<Event> findAllEvents();

    public List<Event> findEventbyId(int eId);

    void updateEvent(int eId, int optT);
}
