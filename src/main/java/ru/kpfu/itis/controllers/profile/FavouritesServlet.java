package ru.kpfu.itis.controllers.profile;

import ru.kpfu.itis.dao.ArticleDao;
import ru.kpfu.itis.entities.Article;
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

@WebServlet("/favourites")
public class FavouritesServlet extends HttpServlet {

    private ArticleDao articleDao;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.articleDao = (ArticleDao) getServletContext().getAttribute("articleDao");
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Integer userId = (Integer) req.getSession().getAttribute("user_id");
            if (userId == null) {
                resp.sendRedirect(getServletContext().getContextPath() + "/signin");
                return;
            }
            List<Article> articles = articleDao.getFavouritesArticles(userId);
            req.setAttribute("articles", articles);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        getServletContext().getRequestDispatcher("/WEB-INF/views/articles/favourites.jsp").forward(req, resp);
    }
}
