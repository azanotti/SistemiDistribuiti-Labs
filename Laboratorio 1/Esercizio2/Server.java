package Esercizio2;

import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
    //Define the server connection properties
    private static int port = 8080;
    private static ServerSocket Server;

    //Define the server data structures
    private HashMap<Integer, ArrayList<Sala>> proiezioni = new HashMap<Integer, ArrayList<Sala>>();

    //Define the HashMap creator
    public static HashMap<Integer, ArrayList<Sala>> createHashMap() throws IOException {
        HashMap<Integer, ArrayList<Sala>> programmazione = new HashMap<Integer, ArrayList<Sala>>();
        ArrayList<Sala> sala1 = new ArrayList<Sala>();
        ArrayList<Sala> sala2 = new ArrayList<Sala>();
        ArrayList<Sala> sala3 = new ArrayList<Sala>();
        ArrayList<Sala> sala4 = new ArrayList<Sala>();
        ArrayList<Sala> sala5 = new ArrayList<Sala>();
        ArrayList<Sala> sala6 = new ArrayList<Sala>();
        ArrayList<Sala> sala7 = new ArrayList<Sala>();

        sala1.add("The Matrix", 20);
        sala1.add("The Matrix 2", 30);

        sala2.add("The Matrix 3", 40);
        sala2.add("The Matrix 4", 50);

        sala3.add("The Matrix 5", 10);
        sala3.add("The Matrix 6", 30);

        sala4.add("The Matrix 7", 60);
        sala4.add("The Matrix 8", 70);

        sala5.add("The Matrix 9", 30);
        sala5.add("The Matrix 10", 30);

        sala6.add("The Matrix 11", 20);
        sala6.add("The Matrix 12", 20);

        sala7.add("The Matrix 13", 10);
        sala7.add("The Matrix 14", 90);

        programmazione.put(1, sala1);
        programmazione.put(2, sala2);
        programmazione.put(3, sala3);
        programmazione.put(4, sala4);
        programmazione.put(5, sala5);
        programmazione.put(6, sala6);
        programmazione.put(7, sala7);

        System.out.println(programmazione);

        return programmazione;
    }

    //Define the server constructor
    public Server(HashMap<Integer, ArrayList<Sala>> programmazione) throws IOException {
        //Set the proiezioni
        this.proiezioni = programmazione;
        Server = new ServerSocket(port);
        System.out.println("Server listening on port: " + port);
    }

    //Define the server action
    public void serverAction(){
        while(true){
            PrintStream out = null;
            BufferedReader in = null;

            try{
                System.out.println("Waiting for client to establish a connection.");

                //Create a client socket
                Socket client = Server.accept();

                System.out.println("Connection accepted from: "+ client.getInetAddress());

                //Define the input and output streams previously declared
                out = new PrintStream(client.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(client.getInputStream()));

                try{
                    String command = in.readLine();

                    if(command.equals("1")){
                        int day = new Integer(in.readLine());

                        if(day > 0 && day < 8){
                            if(!(proiezioni.get(day) == null)){
                                out.println(1);
                                for(Sala s : proiezioni.get(day)){
                                    out.println(s.toString());
                                }
                                out.println("2-close");

                                out.flush();

                                Thread.sleep(1000);
                            } else {
                                System.out.println("No movies for this day.");
                                out.println(-1);
                                out.close();
                                in.close();
                                client.close();
                                continue;
                            }
                        } else {
                            System.out.println("Invalid day.");
                            out.println(0);
                        }
                    } else {
                        System.out.println("Invalid command.");
                        out.println(0);

                        out.close();
                        in.close();
                        client.close();
                        break;
                    }

                    command = in.readLine();
                    if(command.equals("2")) {
                        int day = new Integer(in.readLine());
                        String film = new String(in.readLine());
                        int persone = new Integer(in.readLine());

                        System.out.println("New booking for " + persone + " people for the movie " + film + " on day " + day);

                        if(day > 0 && day < 8){
                            int postiAttualmenteDisponibili = verificaDisponibilita(day, film);

                            if(persone <= postiAttualmenteDisponibili){
                                out.println(1);
                                acquista(day, film, persone);
                            } else {
                                out.println(-1);
                            }
                        } else {
                            System.out.println("Invalid day.");
                            out.println(0);
                        }
                        out.close();
                        in.close();
                        client.close();
                    } else {
                        System.out.println("Invalid command.");
                        out.println(0);
                    }

                } catch(IOException e){
                    System.out.println("Error: " + e);
                }

            } catch(IOException e){
                System.out.println("Error: " + e);
            }
        }
    }

    private void acquista(int day, String film, int persone) {
        ArrayList<Sala> sala = proiezioni.get(day);

        System.out.println("Update the number of available seats for the movie " + film + " on day " + day);

        if(sala == null){
            return;
        }

        for(Sala s: sala){
            if(s.equals(new Sala(film))) {
                s.setPostiOccupati(s.getPostiOccupati() + persone);
            }
        }
        System.out.println(proiezioni);
    }

    private int verificaDisponibilita(int day, String film){
        ArrayList<Sala> sala = proiezioni.get(day);

        System.out.println("Check the number of available seats for the movie " + film + " on day " + day);

        if(sala == null){
            return 0;
        }

        for(Sala s: sala){
            if(s.equals(new Sala(film))) {
                return s.getPostiDisponibili();
            }
        }
        return 0;
    }


    //Define the main
    public static void main(String[] args) throws IOException {
        HashMap<Integer, ArrayList<Sala>> programmazione = new HashMap<Integer, ArrayList<Sala>>();
        programmazione = createHashMap();
        System.out.println(programmazione);

        Server srv = new Server(programmazione);
        srv.serverAction();
    }
}