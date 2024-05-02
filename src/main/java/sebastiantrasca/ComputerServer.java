package sebastiantrasca;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ComputerServer {
    static final int PORT = 8080;
    public static void main(String[] args){
        ServerSocket serverSocket = null;
        Socket clientSocket = null;

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
        }
    }
}
