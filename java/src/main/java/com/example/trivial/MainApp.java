package com.example.trivial;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class MainApp extends Application {

    private Stage primaryStage;

    @Override
    public void start(Stage stage) throws IOException {
        this.primaryStage = stage;
        loadTrivialScene(new Locale("es"));
    }

    public void loadTrivialScene(Locale locale) throws IOException {

        Locale.setDefault(locale);
        
        ResourceBundle bundle = ResourceBundle.getBundle("com.example.trivial.bundles.messages", locale);
        
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("/com/example/trivial/Trivial.fxml"), bundle);
        Parent root = fxmlLoader.load();

        TrivialController controller = fxmlLoader.getController();
        controller.setMainApp(this);

        Scene scene = new Scene(root, 700, 500); 
        scene.getStylesheets().add(getClass().getResource("/com/example/trivial/styles.css").toExternalForm());
        
        primaryStage.setTitle(bundle.getString("app.title"));
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}