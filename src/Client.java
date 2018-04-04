
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class Client implements Runnable {

    static ChatGUI cg;
    public static ObjectInputStream obin;
    public static ObjectOutputStream obout;
    static Socket client;
    static Client c;

    static void disconnect() {
        try {
            String tyString = Values.DISCONNECT_PROTOCOL;
            String mesString = "";
            Message m = new Message(tyString, Values.SERVER_USER_NAME, cg.getUserName(), "");
            obout.writeObject(m);
            obin.close();
            obout.close();
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public Client() throws IOException {

        cg = new ChatGUI();

        cg.setVisible(true);
    }

    public static void connect(String serverAdress) {
        try {
            client = new Socket(serverAdress, (int) Values.SERVER_PORT_NUMBER);
            Client.obin = new ObjectInputStream(client.getInputStream());
            Client.obout = new ObjectOutputStream(client.getOutputStream());
            Client.obout.flush();
            Thread t = new Thread(c);
            t.start();
            Message Username = new Message(Values.CONNECTIN_PROTOCOL, cg.getUserName());
            obout.writeObject(Username);
            System.out.println();
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static boolean empty(final String s) {
        return s == null || s.trim().isEmpty();
    }

    public static void sendMessageLogin(Message msg) {
        try {
            obout.writeObject(msg);
            obout.flush();
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void sendMessage(String to, String from,String message, List l, File f) {
        Message SentMsg;
        try {
            String messageTxt = message;
            String sender = from;
            List userList = l;
            File fileObj = f;
            boolean fileValid = false;

            if (userList.size() == 1) {

                String recipent = (String) userList.get(0);

                if (fileObj == null) {

                    SentMsg = new Message(Values.TEXT_PROTOCOL, recipent, sender, messageTxt);

                } else {

                    SentMsg = new Message(Values.FILE_PROTOCOL, recipent, sender, messageTxt);
                    SentMsg.file = fileObj;
                    fileValid = fileValidation(fileObj);
                    SentMsg.fileName = fileObj.getName();
                }

            } else {
                // SentMsg = new Message(Values.BRODCAST_PROTOCOL, "null", sender, messageTxt);

                if (fileObj == null) {

                    SentMsg = new Message(Values.BRODACAST_TEXT_PROTOCOL, "null", sender, messageTxt);

                } else {

                    SentMsg = new Message(Values.BRODCAST_FILE_PROTOCOL, "null", sender, messageTxt);
                    SentMsg.file = fileObj;
                    SentMsg.fileName = fileObj.getName();
                    fileValid = fileValidation(fileObj);
                }

                SentMsg.obMessage = userList;
            }

            if (fileObj == null) {
                obout.writeObject(SentMsg);
            } else if (fileValid) {
                // obout.writeObject(SentMsg);
                Thread uploadThread = new Thread(new Upload(SentMsg));
                uploadThread.start();

            }
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
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
            int response = JOptionPane.showConfirmDialog(null, msg.message + "\nDo you want to receive the file?");
            Message newMessage = new Message(Values.FILE_REQUEST_RESPONSE, Values.SERVER_USER_NAME, msg.recipent, msg.obMessage);
            newMessage.fileNumber=msg.fileNumber;
            if (response == 0) {
                newMessage.message = Values.FILE_REQUEST_YES;
            } else {
                newMessage.message = Values.FILE_REQUEST_NO;
            }
            obout.writeObject(newMessage);
            obout.flush();
        }
        if (msg.mType.equals(Values.FILE_PROTOCOL)) {
            Thread a = new Thread(new Download(msg.file, (String) msg.fileName));
            a.start();
        }
        if (msg.mType.equals(Values.LOGIN_RESPONSE_PROTOCOL)) {
            if (msg.message.equals(Values.LOGIN_RESPONSE_PROTOCOL_YES)) {
                JOptionPane.showMessageDialog(null, "You are online.");
            } else {
                JOptionPane.showMessageDialog(null, "Authentication failed");
                cg.resetThings();
            }
        }
        if (msg.mType.equals(Values.SIGN_UP_RESPONSE_PROTCOL)) {
            if (msg.message.equals(Values.SIGN_UP_RESPONSE_PROTCOL_DONE)) {
                JOptionPane.showMessageDialog(null, "You are signed up with your user and password");
            } else {
                JOptionPane.showMessageDialog(null, "Your credentials have been updated");
            }
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
