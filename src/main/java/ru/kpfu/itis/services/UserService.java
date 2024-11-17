package ru.kpfu.itis.services;

import ru.kpfu.itis.dao.UserDao;
import ru.kpfu.itis.entities.User;
import ru.kpfu.itis.util.DbException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//должны быть методы для регистрации, входа в систему и удаления пользователя

public class UserService {

    private UserDao userDao;

    public void register(User user, HttpServletRequest req, HttpServletResponse resp) throws DbException {
        req.getSession().setAttribute("user", user);
    }

    public void authUser(User user, HttpServletRequest req, HttpServletResponse resp) throws DbException {
        req.getSession().setAttribute("user", user); //Установка атрибута user в сессии
    }

    public void deleteUser(User user, HttpServletRequest req, HttpServletResponse resp) throws DbException {
        req.getSession().removeAttribute("user");
        //return userDao.deleteUser(user.getId());
    }

    public void signout(HttpServletRequest req) {
        if (req.getSession() != null) {
            req.getSession().invalidate();
        }
    }

//    public void auth(User user, HttpServletRequest req, HttpServletResponse resp) {
//        req.getSession().setAttribute("user", user);
//    }
//
    public boolean isNonAnonymous(HttpServletRequest req, HttpServletResponse resp) {
        return req.getSession().getAttribute("user") != null;
    }
//
    public User getUser(HttpServletRequest req, HttpServletResponse res) {
        return (User) req.getSession().getAttribute("user");
    }
}
