package ru.kpfu.itis.dao.impl;

import ru.kpfu.itis.entities.Article;
import ru.kpfu.itis.util.DbException;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ArticleDao {
    public List<Article> getAll() throws DbException, SQLException;

    public Article getArticleById(int id) throws DbException, SQLException;

    public List<Article> getFavouritesArticles(int user_id) throws SQLException;

    public List<Article> getArticlesByCategory(String category) throws SQLException;

    public Map<String, List<Article>> getArticlesGroupedByCategory() throws SQLException;

    public int createArticle(Article article) throws DbException, SQLException;

    public List<String> getAllCategories() throws SQLException;
}
