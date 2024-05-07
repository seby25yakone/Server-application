package sebastiantrasca;

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
        ObjectInputStream compin = null;

        try {
            in = new DataInputStream(socket.getInputStream());
            compin = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            return;
        }
        try{
            clientComputer = (Computer)compin.readObject();
            this.setName(clientComputer.getUsername());
            ComputerServer.computerList.add(clientComputer);
        } catch(ClassNotFoundException e) {
            System.out.println("Error getting computer data!");
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
