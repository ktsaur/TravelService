package ru.kpfu.itis.dao;

import ru.kpfu.itis.entities.Article;

import ru.kpfu.itis.entities.User;
import ru.kpfu.itis.util.ConnectionProvider;
import ru.kpfu.itis.util.DbException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ArticleDao {
    private ConnectionProvider connectionProvider;

    public ArticleDao(ConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }

    public List<Article> getAll() throws DbException, SQLException {
        Statement st = this.connectionProvider.getCon().createStatement();
        ResultSet rs = st.executeQuery("select * from article");
        List<Article> articles = new ArrayList<>();
        while (rs.next()) {
            Article article = new Article(
                    rs.getInt("article_id"),
                    rs.getString("title"),
                    rs.getString("content"),
                    rs.getDate("created_date"),
                    rs.getBoolean("isFavourite")
            );
            articles.add(article);
        }
        return articles;
    }

    public Article getArticleById(int id) throws DbException, SQLException {
        PreparedStatement st = this.connectionProvider.getCon().prepareStatement("SELECT * FROM article WHERE article_id = ?");
        //PreparedStatement нужен для того, чтобы выполнять запросы в базу данных (чтобы выполнять команды)
        //Но кроме выполнения запроса этот класс позволяет подготовить запрос и отформатировать его должным образом
        st.setInt(1, id);
        ResultSet rs = st.executeQuery();
        boolean hasOne = rs.next();
        // result.next() возвращает true, если мы перешли к следующей строчке.
        // Если ни одной строчки нет, то она вернет false.
        // Если есть ХОТЯ БЫ одна строчка, вернется true.
        if (hasOne) {
            return new Article(
                    rs.getInt("article_id"),
                    rs.getString("title"),
                    rs.getString("content"),
                    rs.getDate("created_date"),
                    rs.getBoolean("isFavourite")
            );
        } else {
            return null;
        }
    }

    public List<Article> getFavouritesArticles(int user_id) throws SQLException {
        PreparedStatement st = this.connectionProvider.getCon().prepareStatement(
                "SELECT a.article_id, a.title, a.content, a.created_date, 0 AS isFavourite " +
                        "FROM favourites f " +
                        "JOIN article a ON f.article_id = a.article_id " +
                        "WHERE f.user_id = ?"
        );
        st.setInt(1, user_id);
        List<Article> articles = new ArrayList<>();
        ResultSet rs = st.executeQuery();
        while (rs.next()) {
            Article article = new Article(
                    rs.getInt("article_id"),
                    rs.getString("title"),
                    rs.getString("content"),
                    rs.getDate("created_date"),
                    rs.getBoolean("isFavourite")
            );
            articles.add(article);
        }
        return articles;
    }
}
