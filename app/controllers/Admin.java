package controllers;

import play.*;
import play.mvc.*;

import java.util.*;

import models.*;

@With(Secure.class)
public class Admin extends Controller {

    @Before
    static void setConnectedUser() {
        if(Security.isConnected()) {
            System.out.println("Quem está conectado? " + Security.connected().toString());
            User user = User.find("byFullname", Security.connected()).first();
            renderArgs.put("user", user.fullname);
        }
    }

    public static void index() {
        render();
    }

}