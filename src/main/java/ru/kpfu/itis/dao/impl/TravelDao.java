package ru.kpfu.itis.dao.impl;

import ru.kpfu.itis.entities.Travel;
import ru.kpfu.itis.util.DbException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public interface TravelDao {

    public int createTravel(Travel travel);

    public int getCount() throws DbException;

    public List<Travel> getTravelsByUserId(int userId) throws DbException, SQLException;

    public Travel getTravelById(int id) throws DbException, SQLException;

    public boolean deleteTravel(int travel_id) throws DbException;

    public boolean updateTravel(Travel travel) throws DbException;

    public boolean updateTravelUrl(int travelId, String url) throws DbException;

    public List<Travel>  getUpcomingTravelsByUserId(int user_id) throws SQLException;

    public List<Travel> getCompletedTravelsByUserId(int user_id) throws DbException, SQLException;

    public boolean updateTravelStatus(int travel_id, boolean isOver) throws DbException;

    public List<Travel> searchTravelsByName(int userId, String name) throws DbException, SQLException;
}
