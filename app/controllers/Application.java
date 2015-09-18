package controllers;

import play.*;
import play.cache.*;
import play.data.validation.Required;
import play.libs.*;
import play.mvc.*;

import java.util.*;

import models.*;


public class Application extends Controller {


    @Before
    static void addDefaults() {
        renderArgs.put("blogTitle", Play.configuration.getProperty("blog.title"));
        renderArgs.put("blogBaseline", Play.configuration.getProperty("blog.baseline"));
    }

    public static void index() {
        Post frontPost = Post.find("order by postedAt desc").first();
        System.out.println(frontPost.content);
        List<Post> olderPosts = Post.find("order by postedAt desc").from(0).fetch(10);
        render(frontPost, olderPosts);
    }


    public static void show(Long id) {
        Post post = Post.findById(id);
        String randomID = Codec.UUID();
        render(post, randomID);
    }

    public static void postComment(Long postId,
            @Required(message="Author is required") String author,
            @Required(message="A message is required") String content,
            @Required(message="Please type the code") String code,
            String randomID) {
        checkAuthenticity();
        Post post = Post.findById(postId);
        validation.equals(code, Cache.get(randomID)).message("Invalid code. Please type it again");
        if(validation.hasErrors()) {
            render("Application/show.html", post, randomID);
        }
        post.addComment(author, content);
        flash.success("Thanks for posting %s", author);
        Cache.delete(randomID);
        show(postId);
    }

    public static void captcha(String id) {
        Images.Captcha captcha = Images.captcha();
        captcha.addNoise("#0000FF");
        String code = captcha.getText("#FF0000");
        Cache.set(id, code, "10mn");
        renderBinary(captcha);
    }

    public static void cadastro(Long authorId) {
        if (authorId == null){
            User author = new User();
            render(author);
        } else {
            User author = User.findById(authorId);
            render(author);
        }
    }

    public static void listar(){
        List<User> authors = User.findAll();
        render(authors);
    }

    public static void salvar(Long authorId, String email, String password, String fullname){
        checkAuthenticity();
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
        checkAuthenticity();
        User user = User.findById(authorId);
        user.delete();
        listar();
    }

    public static void syrena(){
        render("Syrena/index.html");
    }

    public static void messages(){
        render("Syrena/messages.html");
    }



}