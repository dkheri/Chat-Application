
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client implements Runnable {
    
    static ChatGUI cg;
    public static ObjectInputStream obin;
    public static ObjectOutputStream obout;
    static Socket client;
    static Client c;
    
    static void disconnect() throws IOException, InterruptedException {
        String tyString = Values.DISCONNECT_PROTOCOL;
        String mesString = "";
        Message m = new Message(tyString, mesString);
//        for(int i=0;i<50000;i++ ){
//            for(int j=0;j<50000;j++){
//                int b=i+j;
//            }
//        }
        obout.writeObject(m);

    
        System.out.println("sent");
    obin.close();
    obout.close();

    }
    
    public Client() throws IOException {
        
        cg = new ChatGUI();
        
        cg.setVisible(true);
    }
    
    public static void connect(String serverAdress) throws IOException {
        
        client = new Socket(serverAdress, (int) Values.SERVER_PORT_NUMBER);
        Client.obin = new ObjectInputStream(client.getInputStream());
        Client.obout = new ObjectOutputStream(client.getOutputStream());
        Client.obout.flush();
        Thread t = new Thread(c);
        t.start();
        Message Username = new Message(Values.CONNECTIN_PROTOCOL, cg.getUserName());
        obout.writeObject(Username);
        System.out.println();
    }
    
    public static void sendMessage() throws IOException {
        
        String messageTxt = cg.getSendTextArea();
        String recipent = cg.getSelectedUser();
        String sender = cg.getUserName();
        Message SentMsg = new Message(Values.TEXT_PROTOCOL, recipent, sender, messageTxt);
        obout.writeObject(SentMsg);
        
    }
    
    public static void receiveMessage(Message msg) throws IOException {
        
            System.out.println("Received message was called()");

        if (msg.mType.equals(Values.TEXT_PROTOCOL)) {
            String textMessage = msg.message;
            String sender = msg.sender;
            String recipent = msg.recipent;
            String displayMessage = "[" + sender + " to " + recipent + "]:" + textMessage;
            UpdateTextArea(displayMessage);
        }
        
         if (msg.mType.equals(Values.OBJECTTYPE_LIST_PROTOCOL)) 
         {

            ArrayList<String> x=(ArrayList<String>)msg.obMessage;
            x.add("all");
            System.out.println("Received Array obj");
            cg.populateListView(x);
         }
        


    }
    
    public static void login() throws IOException {
        
        Message Username = new Message(Values.CONNECTIN_PROTOCOL, cg.getUserName());
        obout.writeObject(Username);
        
    }
    


    public void run() {
        try {
            
            Message incoming;
            
            while (true) {
                System.out.println("I get the msg object");
                incoming = (Message) obin.readObject();
                System.out.println("readUnshared called");
                receiveMessage(incoming);
            }
            
        } catch (IOException | ClassNotFoundException e) {
            e.getMessage();
        }
        
    }
    
    public static void UpdateTextArea(String displayMessage) throws IOException {
        
        cg.putToTA(displayMessage);

        //@DK MESSAGE 
        //SERVER WILL CHECK IF USER IS THER OR NOT IF YES PASS ON THE MESSAGE
//          String dest,message;
//          dest=Scanner.nextLine();
//          message=Scanner.nextLine();
//          Message msg=new Message("Text",dest,message);
    }
    
    public static void main(String[] args) {
        try {
            c = new Client();

            // ArrayList<String> r = new ArrayList<>();
            // for (int i = 0; i < 5; i++) {
            //     r.add("Mellar");
            // }
            // Client.cg.populateListView(r);
            // System.out.println(Arrays.toString(r.toArray()));
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
