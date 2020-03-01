package dao;

import exception.DaoException;
import model.Availability;

import java.util.List;

public interface AvailabilityDao {
    void addAvailability(Availability a) throws DaoException;
    List<Availability> findAvailabilitiesbyEventId(int eventId);
    List<Availability> findAvailabilitiesbyPersonId(int personId);
    List<Availability> findAvailabilitiesbyPersonandEvent(int personId, int eventId);
}
