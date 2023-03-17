package Esercizio2;

import java.io.*;
import java.net.*;
import java.util.*;

public class Client {
    public static void main(String args[]){
        BufferedReader in = null;
        PrintStream out = null;

        Socket socket = null;
        String message = "";

        ArrayList<Sala> film = new ArrayList<Sala>();

        Random r = new Random();

        try{
            socket = new Socket("localhost", 8000);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintStream(socket.getOutputStream(), true);

            out.println("1");
            int day = r.nextInt(7) + 1;
            out.println(day);

            System.out.println("Sent request for day: " + day);

            while(true){
                message = in.readLine();

                if(message == null || message.equals("-1")){
                    out.close();
                    in.close();
                    socket.close();
                    System.out.println("No movies for this day.");
                    return;
                }

                if(message.equals("1")){
                    System.out.println("Server accepted request.");

                    message = in.readLine();

                    while(!message.equals("2-close")){
                        System.out.println("Received: " + message);
                        film.add(new Sala(message));
                    }
                    break;
                }
            }
            Thread.sleep(10000);
            System.out.println("Test booking request");

            out.println(2);
            out.println(day);
            out.println(film.get(r.nextInt(film.size()-1)).getTitle());
            out.println(r.nextInt(2));
            message = in.readLine();
            System.out.println("Received: " + message);

            out.close();
            in.close();
            socket.close();
        } catch(Exception e){
            System.out.println("Error: " + e);
        }
    }
}
