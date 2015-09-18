package models;

import java.util.*;
import javax.persistence.*;

import org.hibernate.annotations.CollectionId;
import org.hibernate.annotations.Type;
import play.data.validation.Email;
import play.data.validation.Required;
import play.db.jpa.*;

@Entity
@Table(name="blog_user")
public class User extends Model {

    @Email
    @Required
    public String email;

    @Required
    public String password;

    @Required
    public String fullname;
    public boolean isAdmin;

    public User(String email, String password, String fullname) {
        this.email = email;
        this.password = password;
        this.fullname = fullname;
    }

    public User(){
        this.email = "";
        this.password = "";
        this.fullname = "";
    }

    public static User connect(String username, String password) {
        return find("byFullnameAndPassword", username, password).first();
    }
    public String toString() {
        return fullname;
    }
}