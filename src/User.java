<<<<<<< HEAD

import java.net.Socket;

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

    private String username;
    private String password;
    private Socket currentSocket;

    public User(String user) {
        this.username = user;
    }
    public void setCurrentSocket(Socket add){
        this.currentSocket=add;
    }
    public Socket getCurrentAdd(){
        return this.currentSocket;
    }
    boolean isCorrect(User u) {
        return ((u.username.equals(this.username)) && u.password.equals(this.password));
    }
    @Override
    public String toString(){
        return this.username+", "+this.password;
    }
}
=======
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

    private String username;
    private String password;
    private String currentAdress;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
    public void setCurrentAdd(String add){
        this.currentAdress=add;
    }
    public String getCurrentAdd(){
        return this.currentAdress;
    }
    boolean isCorrect(User u) {
        return ((u.username.equals(this.username)) && u.password.equals(this.password));
    }
    @Override
    public String toString(){
        return this.username+", "+this.password;
    }
}
>>>>>>> refs/remotes/origin/master
