

import java.io.*;
import java.net.*;
import java.util.*;
public class Client implements Runnable {

    public BufferedReader in;
    public PrintWriter out;

      public void listen() throws Exception
      {

            String incoming;
            while(!((incoming=in.readLine()).equals("Out")))
            {

                    System.out.println(incoming);

            }

      }  


      public void run()
      {
        try{
        listen();}

        catch(Exception e)
        {
            e.getMessage();
        }


      }


      public Message createMessage()
      {
      	//@DK MESSAGE 

      	//SERVER WILL CHECK IF USER IS THER OR NOT IF YES PASS ON THE MESSAGE

      	String dest,message;
      	dest=Scanner.nextLine();
      	message=Scanner.nextLine();
      	Message msg=new Message("Text",dest,message)

      }


public void sendMessage()
{

	// out and send msg object to server if user is there it will pass it on if not it will discard it and prompt user to retry
}




    public static void main(String[] args) throws IOException {
        
       

        String hostName = "localhost";
        int portNumber = 5000;

        try (
            Socket socket = new Socket(hostName, portNumber);
            PrintWriter out =
                new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in =
                new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            Scanner stdIn=new Scanner(System.in);
        ) {
            
            System.out.println("Username:")
            String name=stdIn.nextLine();



            Client c=new Client();
            c.in=in;
            Thread t=new Thread(c);
            t.start();

            c.out=out;

	//            String userInput;
            // Message msg; 

            while(true)
            {
                msg=createMessage();
                sendMessage();
                // out.println(stdIn.nextLine());
            }

        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
                hostName);
            System.exit(1);
        } 
    }
}