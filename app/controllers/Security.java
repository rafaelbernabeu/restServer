package controllers;

import models.*;

public class Security extends Secure.Security {

    static boolean authenticate(String username, String password) {
        checkAuthenticity();
        return User.connect(username, password) != null;
    }

    static void onDisconnected() {
        Application.index();
    }

    static void onAuthenticated() {
        Admin.index();
    }

    static boolean check(String profile) {
        if("admin".equals(profile)) {
            return User.find("byFullname", connected()).<User>first().isAdmin;
        }
        return false;
    }
}