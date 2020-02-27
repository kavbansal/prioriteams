package dao;

import exception.DaoException;
import model.Professor;

import java.util.List;

public interface ProfessorDao {

    void add(Professor p) throws DaoException;
    List<Professor> findAllProfessors();
}
