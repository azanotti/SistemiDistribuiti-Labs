import java.io.*;
import java.net.*;
import java.util.*;

public class Client {
    public static void main(String[] args){
        //Define the client connection properties
		int port = 8080;

		//Define the client inputs and outputs
		BufferedReader in = null;
		PrintStream out = null;

		//Define the client socket
		Socket socket = null;

		//Define the client data structures
		String message = "";
		Random r = new Random();

        try{
            //Open a connection to the server
            socket = new Socket("localhost", port);

            //Open the input and output streams
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintStream(socket.getOutputStream(), true);

            //Send the command to the server
            out.println("1");

            //Send the day to the server
            out.println(r.nextInt(7) + 1);

            //Read the server response
            while(true){
                message = in.readLine();
                if(message == null || message.equals("-1")){
                    break;
                }

                if(message.equals("1")){
                    System.out.println("Server accepted the request.");

                    message = in.readLine();
                    System.out.println("Received: " + message);
                    while(!message.equals("2-close")){
                        message = in.readLine();
                        System.out.println("Received: " + message);
                    }
                }
            }
            out.close();
            in.close();
            socket.close();
        } catch(IOException e){
            System.out.println("Error while connecting to the server.");
            e.printStackTrace();
        }

    }
}
