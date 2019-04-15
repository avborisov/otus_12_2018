package ru.otus.borisov.hibernate.servlet;

import com.google.gson.Gson;
import ru.otus.borisov.hibernate.dbService.hbrn.UserHibernateDbService;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginServlet extends HttpServlet {

    private static final String APPLICATION_JSON = "application/json;charset=UTF-8";
    private final UserHibernateDbService userDbService;
    private final Gson gson;

    public LoginServlet(UserHibernateDbService userDbService, Gson gson) {
        this.userDbService = userDbService;
        this.gson = gson;
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

    }

}
