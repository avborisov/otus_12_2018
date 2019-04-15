package ru.otus.borisov.hibernate.main;

import com.google.gson.Gson;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;
import ru.otus.borisov.hibernate.dbService.hbrn.UserHibernateDbService;
import ru.otus.borisov.hibernate.servlet.LoginServlet;
import ru.otus.borisov.hibernate.servlet.UserServlet;
import ru.otus.borisov.hibernate.servlet.UserTotalServlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Main {
    private final static int PORT = 8080;
    private final static String STATIC = "/static";

    public static void main(String[] args) throws Exception {
        new Main().start();
    }

    private void start() throws Exception {
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

        ResourceHandler resourceHandler = new ResourceHandler();
        Resource resource = Resource.newClassPathResource(STATIC);
        resourceHandler.setBaseResource(resource);

        UserHibernateDbService db = new UserHibernateDbService();
        Gson gson = new Gson();

        context.addServlet(new ServletHolder(new HttpServlet() {
            @Override
            protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
                RequestDispatcher view = req.getRequestDispatcher("/index.html");
                view.forward(req, resp);
            }
        }), "/index.html");

        context.addServlet(new ServletHolder(new UserServlet(db, gson)), "/user");
        context.addServlet(new ServletHolder(new UserTotalServlet(db)), "/userTotal");
        context.addServlet(new ServletHolder(new LoginServlet(db, gson)), "/login");

        Server server = new Server(PORT);
        server.setHandler(new HandlerList(resourceHandler, context));

        server.start();
        server.join();
    }
}
