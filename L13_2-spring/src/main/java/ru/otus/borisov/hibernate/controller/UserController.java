package ru.otus.borisov.hibernate.controller;

import com.google.gson.Gson;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import ru.otus.borisov.hibernate.base.dataSets.UserDataSet;
import ru.otus.borisov.hibernate.dbService.DBService;
import ru.otus.borisov.hibernate.dbService.hbrn.UserHibernateDbService;

@Controller
public class UserController {

    private static final String APPLICATION_JSON = "application/json;charset=UTF-8";
    private final DBService<UserDataSet> userDbService;
    private final Gson gson;

    public UserController() {
        userDbService = new UserHibernateDbService();
        gson = new Gson();
    }

    public UserController(UserHibernateDbService userDbService, Gson gson) {
        this.userDbService = userDbService;
        this.gson = gson;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "/static/index.html";
    }

    @RequestMapping(value= "/get/{userId}", method = RequestMethod.GET, produces = APPLICATION_JSON)
    @ResponseBody
    protected String getUser(@PathVariable long userId) {
        UserDataSet user = userDbService.read(userId);
        return gson.toJson(user);
    }

    @RequestMapping(value= "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseBody
    protected void createUser(@RequestBody MultiValueMap<String, String> formData){
        UserDataSet user = new UserDataSet();
        user.setAge(Integer.parseInt(formData.getFirst("age")));
        user.setName(formData.getFirst("name"));
        userDbService.create(user);
    }

    @RequestMapping(value= "/count", method = RequestMethod.GET)
    @ResponseBody
    protected String getUsersCount() {
        return gson.toJson(userDbService.count());
    }
}
