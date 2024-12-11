package ru.kpfu.itis.controllers.travels;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.kpfu.itis.dao.TravelDao;
import ru.kpfu.itis.entities.Travel;
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

@WebServlet("/travel/list")
public class TravelListServlet extends HttpServlet {
    private TravelDao travelDao;
    private static final Logger LOG =
            LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.travelDao = (TravelDao) getServletContext().getAttribute("travelDao");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Integer userId = (Integer) req.getSession().getAttribute("user_id");
            if (userId == null) {
                resp.sendRedirect("/signin");
                return;
            }
            LOG.info("User ID from session: " + userId);

            List<Travel> travels = travelDao.getTravelsByUserId(userId);
            req.setAttribute("travels", travels);
        } catch (DbException | SQLException e) {
            throw new RuntimeException(e);
        }
        getServletContext().getRequestDispatcher("/WEB-INF/views/travels/travelList.jsp").forward(req, resp);
    }
}
