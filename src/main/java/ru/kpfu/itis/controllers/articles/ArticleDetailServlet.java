package ru.kpfu.itis.controllers.articles;

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

@WebServlet("/article/detail")
public class ArticleDetailServlet extends HttpServlet {

    private ArticleDao articleDao;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.articleDao = (ArticleDao) config.getServletContext().getAttribute("articleDao");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String article_id = req.getParameter("article_id");
            if (article_id == null) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().println("Bad request");
                return;
            }
            Article article = null;

                article = articleDao.getArticleById(Integer.parseInt(article_id));
            if (article != null) {
                req.setAttribute("article", article);
                req.getSession().setAttribute("article_id", Integer.parseInt(article_id));
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                getServletContext().getRequestDispatcher("/WEB-INF/views/errors/not_found.jsp").forward(req, resp);
            }
            getServletContext().getRequestDispatcher("/WEB-INF/views/articles/article.jsp").forward(req, resp);
        } catch (DbException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
