package ru.kpfu.itis.util;

/* тут нужно использовать singletone, чтобы было только одно подключение к базе данных*/

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionProvider {
    private static ConnectionProvider _instance;

    public static ConnectionProvider getInstance() throws DbException {
        if (_instance == null) {
            _instance = new ConnectionProvider();
        }
        return _instance;
    }

    private Connection con;

    private ConnectionProvider() throws DbException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance(); //подключение драйвера
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/java3_travels", "root", "ksenia2005"); //подключение бд
        } catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            throw new DbException("Can't connect to DB", e);
        }
    }

    public Connection getCon() {
        return con;
    }
}
