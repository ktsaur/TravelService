package ru.kpfu.itis.controllers.articles;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.kpfu.itis.dao.ArticleDaoImpl;
import ru.kpfu.itis.entities.Article;
import ru.kpfu.itis.util.DbException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/article/create")
public class ArticleCreateServlet extends HttpServlet {

    private static final Logger LOG =
            LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private ArticleDaoImpl articleDao;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.articleDao = (ArticleDaoImpl) config.getServletContext().getAttribute("articleDao");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // Загрузка всех существующих категорий
            List<String> categories = articleDao.getAllCategories();
            LOG.info("категории: " + categories.toString());
            req.setAttribute("categories", categories);
            getServletContext().getRequestDispatcher("/WEB-INF/views/articles/articleCreate.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException("Ошибка при загрузке категорий", e);
        }
//        getServletContext().getRequestDispatcher("/WEB-INF/views/articles/articleCreate.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String title = req.getParameter("title");
            String content = req.getParameter("content");;
            Boolean is_favourite = Boolean.parseBoolean(req.getParameter("favourite"));
            String category = req.getParameter("category");

            if (title == null || content == null || category == null) {
                req.setAttribute("message", "Все поля должны быть заполнены");
                doGet(req, resp);
                return;
            }

            List<String> existingCategories = articleDao.getAllCategories();

            Article newArticle = new Article(title, content, null, is_favourite, category);
            int article_id = articleDao.createArticle(newArticle);
            req.setAttribute("message", "Новая статья создана!");
            resp.sendRedirect(req.getContextPath() + "/article/detail?article_id=" + article_id);

        } catch (DbException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
