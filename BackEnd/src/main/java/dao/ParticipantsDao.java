package dao;

import exception.DaoException;
import model.Participants;

import java.util.List;

public interface ParticipantsDao {
    void add(Participants p) throws DaoException;
    List<Participants> getAllParticipantsbyEventId(int eId);
}