import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML private TextArea generalField;
    @FXML private TextArea allClients;
    @FXML private TextArea messField;
    @FXML private Button clearButton;
    @FXML private Button sendButton;
    private Client client;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        sendEvent();
        clearEvent();
        generalField.setEditable(true);
        generalField.setWrapText(true);
        generalField.setEditable(false);
        allClients.setEditable(false);
        messField.setWrapText(true);
    }

    private void sendEvent(){
        sendButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String message = messField.getText().trim();
                if(!message.equals("")){
                    client.writeMess(message);
                    messField.clear();
                }
            }
        });
    }

    private void clearEvent(){
        clearButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                messField.clear();
            }
        });
    }

    public void printMess(String mess){
        generalField.appendText(mess + "\n");
    }

    public void setClient(Client client){
        this.client = client;
    }



}
