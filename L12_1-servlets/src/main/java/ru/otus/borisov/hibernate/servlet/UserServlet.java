package ru.otus.borisov.hibernate.servlet;

import com.google.gson.Gson;
import ru.otus.borisov.hibernate.base.dataSets.UserDataSet;
import ru.otus.borisov.hibernate.dbService.hbrn.UserHibernateDbService;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserServlet extends HttpServlet {

    private static final String APPLICATION_JSON = "application/json;charset=UTF-8";
    private final UserHibernateDbService userDbService;
    private final Gson gson;

    public UserServlet(UserHibernateDbService userDbService, Gson gson) {
        this.userDbService = userDbService;
        this.gson = gson;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String idParam = req.getParameter("id");
        try {
            Long id = Long.parseLong(idParam);
            UserDataSet user = userDbService.read(id);
            resp.setContentType(APPLICATION_JSON);
            ServletOutputStream out = resp.getOutputStream();
            out.print(gson.toJson(user));
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            String userName = req.getParameter("name");
            Integer age = Integer.parseInt(req.getParameter("age"));
            UserDataSet user = new UserDataSet();
            user.setAge(age);
            user.setName(userName);
            userDbService.create(user);
            resp.setStatus(HttpServletResponse.SC_CREATED);
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
