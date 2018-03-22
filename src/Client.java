
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

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
        obout.writeObject(m);
        obin.close();
        obout.close();
        TimeUnit.SECONDS.sleep(3);

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

    public static boolean empty(final String s) {
        return s == null || s.trim().isEmpty();
    }

    public static void sendMessage() throws IOException {
        Message SentMsg;
        String messageTxt = cg.getSendTextArea();
        String sender = cg.getUserName();
        List userList = cg.getSelectedUser();
        File fileObj = cg.getFile();
        boolean fileValid = false;

        if (userList.size() == 1) {

            String recipent = (String) userList.get(0);

            if (fileObj == null) {

                SentMsg = new Message(Values.TEXT_PROTOCOL, recipent, sender, messageTxt);

            } else {

                SentMsg = new Message(Values.FILE_PROTOCOL, recipent, sender, messageTxt);
                SentMsg.file = fileObj;
                fileValid = fileValidation(fileObj);
            }

        } else {
            // SentMsg = new Message(Values.BRODCAST_PROTOCOL, "null", sender, messageTxt);

            if (fileObj == null) {

                SentMsg = new Message(Values.BRODACAST_TEXT_PROTOCOL, "null", sender, messageTxt);

            } else {

                SentMsg = new Message(Values.BRODCAST_FILE_PROTOCOL, "null", sender, messageTxt);
                SentMsg.file = fileObj;
                fileValid = fileValidation(fileObj);
            }

            SentMsg.obMessage = userList;
        }

        if (fileObj == null) {
            obout.writeObject(SentMsg);
        } else if (fileValid) {
            // obout.writeObject(SentMsg);
            Thread uploadThread=new Thread(new Upload(SentMsg));
            uploadThread.start();


        }

        // if(userList.size()==1)
        // {
        //     String recipent=(String)userList.get(0);
        //     SentMsg = new Message(Values.TEXT_PROTOCOL, recipent, sender, messageTxt);
        // }
        // else
        // {
        //     SentMsg = new Message(Values.BRODCAST_PROTOCOL, "null", sender, messageTxt);
        //     SentMsg.obMessage=userList;
        // }
        //     obout.writeObject(SentMsg);
        //     String SRInfo ="[" + SentMsg.sender + " to " + SentMsg.recipent + "]:";
        //     String displayMessage=SRInfo+SentMsg.message+"\n";
        //     UpdateTextArea(displayMessage);
    }

    public static void receiveMessage(Message msg) throws IOException {

        if (msg.mType.equals(Values.TEXT_PROTOCOL)) {
            //Works
            String textMessage = msg.message;
            String sender = msg.sender;
            String recipent = msg.recipent;
            String SRInfo = "[" + sender + " to " + recipent + "]:";
            String displayMessage = SRInfo + textMessage + "\n";
            UpdateTextArea(displayMessage);
        }

        if (msg.mType.equals(Values.OBJECTTYPE_LIST_PROTOCOL)) {
            // ArrayList<String> x=(ArrayList<String>)msg.obMessage;
            ArrayList<String> x = new ArrayList<>();
            x.add("All");
            for (String user : (ArrayList<String>) msg.obMessage) {
                x.add(" " + user);
            }
            cg.populateListView((ArrayList<String>) msg.obMessage);

        }

        if (msg.mType.equals(Values.REQUEST_FILE_PROTOCOL)) {
            int response = JOptionPane.showConfirmDialog(null, msg.message + "Do you want to receiver the file?");
            Message newMessage = new Message(Values.FILE_REQUEST_RESPONSE, Values.SERVER_USER_NAME, msg.recipent, msg.obMessage);
            if (response == 0) {
                newMessage.message = Values.FILE_REQUEST_YES;
            } else {
                newMessage.message = Values.FILE_REQUEST_NO;
            }
            obout.writeObject(newMessage);
            obout.flush();
        }
        if (msg.mType.equals(Values.FILE_PROTOCOL)) {
            Thread a= new Thread(new Download(msg.obMessage));
            a.start();
        }            
    }

    public static void login() throws IOException {

        Message Username = new Message(Values.CONNECTIN_PROTOCOL, cg.getUserName());
        obout.writeObject(Username);

    }

    public static boolean fileValidation(File file) {

        double sizeMb = (file.length()) / (1024 * 1024);
        double limit = 5.0;
        if (Double.compare(sizeMb, limit) > 0) {
            JOptionPane.showMessageDialog(null, "File is too large to be sent!\n File not sent!!!");
            return false;
        } else {
            return true;
        }

    }

    public void run() {
        try {

            Message incoming;

            while (true) {
                incoming = (Message) obin.readUnshared();
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
