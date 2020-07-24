import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    public static final int PORT = 5111;
    private ServerSocket serverSocket;
    private List<ConnectionTCP> clients;

    public Server() throws IOException {
        serverSocket = new ServerSocket(PORT);
        clients = new ArrayList<ConnectionTCP>();
    }

    public static void main(String[] args) {
        try {
            Server server = new Server();
            server.startServer();
        }catch(IOException ex){
            System.out.println("Не удалось создать сервер");
        }
    }

    private void startServer(){
        while(true){
            Socket client;
            try {
                client = serverSocket.accept();
                ConnectionTCP connection = new ConnectionTCP(client,this);
                clients.add(connection);
            } catch (IOException ex){
                System.out.println("Клиент не смог подключиться");
            }
        }
    }

    void sendMessForAllClients(String message){
        for(ConnectionTCP connection : clients){
            connection.sendMess(message);
        }
    }

    public List<ConnectionTCP> getClients() {
        return clients;
    }

    public void setClients(List<ConnectionTCP> clients) {
        this.clients = clients;
    }



}
