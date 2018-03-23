/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Meluleki
 */
public class AcceptClient extends Thread {

    public Socket clientSocket;
    public ObjectInputStream obin;
    public ObjectOutputStream obout;
    private boolean cont;

    public AcceptClient(Socket cs) throws IOException {
        this.cont = true;
        this.clientSocket = cs;
        this.clientSocket.setKeepAlive(true);
        this.obout = new ObjectOutputStream(cs.getOutputStream());
        obout.flush();
        this.obin = new ObjectInputStream(cs.getInputStream());
        CServer.messageBuffer = new ArrayList<>();
        start();

    }

    @Override
    public void run() {
        while (this.cont) {
            Message msgFromClien;
            try {
                msgFromClien = (Message) obin.readObject();
                recMessage(msgFromClien);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage() + "\n" + ex.getCause());
            } catch (ClassNotFoundException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage() + "\n" + ex.getCause());
            }

        }
    }

    private void sendMessage(Message msg, int ortIndex) {
        try {
//            System.out.println(msg);
            CServer.outputstreams.get(ortIndex).reset();
            CServer.outputstreams.get(ortIndex).writeUnshared(msg);
            CServer.outputstreams.get(ortIndex).flush();
        } catch (IOException ex) {
            System.out.println("Error");
        }
    }

    public void sendMessage(Message msg, ArrayList<String> listofuser) {
        for (String list : listofuser) {
            for (String loggedin : CServer.loginNames) {
                if (list.equals(loggedin)) {
                    Message m = new Message(Values.TEXT_PROTOCOL, loggedin, msg.sender, msg.message);
                    int i = CServer.loginNames.indexOf(loggedin);
                    sendMessage(m, i);
                }
            }
        }
    }

    public void updateLists(Message msg) {
        int i = 0;
        for (; i < CServer.loginNames.size(); i++) {
            sendMessage(msg, i);
        }
    }

    public void recMessage(Message msg) {
        switch (msg.mType) {
            case Values.CONNECTIN_PROTOCOL: {

                break;
            }
            case Values.TEXT_PROTOCOL: {
                String rec = msg.recipent;
                int sckNumber;
                for (String s : (ArrayList<String>) CServer.loginNames) {
                    if (s.equals(rec)) {
                        sckNumber = ((ArrayList<String>) CServer.loginNames).indexOf(s);
                        sendMessage(msg, sckNumber);
                        break;
                    }
                }
                break;
            }
            case Values.BRODCAST_PROTOCOL: {

                sendMessage(msg, (ArrayList<String>) msg.obMessage);
                break;
            }
            case Values.FILE_PROTOCOL: {
                Message newMsge = new Message(Values.REQUEST_FILE_PROTOCOL, msg.recipent, Values.SERVER_USER_NAME, msg.message);
                newMsge.obMessage = (Object) CServer.messageBuffer.size();
                CServer.messageBuffer.add(msg);
                CServer.REQUEST_PENDING++;
                for (String s : CServer.loginNames) {
                    if (s.equals(newMsge.recipent)) {
                        sendMessage(newMsge, CServer.loginNames.indexOf(s));
                        break;
                    }
                }
                break;
            }
            case Values.FILE_REQUEST_RESPONSE: {
//                System.out.println(msg);
                CServer.REQUEST_PENDING--;
                if (msg.message.equals(Values.FILE_REQUEST_YES)) {
                    int messageBuff = (Integer) msg.obMessage;
                    Message newMsge = CServer.messageBuffer.get(messageBuff);
                    newMsge.message = "";
                    for (String s : CServer.loginNames) {
                        if (s.equals(newMsge.recipent)) {
                            sendMessage(newMsge, CServer.loginNames.indexOf(s));
                            break;
                        }
                    }
                }
                if (CServer.REQUEST_PENDING == 0) {
                    CServer.messageBuffer.clear();
                }
            }
            case Values.DISCONNECT_PROTOCOL: {
                CServer.removeClient(msg.sender);
                Message a = new Message(Values.OBJECTTYPE_LIST_PROTOCOL, msg.sender, Values.SERVER_USER_NAME, CServer.loginNames);
                updateLists(a);
                this.cont = false;
                break;
            }
            case Values.LOGIN_PROTOCOL: {
                String userTemp = msg.sender;
                char[] passtemp = (char[]) msg.obMessage;
                User tempUser = new User(userTemp, passtemp);
                Message newMsge;
                Message message = new Message(Values.CONNECTIN_PROTOCOL, "");
                message.message = msg.sender;
                for (User checkUser : CServer.users) {
                    if (tempUser.isCorrect(checkUser)) {
                        CServer.addClient(userTemp, obout);
                        newMsge = new Message(Values.LOGIN_RESPONSE_PROTOCOL, msg.sender, Values.SERVER_USER_NAME, Values.LOGIN_RESPONSE_PROTOCOL_YES);

                        if (getSenderIndex(newMsge.recipent) < CServer.loginNames.size()) {
                            sendMessage(msg, getSenderIndex(newMsge.recipent));
                        }
                        Message a = new Message(Values.OBJECTTYPE_LIST_PROTOCOL, msg.sender, Values.SERVER_USER_NAME, CServer.loginNames);
                        updateLists(a);
                        return;
                    }
                }
                newMsge = new Message(Values.LOGIN_RESPONSE_PROTOCOL, msg.sender, Values.SERVER_USER_NAME, "");
                if (getSenderIndex(newMsge.recipent) < CServer.loginNames.size()) {
                    sendMessage(msg, getSenderIndex(newMsge.recipent));
                }
            }
            case Values.SIGN_UP_PROTOCOL: {
                String userTemp = msg.sender;
                char[] passtemp = (char[]) msg.obMessage;
                User tempUser = new User(userTemp, passtemp);
                Message newMsge;
                if (userExist(tempUser)) {
                    newMsge = new Message(Values.SIGN_UP_RESPONSE_PROTCOL, msg.sender, Values.SERVER_USER_NAME, Values.SIGN_UP_RESPONSE_PROTCOL_DONE);
                    CServer.users.add(tempUser);
                    CServer.saveUsers();
                }else{
                    newMsge = new Message(Values.SIGN_UP_RESPONSE_PROTCOL, msg.sender, Values.SERVER_USER_NAME, "");
                }
                Message message = new Message(Values.LOGIN_PROTOCOL, "", msg.sender, passtemp);
                recMessage(message);
                if (getSenderIndex(newMsge.recipent) < CServer.loginNames.size()) {
                    sendMessage(msg, getSenderIndex(newMsge.recipent));
                }
            }
            default: {
                break;
            }
        }
    }

    private int getSenderIndex(String userName) {
        for (String s : CServer.loginNames) {
            if (s.equals(userName)) {
                return CServer.loginNames.indexOf(s);
            }
        }
        return CServer.loginNames.size() + 1;
    }

    private boolean userExist(User u) {
        for (User us : CServer.users) {
            if (us.isCorrect(u)) {
                return true;
            }
        }
        return false;
    }
}
