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
import java.sql.Date;
import java.sql.SQLException;

@WebServlet("/travel/update")
public class TravelUpdateServlet extends HttpServlet {

    private TravelDao travelDao;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.travelDao = (TravelDao) config.getServletContext().getAttribute("travelDao");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try{
            int travel_id = Integer.parseInt(req.getParameter("travel_id"));

            Travel travel = travelDao.getTravelById(travel_id);
            if (travel != null) {
                req.setAttribute("travel", travel);
                getServletContext().getRequestDispatcher("/WEB-INF/views/travels/travelUpdate.jsp").forward(req, resp);
            }
            else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                getServletContext().getRequestDispatcher("/WEB-INF/views/errors/not_found.jsp").forward(req, resp);
            }

        } catch (SQLException | DbException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int travelId = Integer.parseInt(req.getParameter("travel_id"));
            String nameTravel = req.getParameter("name_travel");
            String description = req.getParameter("description");
            Date startDate = Date.valueOf(req.getParameter("start_date"));
            Date endDate = Date.valueOf(req.getParameter("end_date"));
            String transport = req.getParameter("transport");
            String listOfThings = req.getParameter("list_of_things");
            String notes = req.getParameter("notes");

            Travel travel = new Travel(travelId, 0, nameTravel, description, startDate, endDate, transport, listOfThings, notes);
            boolean updated = travelDao.updateTravel(travel);

            if (updated) {
                resp.sendRedirect(req.getContextPath() + "/travel/detail?travel_id=" + travelId);
            } else {
                req.setAttribute("message", "Не удалось обновить путешествие");
                getServletContext().getRequestDispatcher("/WEB-INF/views/travels/travelUpdate.jsp").forward(req, resp);
            }
        } catch (Exception e) {
            throw new ServletException("Ошибка при обновлении путешествия", e);
        }
    }
}
