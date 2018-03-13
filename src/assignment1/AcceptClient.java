/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment1;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Meluleki
 */
public class AcceptClient implements Runnable, Cprotocol {

    public Socket clientSocket;
    public ObjectInputStream obin;
    public ObjectOutputStream obout;

    public AcceptClient(Socket clientSocket, ObjectInputStream obin, ObjectOutputStream obout) {
        this.clientSocket = clientSocket;
        this.obin = obin;
        this.obout = obout;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Message msgFromClien=(Message)obin.readObject();
                recMessage(msgFromClien);
            } catch (Exception e) {
                Logger.getLogger(AcceptClient.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    @Override
    public void sendMessage(Message msg) {

    }

    @Override
    public void recMessage(Message msg) {

    }

}
