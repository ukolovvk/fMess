import java.io.*;
import java.net.Socket;

public class ConnectionTCP extends Thread{
    private Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;
    private Server server;

    public ConnectionTCP(Socket socket,Server server) throws IOException {
        this.socket = socket;
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        this.server = server;
        this.start();
    }

    @Override
    public void run(){
           while(true){
               try {
                   String gotMess = reader.readLine();
                   server.sendMessForAllClients(gotMess);
               }catch (IOException ex){
                   System.out.println("Не удалось прочитать сообщение");
                   server.getClients().remove(this);
                   break;
               }
           }
    }

    void sendMess(String mess){
        try {
            writer.write(mess + "\n");
            writer.flush();
        }catch (IOException ex){
            System.out.println("Не удалось отправить сообщение");
            ex.printStackTrace();
        }
    }

}
