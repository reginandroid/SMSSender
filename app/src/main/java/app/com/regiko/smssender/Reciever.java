package app.com.regiko.smssender;

/**
 * Created by Ковтун on 26.02.2018.
 */

public class Reciever {
   private String name;
    private String phone;
    private String email;

    public Reciever() {
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Reciever(String phone) {

        this.phone = phone;
    }

    public Reciever(String name, String phone) {

        this.name = name;
        this.phone = phone;

    }
}
