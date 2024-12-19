package ru.kpfu.itis.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.kpfu.itis.dao.impl.UserDao;
import ru.kpfu.itis.entities.User;
import ru.kpfu.itis.util.ConnectionProvider;
import ru.kpfu.itis.util.DbException;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDaoImpl implements UserDao {

    private final ConnectionProvider connectionProvider;
    private static final Logger LOG =
            LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public UserDaoImpl(ConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }

    public User getUsernameAndPassword(String username, String encryptedPassword) throws DbException {
        try {
            PreparedStatement st = this.connectionProvider.getCon().prepareStatement("SELECT * FROM user WHERE username = ? AND password = ?");
            st.setString(1, username);
            st.setString(2, encryptedPassword);
            ResultSet rs = st.executeQuery();
            boolean hasOne = rs.next();
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
            Connection con = this.connectionProvider.getCon();

            PreparedStatement deleteTravelSt = con.prepareStatement("DELETE FROM travel WHERE user_id = ?");
            deleteTravelSt.setInt(1, user_id);
            deleteTravelSt.executeUpdate();

            PreparedStatement deleteUserSt = con.prepareStatement("DELETE FROM user WHERE user_id = ?");
            deleteUserSt.setInt(1, user_id);
            int affectedRows = deleteUserSt.executeUpdate();

            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DbException("Can't delete user from db.", e);
        }
    }


    public boolean addUser(User user) throws DbException {
        LOG.info("Adding user: {}", user);
        try {
            PreparedStatement st = this.connectionProvider.getCon().prepareStatement("INSERT INTO user (username, password, email, url) VALUES (?, ?, ?, ?) ");
            st.setString(1, user.getUsername());
            st.setString(2, user.getPassword());
            st.setString(3, user.getEmail());
            st.setString(4, "https://res.cloudinary.com/dkiovijcy/image/upload/v1733845755/anonim_hlakgc.png");
            int affectedRows = st.executeUpdate();
            LOG.info("Affected rows: {}", affectedRows);
            return affectedRows > 0;
        } catch (SQLException e) {
            LOG.error("Error adding user", e);
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

    public User getUserById(int userId) throws DbException {
        try {
            PreparedStatement st = this.connectionProvider.getCon().prepareStatement("SELECT * FROM user WHERE user.user_id = ?");
            st.setInt(1, userId);
            ResultSet resultSet = st.executeQuery();
            if (resultSet.next()) {
                return new User(
                        resultSet.getInt("user_id"),
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

    public boolean updateUserInfo(User user) throws DbException {
        try  {
            String query = "UPDATE user SET username = ?, email = ?" +
                    (user.getUrl() != null ? ", url = ?" : "") +
                    " WHERE user_id = ?";

            PreparedStatement st = this.connectionProvider.getCon().prepareStatement(query);
            st.setString(1, user.getUsername());
            st.setString(2, user.getEmail());

            int paramIndex = 3;
            if (user.getUrl() != null) {
                st.setString(paramIndex++, user.getUrl());
            }
            st.setInt(paramIndex, user.getId());

            return st.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DbException("Ошибка при обновлении путешествия.", e);
        }
    }

    public boolean updatePasswordInDatabase(User user) throws DbException {
        try  {
            PreparedStatement st = this.connectionProvider.getCon().prepareStatement("UPDATE user SET password = ? WHERE user_id = ?");
            st.setString(1, user.getPassword());
            st.setInt(2, user.getId());

            return st.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DbException("Ошибка при обновлении путешествия.", e);
        }
    }

}
