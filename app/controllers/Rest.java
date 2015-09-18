package controllers;

import models.User;
import play.mvc.Controller;
import play.mvc.With;

import java.util.List;

/**
 * Created by rafael on 12/04/15.
 */
public class Rest extends Controller {

    public static void listar(){
        List<User> authors = User.findAll();
        renderJSON(authors);
    }

    public static void salvar(Long authorId, String email, String password, String fullname){
        if (authorId == null) {
            User author = new User();
            author.email = email;
            author.password = password;
            author.fullname = fullname;
            author.save();
            listar();
        } else {
            User author = User.findById(authorId);
            author.email = email;
            author.password = password;
            author.fullname = fullname;
            author.save();
            listar();
        }
    }

    public static void excluir(Long authorId){
        User user = User.findById(authorId);
        user.delete();
        listar();
    }
}
