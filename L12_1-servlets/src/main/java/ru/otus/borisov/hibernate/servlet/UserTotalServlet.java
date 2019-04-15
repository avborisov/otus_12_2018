package ru.otus.borisov.hibernate.servlet;

import com.google.gson.Gson;
import ru.otus.borisov.hibernate.base.dataSets.UserDataSet;
import ru.otus.borisov.hibernate.dbService.hbrn.UserHibernateDbService;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserTotalServlet extends HttpServlet {

    private final UserHibernateDbService userDbService;

    public UserTotalServlet(UserHibernateDbService userDbService) {
        this.userDbService = userDbService;
    }

    @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ServletOutputStream out = resp.getOutputStream();
        out.print(userDbService.count());
    }

}
