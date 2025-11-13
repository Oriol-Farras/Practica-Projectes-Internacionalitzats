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
        // Establecemos el idioma español como predeterminado al iniciar
        loadTrivialScene(new Locale("es"));
    }

    public void loadTrivialScene(Locale locale) throws IOException {
        // Establece el Locale por defecto para la aplicación
        Locale.setDefault(locale);
        
        // Carga el ResourceBundle correspondiente al Locale
        ResourceBundle bundle = ResourceBundle.getBundle("com.example.trivial.bundles.messages", locale);
        
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("/com/example/trivial/Trivial.fxml"), bundle);
        Parent root = fxmlLoader.load();

        // Pasa una referencia de MainApp al controlador para que pueda recargar la escena
        TrivialController controller = fxmlLoader.getController();
        controller.setMainApp(this);

        Scene scene = new Scene(root, 700, 500); // Un poco más grande para el selector
        scene.getStylesheets().add(getClass().getResource("/com/example/trivial/styles.css").toExternalForm());
        
        primaryStage.setTitle(bundle.getString("app.title"));
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}