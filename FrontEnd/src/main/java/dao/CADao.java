package dao;

import exception.DaoException;
import model.CourseAssistant;

import java.util.List;

public interface CADao {
    void add(CourseAssistant ca) throws DaoException;

    List<CourseAssistant> findCA(String username, String password);
}
