import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;

public class Client extends Application {
    public static final int PORT = 5111;
    public static final String HOST = "localhost";
    private Socket socket;
    private BufferedWriter writer;
    private BufferedReader reader;


    public Client() {}

    public static void main(String[] args) throws IOException{ Application.launch(args); }

    public void start(Stage stage) throws Exception {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Client.class.getResource("/view.fxml"));
            Parent root = loader.load();
            Controller controller = loader.getController();
            this.connectToServer();
            controller.setClient(this);
            Task<Void> task = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    while(true){
                        String gotMess = reader.readLine();
                        controller.printMess(gotMess);
                    }
                }
            };
            Thread thread = new Thread(task);
            thread.setDaemon(true);
            thread.start();
            stage.setTitle("Client");
            stage.setScene(new Scene(root));
            stage.setResizable(true);
            stage.show();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void connectToServer() throws IOException{
        socket = new Socket(HOST,PORT);
        writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }


    public void writeMess(String mess){
        try {
            writer.write(mess + "\n");
            writer.flush();
        }catch (IOException ex){
            System.out.println("Не удалось отправить сообщение");
        }
    }


}
