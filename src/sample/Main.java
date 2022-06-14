package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
       // Files.createDirectories(Path.of(System.getProperty("user.dir") + "\\src\\sample\\saved\\h"));
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Scene scene =new Scene(root,Color.TRANSPARENT);
        scene.getStylesheets().add(getClass().getResource("Aplication.css").toExternalForm());
        //primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setOnCloseRequest(e -> Controller.clearH());

    }



    public static void main(String[] args) {
        launch(args);
    }
}

