package ru.kpfu.itis.dao.impl;

import ru.kpfu.itis.entities.User;
import ru.kpfu.itis.util.DbException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface UserDao {

    public User getUsernameAndPassword(String username, String password) throws DbException;

    public boolean deleteUser(int user_id) throws DbException;

    public boolean addUser(User user) throws DbException;

    public int getUserId(String username) throws DbException;

    public User getUserById(int userId) throws DbException;

    public boolean updateUserInfo(User user) throws DbException;

    public boolean updatePasswordInDatabase(User user) throws DbException;
}
