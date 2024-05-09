package sebastiantrasca;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalTime;

public class CommunicationThread extends Thread{
    protected Socket socket;
    private Computer clientComputer;

    public CommunicationThread(Socket clientSocket){
        this.socket = clientSocket;
    }

    public Computer getClientComputer(){
        return clientComputer;
    }

    public void run(){
        Instant start = Instant.now();
        DataInputStream in = null;
        ObjectMapper mapper = new ObjectMapper();

        try {
            in = new DataInputStream(socket.getInputStream());
        } catch (IOException e) {
            return;
        }
        try{
            int length = in.readInt();
            byte[] jsonData = new byte[length];
            in.readFully(jsonData);
            String jsonString = new String(jsonData);
            clientComputer = mapper.readValue(jsonString, Computer.class);
            this.setName(clientComputer.getUsername());
            ComputerServer.computerList.add(clientComputer);
        } catch (IOException i){
            i.printStackTrace();
        }
        System.out.println("\u001B[32mClient info: \n" + clientComputer.toString() + "\u001B[0m");
        String line = "";
        try{
            while(true){
                line = in.readUTF();
                System.out.println("\u001B[36m" + line + "\u001B[0m");
            }
        } catch (IOException e){
            line=this.getName();
            System.out.println("\u001B[43mClient "+line+" closed the connection at " + LocalTime.now() + "\u001B[0m");
        }
        finally{
            try{
                Instant end = Instant.now();
                Duration elapsed = Duration.between(start, end);
                System.out.println("\u001B[43mClosing client socket... Connection duration: " + elapsed.toSeconds() + " s\u001B[0m");
                in.close();
                socket.close();
            } catch (IOException e) {
                System.out.println("Error closing socket!");
            }
        }
    }
}
