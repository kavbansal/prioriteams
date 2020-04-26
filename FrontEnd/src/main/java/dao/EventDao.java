package dao;

import exception.DaoException;
import model.Availability;
import model.Event;

import java.util.List;

public interface EventDao {
    int add(Event event) throws DaoException;

    List<Event> findAllEvents();

    //List<Event> calcOTime(int id, List<Availability> aList);

    Event findEventbyId(int id);

    List<Event> removeAndUpdateOptTime(int eventId, int optTime, int optDay);
}
