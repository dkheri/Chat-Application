
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client {

    static ChatGUI cg;
    public static ObjectInputStream obin;
    public static ObjectOutputStream obout;
    Socket client=null;

    public Client() throws IOException {
        System.out.println("hERE");
        client= new Socket(Values.SERVER_IP_ADDRESS, (int) Values.SERVER_PORT_NUMBER);
        Client.obin = new ObjectInputStream(client.getInputStream());
        Client.obout = new ObjectOutputStream(client.getOutputStream());
        Client.obout.flush();
        System.out.println("In here");
        cg = new ChatGUI();
        cg.setVisible(true);
    }

    // public void listen() throws Exception {
    //     String incoming;
    //     while (!((incoming = in.readLine()).equals("Out"))) {
    //         System.out.println(incoming);
    //     }
    // }
    // public void run() {
    //     try {
    //         listen();
    //     } catch (Exception e) {
    //         e.getMessage();
    //     }
    // }
    public static void createMessage(String msg) throws IOException {
        cg.putToTA(msg);
        Message Username = new Message(Values.CONNECTIN_PROTOCOL, cg.getUserName());
        obout.writeObject(Username);
        //@DK MESSAGE 
        //SERVER WILL CHECK IF USER IS THER OR NOT IF YES PASS ON THE MESSAGE
//      	String dest,message;
//      	dest=Scanner.nextLine();
//      	message=Scanner.nextLine();
//      	Message msg=new Message("Text",dest,message);
    }

    public void sendMessage() {

        // out and send msg object to server if user is there it will pass it on if not it will discard it and prompt user to retry
    }

    public static void main(String[] args){
        try {
            Client c= new Client();
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
