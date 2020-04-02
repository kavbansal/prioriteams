package dao;

import exception.DaoException;
import model.Event;

import java.util.List;

public interface EventDao {
    void add(Event event) throws DaoException;

    void update(int time, int eId, int day);

    List<Event> findAllEvents();

    public List<Event> findEventbyId(int eId);

    void remove(int eventId);

}
