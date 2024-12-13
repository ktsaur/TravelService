package ru.kpfu.itis.controllers.travels;


import ru.kpfu.itis.dao.TravelDao;
import ru.kpfu.itis.entities.Travel;
import ru.kpfu.itis.services.UserService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;


@WebServlet("/travel/create")
public class TravelCreateServlet extends HttpServlet {

    private TravelDao travelDao;
    private UserService userService;


    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.travelDao = (TravelDao) config.getServletContext().getAttribute("travelDao");
        this.userService = (UserService) getServletContext().getAttribute("userService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/WEB-INF/views/travels/travelCreate.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int user_id = userService.getUser(req, resp).getId();
        String name_travel = req.getParameter("name_travel");
        String description = req.getParameter("description");;
        Date start_date = Date.valueOf(req.getParameter("start_date"));
        Date end_date = Date.valueOf(req.getParameter("end_date"));
        String transport  = req.getParameter("transport");
        String list_of_things = req.getParameter("list_of_things");
        String notes = req.getParameter("notes"); //доп информация

        if (name_travel == null || description == null || start_date == null || end_date == null
        || transport == null || list_of_things == null || notes == null) {
            req.setAttribute("message", "Все поля должны быть заполнены");
            getServletContext().getRequestDispatcher("/WEB-INF/views/travels/travelCreate.jsp").forward(req, resp);
            return;
        }
        Travel newTravel = new Travel(user_id, name_travel, description, start_date, end_date, transport, list_of_things, notes);
        int travel_id = travelDao.createTravel(newTravel);

        req.setAttribute("message", "Новое путешествие создано!");
        resp.sendRedirect(req.getContextPath() + "/travel/detail?travel_id=" + travel_id);
    }
}
