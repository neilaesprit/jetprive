package test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class mainFx extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root, 800, 600); // ✅ Set scene size here
            primaryStage.setScene(scene);
            primaryStage.setTitle("Location JetPrive");
            primaryStage.show();
        } catch (IOException e) {
            System.out.println("Erreur lors du chargement de login.fxml : " + e.getMessage());
        }
    }
}
