package ru.kpfu.itis.controllers.articles;

import ru.kpfu.itis.dao.ArticleDao;
import ru.kpfu.itis.dao.FavouritesDao;
import ru.kpfu.itis.entities.Article;
import ru.kpfu.itis.services.FavouritesService;
import ru.kpfu.itis.util.DbException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@WebServlet("/article/detail")
public class ArticleDetailServlet extends HttpServlet {

    private ArticleDao articleDao;
    private FavouritesDao favouritesDao;
    private FavouritesService favouritesService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.articleDao = (ArticleDao) config.getServletContext().getAttribute("articleDao");
        this.favouritesDao = (FavouritesDao) config.getServletContext().getAttribute("favouritesDao");
        this.favouritesService = (FavouritesService) getServletContext().getAttribute("favouritesService");

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String articleIdParam = req.getParameter("article_id");

            if (articleIdParam == null) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().println("Bad request");
                return;
            }

            int articleId = Integer.parseInt(articleIdParam);
            Article article = articleDao.getArticleById(articleId);
            if (article != null) {
                req.setAttribute("article", article);

                Integer userId = (Integer) req.getSession().getAttribute("user_id");
                boolean isFavourite = userId != null && favouritesService.isFavourite(userId, articleId);

                req.setAttribute("isFavourite", isFavourite);
                getServletContext().getRequestDispatcher("/WEB-INF/views/articles/article.jsp").forward(req, resp);
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                getServletContext().getRequestDispatcher("/WEB-INF/views/errors/not_found.jsp").forward(req, resp);
            }
        } catch (DbException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String articleIdParam = req.getParameter("article_id");
            Integer user_id = (Integer) req.getSession().getAttribute("user_id");

            if (user_id == null) {
                Article article = articleDao.getArticleById(Integer.parseInt(articleIdParam));
                req.setAttribute("article", article);
                req.setAttribute("message", "Сначала пользователь должен войти в аккаунт.");
                getServletContext().getRequestDispatcher("/WEB-INF/views/articles/article.jsp").forward(req, resp);
                return;
            }

            if (articleIdParam == null || user_id == null) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().println("Bad request: Missing article_id or user_id");
                return;
            }

            int articleId = Integer.parseInt(articleIdParam);
            favouritesService.actionWithFavourite(user_id, articleId);
            resp.sendRedirect(req.getContextPath() + "/article/detail?article_id=" + articleId);
        } catch (Exception e) {
            throw new ServletException("Error toggling favourite status", e);
        }
    }

}
