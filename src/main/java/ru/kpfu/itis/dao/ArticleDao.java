package ru.kpfu.itis.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.kpfu.itis.entities.Article;

import ru.kpfu.itis.entities.User;
import ru.kpfu.itis.util.ConnectionProvider;
import ru.kpfu.itis.util.DbException;

import java.lang.invoke.MethodHandles;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArticleDao {

    private static final Logger LOG =
            LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
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
                    rs.getBoolean("isFavourite"),
                    rs.getString("category")
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
                    rs.getBoolean("isFavourite"),
                    rs.getString("category")
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

    public List<Article> getArticlesByCategory(String category) throws SQLException {
        PreparedStatement st = this.connectionProvider.getCon().prepareStatement("SELECT * FROM article WHERE category = ?");
        st.setString(1, category);
        ResultSet rs = st.executeQuery();
        List<Article> articles = new ArrayList<>();
        while (rs.next()) {
            articles.add(new Article(
                    rs.getInt("article_id"),
                    rs.getString("title"),
                    rs.getString("content"),
                    rs.getDate("created_date"),
                    rs.getBoolean("isFavourite"),
                    rs.getString("category")
            ));
        }
        return articles;
    }

    public Map<String, List<Article>> getArticlesGroupedByCategory() throws SQLException {
        Map<String, List<Article>> groupedArticles = new HashMap<>();
        try {
            PreparedStatement st = this.connectionProvider.getCon().prepareStatement("SELECT category, article_id, title, content, created_date, isFavourite FROM article ORDER BY category, title");
            ResultSet resultSet = st.executeQuery();
            while (resultSet.next()) {
                String category = resultSet.getString("category");
                Article article = new Article(
                        resultSet.getInt("article_id"),
                        resultSet.getString("title"),
                        resultSet.getString("category"),
                        resultSet.getString("content")

                );
                groupedArticles.computeIfAbsent(category, k -> new ArrayList<>()).add(article);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return groupedArticles;
    }

    public int createArticle(Article article) throws DbException, SQLException {
        int articleId = -1;
        LocalDate today = LocalDate.now(); // Получаем текущую дату
        Date sqlDate = Date.valueOf(today);
        try {
            PreparedStatement st = this.connectionProvider.getCon().prepareStatement("INSERT INTO article " +
                            "(title, content, created_date, isFavourite, category) VALUE (?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            st.setString(1, article.getTitle());
            st.setString(2, article.getContent());
            st.setDate(3, sqlDate);
            st.setBoolean(4, false);
            st.setString(5, article.getCategory());
            int affectedRows = st.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = st.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        articleId = generatedKeys.getInt(1);
                    }
                }
            } else {
                throw new SQLException("Создание статьи не удалось.");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return articleId;
    }

    public List<String> getAllCategories() throws SQLException {
        String query = "SELECT DISTINCT category FROM article WHERE category IS NOT NULL AND category <> ''";
        List<String> categories = new ArrayList<>();

        try (PreparedStatement statement = this.connectionProvider.getCon().prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                categories.add(resultSet.getString("category"));
            }
        }

        return categories;
    }
}
