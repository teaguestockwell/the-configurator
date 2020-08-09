package fxmlDocs;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Arrays;

public class Main extends Application {

    public static final double picSize = 300.0;
    static Stage window;

    //Teague Stockwell 20200809 The Configurator

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("compare2.fxml"));
        primaryStage.setTitle("Teague Stockwell Configurator");
        primaryStage.setScene(new Scene(root, 800, 800));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static void changeFXML(String fxmlDoc) {
        try {
            Parent root = FXMLLoader.load(Main.class.getResource(fxmlDoc));
            Scene tableViewScene = new Scene(root);
            window.setScene(tableViewScene);
            window.show();
        } catch (Exception e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }
}
