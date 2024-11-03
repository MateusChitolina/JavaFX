package javafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
/**
 *
 * @author mateu
 */
public class JavaFX extends Application {
    
 @Override
    public void start(Stage primaryStage) throws Exception {
        System.out.println("Iniciando a aplicação...");
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        primaryStage.setTitle("Gerenciador de Livros");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        System.out.println("Aplicação iniciada.");
    }

    public static void main(String[] args) {
        launch(args);
    }
}