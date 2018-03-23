
import java.util.Arrays;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Meluleki
 */
public class User {

    private final String username;
    private final char[] password;
    private String currentAdress;

    public User(String username, char[] pass) {
        this.username = username;
        this.password = pass;
    }

    public void setCurrentAdd(String add) {
        this.currentAdress = add;
    }

    public String getCurrentAdd() {
        return this.currentAdress;
    }

    boolean isCorrect(User u) {
        return ((u.username.equals(this.username)) && isPasswordCorrect(u.password));
    }

    private boolean isPasswordCorrect(char[] pass) {
        if (this.password.length != pass.length) {
            return false;
        } else {
            for (int i = 0; i < pass.length; i++) {
                if (pass[i] != this.password[i]) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return this.username + ", " + Arrays.toString(this.password);
    }
}
