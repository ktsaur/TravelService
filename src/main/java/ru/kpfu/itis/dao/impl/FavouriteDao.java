package ru.kpfu.itis.dao.impl;

import ru.kpfu.itis.util.ConnectionProvider;
import ru.kpfu.itis.util.DbException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface FavouriteDao {

    public boolean addFavourite(int user_id, int article_id) throws SQLException;

    public boolean removeFavourite(int user_id, int article_id) throws DbException;

    public boolean isFavourite(int user_id, int article_id) throws SQLException;
}
