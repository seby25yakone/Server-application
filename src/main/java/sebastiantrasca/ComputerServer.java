package sebastiantrasca;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ComputerServer {
    static final int PORT = 8080;
    static List<Record> deviceList = new ArrayList<>();
    public static void main(String[] args){
        ServerSocket serverSocket = null;
        Socket clientSocket = null;
        Scanner read = new Scanner(System.in);

        try{
            serverSocket = new ServerSocket(PORT);
            System.out.println("Server started.");
            System.out.println("Waiting for a client...");
        } catch (IOException e) {e.printStackTrace();}
        while(true){
            try{
                synchronized (serverSocket){
                    clientSocket = serverSocket.accept();
                    System.out.println("Client connected!");
                }
            } catch (IOException e) {e.printStackTrace();}
            new CommunicationThread(clientSocket).start();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while(true){
                        int i = read.nextInt();
                        if(deviceList.size()>=i+1){
                            System.out.println("\u001B[32mClient info: ");
                            System.out.println(deviceList.get(i) + "\u001B[0m");
                        }
                        else System.out.println("Client index not found");

                    }
                }
            }).start();
        }
    }
}
