package ru.kpfu.itis.dao;

import ru.kpfu.itis.entities.User;
import ru.kpfu.itis.util.ConnectionProvider;
import ru.kpfu.itis.util.DbException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FavouritesDao {
    private ConnectionProvider connectionProvider;

    public FavouritesDao(ConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }

    public boolean addFavourite(int user_id, int article_id) throws SQLException {
        PreparedStatement st = this.connectionProvider.getCon().prepareStatement("INSERT INTO favourites (user_id, article_id) VALUES (?, ?)");
        st.setInt(1, user_id);
        st.setInt(2, article_id);
        int affectedRows = st.executeUpdate();
        return affectedRows > 0;
    }

    public boolean removeFavourite(int user_id, int article_id) throws DbException {
        try {
            PreparedStatement st = this.connectionProvider.getCon().prepareStatement("DELETE FROM favourites WHERE user_id = ? AND article_id = ?");
            st.setInt(1, user_id);
            st.setInt(2, article_id);
            int affectedRows = st.executeUpdate();
            //возвращает количество (вданном случае удаленных, а так изменнных) строк
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new DbException("Can't delete travel from db.", e);
        }
    }

    public boolean isFavourite(int user_id, int article_id) throws SQLException {
        PreparedStatement st = this.connectionProvider.getCon().prepareStatement("SELECT * FROM favourites WHERE user_id = ? AND article_id = ?");
        st.setInt(1, user_id);
        st.setInt(2, article_id);
        ResultSet rs = st.executeQuery();
        if (rs.next()) {
            return rs.getInt(1) > 0; // Проверяем, есть ли записи
        }
        return false;
    }

}
