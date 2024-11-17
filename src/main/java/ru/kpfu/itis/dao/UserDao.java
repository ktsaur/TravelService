package ru.kpfu.itis.dao;

import ru.kpfu.itis.entities.User;
import ru.kpfu.itis.util.ConnectionProvider;
import ru.kpfu.itis.util.DbException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class UserDao {

    private final ConnectionProvider connectionProvider;

    public UserDao(ConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }

    public User getUsernameAndPassword(String username, String password) throws DbException {
        try {
            PreparedStatement st = this.connectionProvider.getCon().prepareStatement("SELECT * FROM user WHERE username = ? AND password = ?");
            st.setString(1, username);
            st.setString(2, password);
            ResultSet rs = st.executeQuery(); //возвращает результат SQL-запроса
            boolean hasOne = rs.next();
            // result.next() возвращает true, если мы перешли к следующей строчке.
            // Если ни одной строчки нет, то она вернет false.
            // Если есть ХОТЯ БЫ одна строчка, вернется true.
            if (hasOne) {
                return new User(
                        rs.getInt("user_id"),
                        rs.getString("username"),
                        null,
                        rs.getString("email")
                );
            } else { return null; }
        } catch (SQLException e) {
            throw new DbException("Can't get user from db.", e);
        }
    }

    public boolean deleteUser(int user_id) throws DbException {
        try {
            PreparedStatement st = this.connectionProvider.getCon().prepareStatement("DELETE FROM user WHERE user_id = ?");
            st.setInt(1, user_id);
            int affectedRows = st.executeUpdate();
            //возвращает количество (вданном случае удаленных, а так изменнных) строк
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new DbException("Can't delete user from db.", e);
        }
    }

    public boolean addUser(User user) throws DbException {
        try {
            PreparedStatement st = this.connectionProvider.getCon().prepareStatement("INSERT INTO user (username, password, email) VALUES (?, ?, ?) ");
            st.setString(1, user.getUsername());
            st.setString(2, user.getPassword());
            st.setString(3, user.getEmail());
            int affectedRows = st.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new DbException("Can't add user from db.", e);
        }
    }
}
