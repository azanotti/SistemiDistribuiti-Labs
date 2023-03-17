import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	//Define the server connection properties
	private static int port = 8080;
	private static ServerSocket Server;
	
	//Define the server data structures
	private static String[] movies;
	private static String moviesOneLiner = "";
	
	//Define the constructor
	public Server() {
		//Open server on default port if it's not already binded
		try {
			Server = new ServerSocket(port);
			System.out.println("Server listening on port: " + Server.getLocalPort());
		} catch (IOException e) {
			System.out.println("Error while starting server on port " + port);
		}
	}
	
	//Define the loop for accepting and processing connections
	public static void serverAction() throws IOException {
		while(true) {
			PrintWriter out = null;
			BufferedReader in = null;
			
			System.out.println("Waiting for client to establish a connection.");
			Socket client = Server.accept();
			
			System.out.println("Connection accepted from: "+ client.getInetAddress());
			
			out = new PrintWriter(client.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			
			try {
				int command=in.read();
				in.read();
				int day=in.read();
				
				if(command==1 && day>=1 && day<=7){
					System.out.println("Sending movies list: " + moviesOneLiner);
					
					out.write(1);
					out.write('\n');
					out.write(moviesOneLiner);
					out.write("\n");
					
					out.flush();
					
					Thread.sleep(1000);					
				}
				else{
					out.write(0);
					out.write('\n');
				}
				
				out.close();
				in.close();
				client.close();

			} catch(Exception e) {
				System.out.println("A general system error has occured");
			}
			
		}
	}
	
	public static void main(String[] args) throws Exception {
		new Server();
		//Remember to initialize the array, otherwise you'll get an exception
		movies = new String[4];
		//Fill the array (you can also create a method)
		movies[0] = "The Batman";
		movies[1] = "Assassinio sul nilo";
		movies[2] = "Uncharted";
		movies[3] = "Sing 2";

		for(int i = 0; i < movies.length; i++) {
			moviesOneLiner = moviesOneLiner + movies[i] + ", ";
		}
		moviesOneLiner = moviesOneLiner.substring(0, moviesOneLiner.length() - 2);
				
		serverAction();
	}

}