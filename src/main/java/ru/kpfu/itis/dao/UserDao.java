package ru.kpfu.itis.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.kpfu.itis.entities.User;
import ru.kpfu.itis.util.ConnectionProvider;
import ru.kpfu.itis.util.DbException;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



public class UserDao {

    private final ConnectionProvider connectionProvider;
    private static final Logger LOG =
            LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

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
                        rs.getString("email"),
                        rs.getString("url")
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
            PreparedStatement st = this.connectionProvider.getCon().prepareStatement("INSERT INTO user (username, password, email, url) VALUES (?, ?, ?, ?) ");
            st.setString(1, user.getUsername());
            st.setString(2, user.getPassword());
            st.setString(3, user.getEmail());
            st.setString(4, "https://res.cloudinary.com/dkiovijcy/image/upload/v1733845755/anonim_hlakgc.png");
            int affectedRows = st.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new DbException("Can't add user from db.", e);
        }
    }

    public int getUserId(String username) throws DbException {
        try {
            PreparedStatement st = this.connectionProvider.getCon().prepareStatement("SELECT * FROM user WHERE username = ?");
            st.setString(1, username);
            ResultSet rs = st.executeQuery();
            boolean hasOne = rs.next();
            LOG.info("User with id: " + hasOne);
            if (hasOne) {
                return rs.getInt("user_id");
            } else { return 0; }
        } catch (SQLException e) {
            throw new DbException("Can't get user from db.", e);
        }
    }

    public boolean updateUserUrl(int userId, String url) throws DbException {
        try {
            PreparedStatement st = this.connectionProvider.getCon().prepareStatement(
                    "UPDATE user SET url = ? WHERE user_id = ?"
            );
            st.setString(1, url);
            st.setInt(2, userId);
            int affectedRows = st.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new DbException("Can't update user URL in the database.", e);
        }
    }

    public User getUserById(int userId) throws DbException {
        try {
            PreparedStatement st = this.connectionProvider.getCon().prepareStatement("SELECT * FROM user WHERE user.user_id = ?");
            st.setInt(1, userId);
            ResultSet resultSet = st.executeQuery();
            if (resultSet.next()) {
                return new User(
                        resultSet.getInt("id"),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getString("email"),
                        resultSet.getString("url")
                );
            }
        } catch (SQLException e) {
            throw new DbException("Ошибка при получении пользователя по ID", e);
        }
        return null;
    }
}
