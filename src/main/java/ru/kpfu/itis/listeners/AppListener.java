package ru.kpfu.itis.listeners;

import com.mysql.cj.jdbc.AbandonedConnectionCleanupThread;
import ru.kpfu.itis.dao.ArticleDao;
import ru.kpfu.itis.dao.FavouritesDao;
import ru.kpfu.itis.dao.TravelDao;
import ru.kpfu.itis.dao.UserDao;
import ru.kpfu.itis.services.FavouritesService;
import ru.kpfu.itis.services.UserService;
import ru.kpfu.itis.util.ConnectionProvider;
import ru.kpfu.itis.util.DbException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;


@WebListener
public class AppListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            ConnectionProvider connectionProvider = ConnectionProvider.getInstance();
            FavouritesDao favouritesDao = new FavouritesDao(connectionProvider);
            sce.getServletContext().setAttribute("userDao", new UserDao(connectionProvider));
            sce.getServletContext().setAttribute("travelDao", new TravelDao(connectionProvider));
            sce.getServletContext().setAttribute("articleDao", new ArticleDao(connectionProvider));
            sce.getServletContext().setAttribute("userService", new UserService());
            sce.getServletContext().setAttribute("favouritesService", new FavouritesService(favouritesDao));
            sce.getServletContext().setAttribute("favouritesDao", favouritesDao);
        } catch (DbException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        AbandonedConnectionCleanupThread.checkedShutdown();
    }
}
