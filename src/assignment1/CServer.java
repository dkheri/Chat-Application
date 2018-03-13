/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment1;

import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author Meluleki
 */
public class CServer implements Cprotocol{
    private static ArrayList<Socket> clientSockets;
    private static ArrayList<String> loginNames;
    
    public void sendStatus(int code, String Descript){
        
    }

    @Override
    public void sendMessage(Message msg) {
    }

    @Override
    public void recMessage(Message msg) {
        
    }
    
    
}
