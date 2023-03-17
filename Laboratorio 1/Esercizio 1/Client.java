import java.io.*;
import java.net.*;
import java.util.*;

public class Client {
	public static void main(String args[]) {
		//Define the client connection properties
		int port = 8080;

		//Define the client inputs and outputs
		DataInputStream in = null;
		PrintStream out = null;

		//Define the client socket
		Socket socket = null;

		//Define the client data structures
		int message;
		String movies="";
		Random r= new Random();
		
		try {
			//Open a socket connection using the default host and port or the one specified as arguments
			if(args.length == 0){
				socket = new Socket("localhost", port);
			} else {
				socket = new Socket(args[0], port);
			}
			
			//Define the previously declared input and output channels
			in = new DataInputStream(socket.getInputStream());
			out = new PrintStream(socket.getOutputStream(), true);			

			//Send the first command
			out.write(1);
			out.write('\n');

			//Send the random date
			int d=r.nextInt(7)+1;
			out.write(d);
			System.out.println("Sent request for day: " + d);

			//Read the server response
			while (true) {
				message = in.read();
				if (message == -1)
					break; // no more bytes
				if (message== 1){ // ok
					System.out.println("Server accepted the request.");
					//Skip the \n
					in.read();
					//Read the movies list
					message=in.read();
					
					while (! ((char)message+"").equals("\n")) {
							movies = movies + Character.toString(((char) message));
							message=in.read();
					}
	
				}
		
			}
			System.out.println("Movie list on day " + d + ": " + movies);
			
			out.close();
			in.close();
			socket.close();
		}
		catch(Exception e) { 
			System.out.println("An unexpected error occurred: " + e.getMessage());
		}
	}
}