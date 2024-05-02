package sebastiantrasca;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class CommunicationThread extends Thread{
    protected Socket socket;
    private Computer clientComputer;

    public CommunicationThread(Socket clientSocket){
        this.socket = clientSocket;
    }

    public void run(){
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
        } catch(ClassNotFoundException e) {
            System.out.println("Error getting computer data!");
        } catch (IOException i){
            i.printStackTrace();
        }
        System.out.println("Client info: \n" + clientComputer.toString());
        String line = "";
        try{
            while(!line.equals("OVER")){
                line = in.readUTF();
                System.out.println(line);
            }
        } catch (IOException e){
            line=this.getName();
            System.out.println("Client "+line+" closed the connection.");
        }
        finally{
            try{
                System.out.println("Closing connection...");
                in.close();
                socket.close();
            } catch (IOException e) {
                System.out.println("Error closing server!");
            }
        }
    }
}
