package ru.kpfu.itis.dao;

import ru.kpfu.itis.entities.Travel;
import ru.kpfu.itis.util.ConnectionProvider;
import ru.kpfu.itis.util.DbException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TravelDao {

    private final ConnectionProvider connectionProvider;

    public TravelDao(ConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }

    public int createTravel(Travel travel) {
        int travelId = -1;
        try {
            PreparedStatement st = this.connectionProvider.getCon().prepareStatement("INSERT INTO travel " +
                    "(user_id, name_travel, description, start_date, end_date, transport, list_of_things, notes, travel_url) VALUE (?, ?, ?, ?, ?, ?, ?, ?, ?)",
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
            //Statement - обычный запрос.
            ResultSet rs = st.executeQuery("select count(travel_id) AS count from travel"); // Если используем обычный select - еxecuteQuery().
            //Этот метод возвращает ResultSet - это итератор по результатам
            rs.next(); // next() - проходимся по результатам
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
                    rs.getString("travel_url")
            );
            travels.add(travel);
        }
        return travels;
    }

    public Travel getTravelById(int id) throws DbException, SQLException {
        PreparedStatement st = this.connectionProvider.getCon().prepareStatement("SELECT * FROM travel WHERE travel_id = ?");
        //PreparedStatement нужен для того, чтобы выполнять запросы в базу данных (чтобы выполнять команды)
        //Но кроме выполнения запроса этот класс позволяет подготовить запрос и отформатировать его должным образом
        st.setInt(1, id);
        ResultSet rs = st.executeQuery();
        boolean hasOne = rs.next();
        // result.next() возвращает true, если мы перешли к следующей строчке.
        // Если ни одной строчки нет, то она вернет false.
        // Если есть ХОТЯ БЫ одна строчка, вернется true.
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
                    rs.getString("travel_url")
            );
        } else { return null; }
    }

    public boolean deleteTravel(int travel_id) throws DbException {
        try {
            PreparedStatement st = this.connectionProvider.getCon().prepareStatement("DELETE FROM travel WHERE travel_id = ?");
            st.setInt(1, travel_id);
            int affectedRows = st.executeUpdate();
            //возвращает количество (вданном случае удаленных, а так изменнных) строк
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

}
