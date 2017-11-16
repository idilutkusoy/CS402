package tr.edu.ozu.ozunaviclient;

import java.io.Serializable;

/**
 * Created by idilutkusoy on 19.10.2017.
 */

public class Subscriber implements Serializable{

    private int id;
    private String name;
    private String lastname;
    private String gender;
    private int age;
    private String mail;

    public Subscriber(int id, String name, String lastname, String gender, int age, String mail) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.gender = gender;
        this.age = age;
        this.mail = mail;
    }

    public String getGender() {
        return gender;
    }

    public int getAge() {
        return age;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLastname() {
        return lastname;
    }

    public String getMail() {
        return mail;
    }
}
