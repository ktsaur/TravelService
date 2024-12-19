package ru.kpfu.itis.services;

import ru.kpfu.itis.dao.UserDaoImpl;
import ru.kpfu.itis.entities.User;
import ru.kpfu.itis.util.DbException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserService {
    private UserDaoImpl userDao;

    public void register(User user, HttpServletRequest req, HttpServletResponse resp) throws DbException {
        req.getSession().setAttribute("user", user);
    }

    public void authUser(User user, HttpServletRequest req, HttpServletResponse resp) throws DbException {
        req.getSession().setAttribute("user", user);
    }

    public void deleteUser(User user, HttpServletRequest req, HttpServletResponse resp) throws DbException {
        req.getSession().removeAttribute("user");
    }

    public void signout(HttpServletRequest req) {
        if (req.getSession() != null) {
            req.getSession().invalidate();
        }
    }

    public boolean isNonAnonymous(HttpServletRequest req, HttpServletResponse resp) {
        return req.getSession().getAttribute("user") != null;
    }

    public User getUser(HttpServletRequest req, HttpServletResponse res) {
        return (User) req.getSession().getAttribute("user");
    }
}
