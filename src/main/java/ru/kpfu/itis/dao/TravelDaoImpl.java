package ru.kpfu.itis.dao;

import ru.kpfu.itis.dao.impl.TravelDao;
import ru.kpfu.itis.entities.Travel;
import ru.kpfu.itis.util.ConnectionProvider;
import ru.kpfu.itis.util.DbException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TravelDaoImpl implements TravelDao {

    private final ConnectionProvider connectionProvider;

    public TravelDaoImpl(ConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }

    public int createTravel(Travel travel) {
        int travelId = -1;
        try {
            PreparedStatement st = this.connectionProvider.getCon().prepareStatement("INSERT INTO travel " +
                    "(user_id, name_travel, description, start_date, end_date, transport, list_of_things, notes, travel_url, isOver) VALUE (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            st.setInt(1, travel.getUser_id());
            st.setString(2, travel.getName_travel());
            st.setString(3, travel.getDescription());
            st.setDate(4, travel.getStart_date());
            st.setDate(5, travel.getEnd_date());
            st.setString(6, travel.getTransport());
            st.setString(7, travel.getList_of_things());
            st.setString(8, travel.getNotes());
            st.setString(9, "https://res.cloudinary.com/dkiovijcy/image/upload/v1733940712/%D0%A1%D0%BD%D0%B8%D0%BC%D0%BE%D0%BA_%D1%8D%D0%BA%D1%80%D0%B0%D0%BD%D0%B0_2024-12-11_%D0%B2_21.11.23_nuchos.png");
            st.setBoolean(10,false);
            int affectedRows = st.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = st.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        travelId = generatedKeys.getInt(1);
                    }
                }
            } else {
                throw new SQLException("Создание путешествия не удалось.");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return travelId;
    }

    public int getCount() throws DbException {
        try {
            Statement st = this.connectionProvider.getCon().createStatement();
            ResultSet rs = st.executeQuery("select count(travel_id) AS count from travel");
            rs.next();
            return rs.getInt("count");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    public List<Travel> getTravelsByUserId(int userId) throws DbException, SQLException {
        PreparedStatement st = this.connectionProvider.getCon().prepareStatement("SELECT * FROM travel WHERE user_id = ?");
        st.setInt(1, userId);
        ResultSet rs = st.executeQuery();
        List<Travel> travels = new ArrayList<>();
        while(rs.next()){
            Travel travel = new Travel(
                    rs.getInt("travel_id"),
                    rs.getInt("user_id"),
                    rs.getString("name_travel"),
                    rs.getString("description"),
                    rs.getDate("start_date"),
                    rs.getDate("end_date"),
                    rs.getString("transport"),
                    rs.getString("list_of_things"),
                    rs.getString("notes"),
                    rs.getString("travel_url"),
                    rs.getBoolean("isOver")
            );
            travels.add(travel);
        }
        return travels;
    }

    public Travel getTravelById(int id) throws DbException, SQLException {
        PreparedStatement st = this.connectionProvider.getCon().prepareStatement("SELECT * FROM travel WHERE travel_id = ?");
        st.setInt(1, id);
        ResultSet rs = st.executeQuery();
        boolean hasOne = rs.next();
        if (hasOne) {
            return new Travel(
                    rs.getInt("travel_id"),
                    rs.getInt("user_id"),
                    rs.getString("name_travel"),
                    rs.getString("description"),
                    rs.getDate("start_date"),
                    rs.getDate("end_date"),
                    rs.getString("transport"),
                    rs.getString("list_of_things"),
                    rs.getString("notes"),
                    rs.getString("travel_url"),
                    rs.getBoolean("isOver")
            );
        } else { return null; }
    }

    public boolean deleteTravel(int travel_id) throws DbException {
        try {
            PreparedStatement st = this.connectionProvider.getCon().prepareStatement("DELETE FROM travel WHERE travel_id = ?");
            st.setInt(1, travel_id);
            int affectedRows = st.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new DbException("Can't delete travel from db.", e);
        }
    }

    public boolean updateTravel(Travel travel) throws DbException {
        try  {
            PreparedStatement st = this.connectionProvider.getCon().prepareStatement("UPDATE travel SET name_travel = ?, description = ?, start_date = ?, end_date = ?, " +
                    "transport = ?, list_of_things = ?, notes = ? WHERE travel_id = ?");
            st.setString(1, travel.getName_travel());
            st.setString(2, travel.getDescription());
            st.setDate(3, travel.getStart_date());
            st.setDate(4, travel.getEnd_date());
            st.setString(5, travel.getTransport());
            st.setString(6, travel.getList_of_things());
            st.setString(7, travel.getNotes());
            st.setInt(8, travel.getTravel_id());

            return st.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DbException("Ошибка при обновлении путешествия.", e);
        }
    }

    public boolean updateTravelUrl(int travelId, String url) throws DbException {
        try {
            PreparedStatement st = this.connectionProvider.getCon().prepareStatement(
                    "UPDATE travel SET travel_url = ? WHERE travel_id = ?"
            );
            st.setString(1, url);
            st.setInt(2, travelId);
            int affectedRows = st.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new DbException("Can't update user URL in the database.", e);
        }
    }

    public List<Travel>  getUpcomingTravelsByUserId(int user_id) throws SQLException {
        PreparedStatement st = this.connectionProvider.getCon().prepareStatement("SELECT * FROM travel WHERE user_id = ? AND isOver = FALSE");
        st.setInt(1, user_id);
        ResultSet rs = st.executeQuery();
        List<Travel> travels = new ArrayList<>();
        while(rs.next()){
            Travel travel = new Travel(
                    rs.getInt("travel_id"),
                    rs.getInt("user_id"),
                    rs.getString("name_travel"),
                    rs.getString("description"),
                    rs.getDate("start_date"),
                    rs.getDate("end_date"),
                    rs.getString("transport"),
                    rs.getString("list_of_things"),
                    rs.getString("notes"),
                    rs.getString("travel_url"),
                    rs.getBoolean("isOver")
            );
            travels.add(travel);
        }
        return travels;
    }

    public List<Travel> getCompletedTravelsByUserId(int user_id) throws DbException, SQLException {
        PreparedStatement st = this.connectionProvider.getCon().prepareStatement("SELECT * FROM travel WHERE user_id = ? AND isOver = TRUE");
        st.setInt(1, user_id);
        ResultSet rs = st.executeQuery();
        List<Travel> travels = new ArrayList<>();
        while(rs.next()){
            Travel travel = new Travel(
                    rs.getInt("travel_id"),
                    rs.getInt("user_id"),
                    rs.getString("name_travel"),
                    rs.getString("description"),
                    rs.getDate("start_date"),
                    rs.getDate("end_date"),
                    rs.getString("transport"),
                    rs.getString("list_of_things"),
                    rs.getString("notes"),
                    rs.getString("travel_url"),
                    rs.getBoolean("isOver")
            );
            travels.add(travel);
        }
        return travels;
    }

    public boolean updateTravelStatus(int travel_id, boolean isOver) throws DbException {
        try {
            PreparedStatement stmt = this.connectionProvider.getCon().prepareStatement("UPDATE travel SET isOver = ? WHERE travel_id = ?");
            stmt.setBoolean(1, isOver);
            stmt.setInt(2, travel_id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DbException("Ошибка обновления статуса путешествия", e);
        }
    }

    public List<Travel> searchTravelsByName(int userId, String name) throws DbException, SQLException {
        PreparedStatement stmt = this.connectionProvider.getCon().prepareStatement("SELECT * FROM travel WHERE user_id = ? AND LOWER(name_travel) LIKE ?");
        stmt.setInt(1, userId);
        stmt.setString(2, "%" + name.toLowerCase() + "%");
        ResultSet rs = stmt.executeQuery();
        List<Travel> travels = new ArrayList<>();
        while(rs.next()){
            Travel travel = new Travel(
                    rs.getInt("travel_id"),
                    rs.getInt("user_id"),
                    rs.getString("name_travel"),
                    rs.getString("description"),
                    rs.getDate("start_date"),
                    rs.getDate("end_date"),
                    rs.getString("transport"),
                    rs.getString("list_of_things"),
                    rs.getString("notes"),
                    rs.getString("travel_url"),
                    rs.getBoolean("isOver")
            );
            travels.add(travel);
        }
        return travels;

    }

}
