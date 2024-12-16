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
            Integer user_id = (Integer) req.getSession().getAttribute("user_id");

            String filter = req.getParameter("filter");
            String search = req.getParameter("search");

            List<Travel> travels;

            if (search != null && !search.trim().isEmpty()) {
                travels = travelDao.searchTravelsByName(user_id, search.trim());
            } else if (filter != null) {
                switch (filter) {
                    case "upcoming":
                        travels = travelDao.getUpcomingTravelsByUserId(user_id);
                        break;
                    case "completed":
                        travels = travelDao.getCompletedTravelsByUserId(user_id);
                        break;
                    default:
                        travels = travelDao.getTravelsByUserId(user_id);
                }
            } else {
                travels = travelDao.getTravelsByUserId(user_id);
            }

            req.setAttribute("travels", travels);
            req.setAttribute("filter", filter);
            req.setAttribute("search", search);

        } catch (DbException | SQLException e) {
            throw new RuntimeException(e);
        }
        getServletContext().getRequestDispatcher("/WEB-INF/views/travels/travelList.jsp").forward(req, resp);
    }
}
