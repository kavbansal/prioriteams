package dao;

import exception.DaoException;
import model.Availability;
import model.Event;

import java.util.List;

public interface EventDao {
    void add(Event event) throws DaoException;

    List<Event> findAllEvents();

    //List<Event> calcOTime(int id, List<Availability> aList);

    List<Event> findEventbyId(int id);

    void removeAndUpdateOptTime(int eventId, int optTime);
}
