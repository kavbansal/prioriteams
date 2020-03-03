package dao;

import exception.DaoException;
import model.CourseAssistant;

import java.util.List;

public interface CourseAssistantDao {

    void add(CourseAssistant ca) throws DaoException;
    List<CourseAssistant> findAllCAs();
}

