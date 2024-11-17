package ru.kpfu.itis.controllers.travels;

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
import java.sql.SQLException;
import java.util.List;

@WebServlet("/travel/list")
public class TravelListServlet extends HttpServlet {
    private TravelDao travelDao;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.travelDao = (TravelDao) getServletContext().getAttribute("travelDao");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<Travel> travels = travelDao.getAll();
            req.setAttribute("travels", travels);
            req.setAttribute("count", travelDao.getCount());
        } catch (DbException | SQLException e) {
            throw new RuntimeException(e);
        }
        getServletContext().getRequestDispatcher("/WEB-INF/views/travels/travelList.jsp").forward(req, resp);
    }
}
