/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment1;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import static assignment1.Values.*;
import java.io.IOException;
/**
 *
 * @author Meluleki
 */
public class CServer{
    
    private static long Port;
    private ServerSocket server;
    private static ArrayList<Socket> clientSockets;
    private static ArrayList<String> loginNames;

    public CServer() throws IOException {
        server= new ServerSocket((int)SERVER_PORT_NUMBER);
        clientSockets= new ArrayList<>();
        loginNames= new ArrayList<>();
        connect();
    }
    public void connect() throws IOException{
        while(true){
            Socket clienSocket = this.server.accept();
        }
    }
}
