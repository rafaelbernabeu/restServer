package models;

import play.db.jpa.*;
import javax.persistence.*;

/**
 * Created by rafael on 21/04/15.
 */
@Entity
public class Tag extends Model implements Comparable<Tag> {

    public String name;

    private Tag(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }

    public int compareTo(Tag otherTag) {
        return name.compareTo(otherTag.name);
    }

    public static Tag findOrCreateByName(String name) {
        Tag tag = Tag.find("byName", name).first();
        if(tag == null) {
            tag = new Tag(name);
        }
        return tag;
    }
}