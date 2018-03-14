/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.io.IOException;

/**
 *
 * @author Meluleki
 */
public class CServer {

    private static long Port;

   
    private final ServerSocket server;
    private static ArrayList<Socket> clientSockets;
    private static ArrayList<String> loginNames;

    public CServer() throws IOException {
        server = new ServerSocket((int) Values.SERVER_PORT_NUMBER);
        clientSockets = new ArrayList<>();
        loginNames = new ArrayList<>();
        connect();
    }

    public void connect() throws IOException {
        while (true) {
            Socket clienSocket = this.server.accept();
            Thread runThread = new Thread(new AcceptClient(clienSocket));
        }
    }
    
    public static void addClient(String userTemp, Socket clientSocket) {
    
    }

    public static void removeClient() {

    }
    
    
}
