package sebastiantrasca;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalTime;

public class CommunicationThread extends Thread{
    protected Socket socket;
    private Record clientDevice;

    public CommunicationThread(Socket clientSocket){
        this.socket = clientSocket;
    }

    public Record getClientDevice(){
        return clientDevice;
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
            clientDevice = mapper.readValue(jsonString, Record.class);
            this.setName(new String(clientDevice.getId()+""));
            ComputerServer.deviceList.add(clientDevice);
        } catch (IOException i){
            i.printStackTrace();
        }
        System.out.println("\u001B[32mClient info: \n" + clientDevice.toString() + "\u001B[0m");
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
