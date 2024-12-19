package ru.kpfu.itis.controllers.login;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.kpfu.itis.dao.ArticleDaoImpl;
import ru.kpfu.itis.entities.Article;
import ru.kpfu.itis.services.FavouritesService;
import ru.kpfu.itis.util.DbException;
import ru.kpfu.itis.util.FavouriteActionResult;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/main")
public class MainServlet extends HttpServlet {

    private static final Logger LOG =
            LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private ArticleDaoImpl articleDao;
    private FavouritesService favouritesService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.articleDao = (ArticleDaoImpl) getServletContext().getAttribute("articleDao");
        this.favouritesService = (FavouritesService) getServletContext().getAttribute("favouritesService");
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Map<String, List<Article>> groupedArticles = articleDao.getArticlesGroupedByCategory();
            LOG.info("groupedArticles: = " + groupedArticles.toString());
            Integer userId = (Integer) req.getSession().getAttribute("user_id");

            for (Map.Entry<String, List<Article>> entry : groupedArticles.entrySet()) {
                LOG.info("Category: " + entry.getKey());
                for (Article article : entry.getValue()) {
                    LOG.info("Article: " + article.getContent());
                }
            }

            Map<Integer, Boolean> favouriteStatus = new HashMap<>();
            if (userId != null) {
                for (List<Article> articles : groupedArticles.values()) {
                    for (Article article : articles) {
                        boolean isFavourite = favouritesService.isFavourite(userId, article.getArticle_id());
                        favouriteStatus.put(article.getArticle_id(), isFavourite);
                    }
                }
            }
            LOG.info("favouriteStatus = " + favouriteStatus.toString());

            req.setAttribute("groupedArticles", groupedArticles);
            req.setAttribute("favouriteStatus", favouriteStatus);

            String message = (String) req.getSession().getAttribute("message");
            if (message != null) {
                req.setAttribute("message", message);
                req.getSession().removeAttribute("message");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        getServletContext().getRequestDispatcher("/WEB-INF/views/login/main.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        String action = req.getParameter("action");
        if ("toggleFavourite".equals(action)) {
            actionWithFavourites(req, resp);
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    public void actionWithFavourites(HttpServletRequest req, HttpServletResponse resp) {
        try {
            Integer user_id = (Integer) req.getSession().getAttribute("user_id");
            if (user_id == null) {
                Map<String, List<Article>> groupedArticles = articleDao.getArticlesGroupedByCategory();
                LOG.info("groupedArticles: = " + groupedArticles.toString());
                req.setAttribute("groupedArticles", groupedArticles);
                req.setAttribute("message", "Сначала пользователь должен войти в аккаунт.");
                getServletContext().getRequestDispatcher("/WEB-INF/views/login/main.jsp").forward(req, resp);
                return;
            }
            String articleIdParam = req.getParameter("article_id");
            if (articleIdParam == null) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            Integer article_id = Integer.parseInt(articleIdParam);
            FavouriteActionResult result = favouritesService.actionWithFavourite(user_id, article_id);
            if (result.isSuccess()) {
                req.setAttribute("isFavourite", result.isFavourite());
                req.setAttribute("message", result.isFavourite() ? "Статья добавлена в избранное." : "Статья удалена из избранного.");
                resp.sendRedirect(getServletContext().getContextPath() + "/main");
            } else {
                req.setAttribute("message", "Не удалось выполнить действие с избранным.");
                getServletContext().getRequestDispatcher("/WEB-INF/views/login/main.jsp").forward(req, resp);
            }
        } catch (SQLException | IOException | DbException | ServletException e) {
            throw new RuntimeException(e);
        }
    }
}
