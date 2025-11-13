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
        // Set Spanish as the default language on startup
        loadTrivialScene(new Locale("es"));
    }

    public void loadTrivialScene(Locale locale) throws IOException {
        // Set the default Locale for the application
        Locale.setDefault(locale);
        
        // Load the ResourceBundle corresponding to the new Locale
        ResourceBundle bundle = ResourceBundle.getBundle("com.example.trivial.bundles.messages", locale);
        
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("/com/example/trivial/Trivial.fxml"), bundle);
        Parent root = fxmlLoader.load();

        // Pass a reference of MainApp to the controller so it can reload the scene
        TrivialController controller = fxmlLoader.getController();
        controller.setMainApp(this);

        Scene scene = new Scene(root, 700, 500);
        String cssPath = getClass().getResource("/com/example/trivial/styles.css").toExternalForm();
        scene.getStylesheets().add(cssPath);
        
        primaryStage.setTitle(bundle.getString("app.title"));
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}