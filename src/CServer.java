/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Meluleki
 */
public class CServer {

    private static long Port;

    private final ServerSocket server;
    public static ArrayList<String> loginNames;
    public static ArrayList<ObjectOutputStream> outputstreams;
//    private final InetAddress localhost;

    public CServer() throws IOException {

        server = new ServerSocket((int) Values.SERVER_PORT_NUMBER);
        loginNames = new ArrayList<>();
        outputstreams= new ArrayList<>();
        System.out.print(server.getLocalSocketAddress());
        connect();
    }

    public void connect() throws IOException {
        while (true) {
            Socket clienSocket = this.server.accept();
            System.out.println("here");
            Thread runThread = new Thread(new AcceptClient(clienSocket));
        }
    }

    public synchronized static void addClient(String userTemp, ObjectOutputStream obout) {
        loginNames.add(userTemp);
        outputstreams.add(obout);
    }

    public synchronized static void removeClient(String userTemp) {
        int i;
        for (String s : loginNames) {
            if (s.equals(userTemp)) {
                i = loginNames.indexOf(s);
                loginNames.remove(s);
                outputstreams.remove(i);
            }
        }
    }
    public static void main(String[] args) {
        try {
            CServer cs = new CServer();
        } catch (IOException ex) {
            Logger.getLogger(CServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
