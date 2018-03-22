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
    private final String password;
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
    public static void main(String[] args){
        String a="";
        if(a==null){
            System.out.println("Null");
        }
    }
}
