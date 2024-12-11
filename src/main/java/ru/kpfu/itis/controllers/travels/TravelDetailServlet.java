package ru.kpfu.itis.controllers.travels;

import ru.kpfu.itis.dao.TravelDao;
import ru.kpfu.itis.entities.Travel;
import ru.kpfu.itis.entities.User;
import ru.kpfu.itis.util.DbException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/travel/detail")
public class TravelDetailServlet extends HttpServlet {

    private TravelDao travelDao;

    @Override
    public void init() throws ServletException {
        super.init();
        this.travelDao = (TravelDao) getServletContext().getAttribute("travelDao");

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try{
            String travel_id = req.getParameter("travel_id");

            if (travel_id == null) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().println("Bad request. No travel_id or route_id has been provided.");
                return;
            }
            Travel travel = travelDao.getTravelById(Integer.parseInt(travel_id));
            if (travel != null) {
                // Устанавливаем атрибуты запроса для JSP
                req.setAttribute("travel", travel);
               // req.setAttribute("route", route);

            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                getServletContext().getRequestDispatcher("/WEB-INF/views/errors/not_found.jsp").forward(req, resp);

            }
            //req.setAttribute("travel", travel);
            getServletContext().getRequestDispatcher("/WEB-INF/views/travels/travel.jsp").forward(req, resp);

        } catch (SQLException | DbException e) {
            throw new RuntimeException(e);
        }


        /*
        try {
            // Получаем travel_id из параметров запроса
            String travelIdParam = req.getParameter("travel_id");

            // Получаем объект путешествия по travel_id
            Travel travel = travelDao.getTravelById(Integer.parseInt(travelIdParam));

            if (travel != null) {
                // Получаем объект маршрута по route_id, который связан с этим путешествием
                Route route = routeDao.getRouteById(travel.getRoute_id());

                // Устанавливаем атрибуты запроса для JSP
                req.setAttribute("travel", travel);
                req.setAttribute("route", route);
            }

            // Перенаправляем на страницу travel/detail.jsp
            getServletContext().getRequestDispatcher("/WEB-INF/views/travel/detail.jsp").forward(req, resp);

        } catch (DbException | NumberFormatException e) {
            // Обработка ошибок
            //e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unable to load travel details.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

         */

/*        try {
            String travel_id = req.getParameter("travel_id");
            if (travel_id == null) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().println("Bad request. No id has been provided.");
            }
            Travel travel = travelDao.getTravelById(Integer.parseInt(travel_id));
            //мы обращаемся к BookDao и если он говорит, что книга не найдена, то даем 404 статусный код
            if (travel == null) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                getServletContext().getRequestDispatcher("/WEB-INF/views/errors/notfound.jsp").forward(req, resp);
            }
            req.setAttribute("travel", travel);
            getServletContext().getRequestDispatcher("/WEB-INF/views/travels/travel.jsp").forward(req, resp);
        } catch (DbException e) {
            throw new ServletException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }*/

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        int travel_id = Integer.parseInt(req.getParameter("travel_id"));

        // Проверяем, если действие - это удаление аккаунта
        if ("delete".equals(action)) {
            try {
                boolean isDeleted = travelDao.deleteTravel(travel_id);
                if (isDeleted) {
                    resp.sendRedirect(req.getContextPath() + "/travel/list");
                } else {
                    req.setAttribute("message", "Не удалось удалить аккаунт. Попробуйте позже.");
                    resp.sendRedirect(req.getContextPath() + "/travel/detail?travel_id=" + travel_id);
                }
            } catch (DbException e) {
                req.setAttribute("message", "Ошибка при удалении путешествия. Попробуйте позже.");
                //getServletContext().getRequestDispatcher("/WEB-INF/views/profile.jsp").forward(req, resp);
            }
        }
        else {
            resp.sendRedirect(req.getContextPath() + "/travel/detail?travel_id=" + travel_id);
        }
    }
}
