
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client implements Runnable{

    static ChatGUI cg;
    public static ObjectInputStream obin;
    public static ObjectOutputStream obout;
    Socket client;

    public Client() throws IOException {
        
        cg = new ChatGUI();

        cg.setVisible(true);
    }


    public static void connect()
    {

        client = new Socket(Values.SERVER_IP_ADDRESS, (int) Values.SERVER_PORT_NUMBER);
        Client.obin = new ObjectInputStream(client.getInputStream());
        Client.obout = new ObjectOutputStream(client.getOutputStream());
        Client.obout.flush();

    }
    


    public static receiveMessage(Message msg)
    {

        Message msg=msg;
        if(msg.mType.equals(Values.TEXT_PROTOCOL))
        {
            String textMessage=msg.message;
            String sender=msg.sender;
            String recipent=msg.recipent;
            String displayMessage="["+sender+" to "+recipent+"]:"+msg;
            UpdateTextArea(displayMessage);
        }

    }


    public static void login()
    {

        Message Username = new Message(Values.CONNECTIN_PROTOCOL, cg.getUserName());
        obout.writeObject(Username);

    }

    
    public void run() {
        try {
            
            Message incoming;

            while(true)
                {   
                    incoming=(Message)obout.readObject();        
                    receiveMessage(incoming);
                }
                
        } 

        catch (Exception e) {
            e.getMessage();
        }

    }




    public static void UpdateTextArea(String displayMessage) throws IOException {
        
        
        cg.putToTA(displayMessage);
        

        
        



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

    public static void main(String[] args) {
        try {
            Client c = new Client();
            Thread t=new Thread(c);
            t.start();


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
