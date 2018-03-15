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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Meluleki
 */
public class AcceptClient extends Thread implements Cprotocol {

    public Socket clientSocket;
    public ObjectInputStream obin;
    public ObjectOutputStream obout;

    public AcceptClient(Socket cs) throws IOException {
        this.clientSocket = cs;
        this.obout = new ObjectOutputStream(cs.getOutputStream());
        obout.flush();
        this.obin = new ObjectInputStream(cs.getInputStream());
        System.out.println("here");
        
        
        start();
        
    }

    @Override
    public void run() {
        while (true) {
            try {
                Message msgFromClien = (Message) obin.readObject();
                recMessage(msgFromClien);
            } catch (Exception e) {
                Logger.getLogger(AcceptClient.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    @Override
    public void sendMessage(Message msg) {
        try {
            obout.writeObject(msg);
            obout.flush();
        } catch (IOException ex) {
            Logger.getLogger(AcceptClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @Override
    public void recMessage(Message msg) {
        switch (msg.mType) {
            case Values.CONNECTIN_PROTOCOL: {
                System.out.println();
                String userTemp=msg.message;
                CServer.addClient(userTemp, clientSocket);
                sendMessage(new Message("Connected", userTemp));
                System.out.println(msg.message);
                Message a= new Message(Values.OBJECTTYPE_List_PROTOCOL, msg.sender, Values.SERVER_USER_NAME, CServer.getList());
                sendMessage(a);
                break;
            }
            case Values.TEXT_PROTOCOL:{
                String rec=msg.recipent;
                int sckNumber;
                for(String s:(ArrayList<String>) CServer.getList()){
                    if(s.equals(rec)){
                        sckNumber=((ArrayList<String>) CServer.getList()).indexOf(s);
                        
                    }
                }
            }
        }
    }

}
