package com.example.trivial;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.util.StringConverter;

import java.io.IOException;
import java.util.*;

public class TrivialController {

    @FXML
    private Label preguntaLabel;
    @FXML
    private GridPane opcionesGrid;
    @FXML
    private Label resultadoLabel;
    @FXML
    private Button siguienteButton;
    @FXML
    private ChoiceBox<Locale> languageSelector;
    
    private MainApp mainApp;
    private ResourceBundle bundle;
    private int preguntaActual = 0;
    private int totalPreguntas;
    private List<Button> botonesOpcion = new ArrayList<>();

    @FXML
    public void initialize() {

        bundle = ResourceBundle.getBundle("com.example.trivial.bundles.messages");
        totalPreguntas = Integer.parseInt(bundle.getString("total.questions"));
        
        setupLanguageSelector();
        crearBotones();
        updateUITexts();
        cargarPregunta();
    }

    /**
     * Permite a MainApp pasar una referencia de sí misma al controlador.
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    /**
     * Configura el selector de idioma, sus opciones y su comportamiento.
     */
    private void setupLanguageSelector() {

        languageSelector.getItems().addAll(new Locale("es"), new Locale("en"), new Locale("ca"));
        
        languageSelector.setConverter(new StringConverter<>() {
            @Override
            public String toString(Locale locale) {
                if (locale.getLanguage().equals("es")) return "Español";
                if (locale.getLanguage().equals("en")) return "English";
                if (locale.getLanguage().equals("ca")) return "Català";
                return null;
            }
            @Override
            public Locale fromString(String string) { return null; }
        });

        languageSelector.setValue(Locale.getDefault());
        
        languageSelector.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !newValue.equals(oldValue)) {
                try {
                    mainApp.loadTrivialScene(newValue);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Actualiza los textos de la UI que no cambian con cada pregunta (ej. botón "Siguiente").
     */
    private void updateUITexts() {
        siguienteButton.setText(bundle.getString("button.next"));
        preguntaLabel.setText(bundle.getString("game.loading"));
    }
    
    /**
     * Crea los 4 botones de respuesta y los añade a la cuadrícula. Se hace una sola vez.
     */
    private void crearBotones() {
        for (int i = 0; i < 4; i++) {
            Button button = new Button();
            button.setOnAction(this::opcionSeleccionada);
            botonesOpcion.add(button);
            opcionesGrid.add(button, i % 2, i / 2);
        }
    }
    
    /**
     * Carga los datos de la pregunta actual desde el ResourceBundle.
     */
    private void cargarPregunta() {
        resetearBotones();
        resultadoLabel.setText("");
        siguienteButton.setVisible(false);
        opcionesGrid.setDisable(false);
        
        String keyBase = "question." + (preguntaActual + 1);
        preguntaLabel.setText(bundle.getString(keyBase + ".statement"));

        String respuestaCorrecta = bundle.getString(keyBase + ".correct");
        List<String> opciones = new ArrayList<>();
        opciones.add(respuestaCorrecta);
        opciones.add(bundle.getString(keyBase + ".wrong1"));
        opciones.add(bundle.getString(keyBase + ".wrong2"));
        opciones.add(bundle.getString(keyBase + ".wrong3"));
        
        Collections.shuffle(opciones);

        for (int i = 0; i < botonesOpcion.size(); i++) {
            Button button = botonesOpcion.get(i);
            String opcionActual = opciones.get(i);
            button.setText(opcionActual);
            button.setUserData(opcionActual.equals(respuestaCorrecta));
        }
    }
    
    /**
     * Se ejecuta cuando el usuario hace clic en un botón de respuesta.
     */
    private void opcionSeleccionada(ActionEvent event) {
        opcionesGrid.setDisable(true);
        siguienteButton.setVisible(true);

        Button botonSeleccionado = (Button) event.getSource();
        boolean esCorrecto = (boolean) botonSeleccionado.getUserData();

        if (esCorrecto) {
            resultadoLabel.setText(bundle.getString("feedback.correct"));
            resultadoLabel.setStyle("-fx-text-fill: #2ECC71;");
        } else {
            resultadoLabel.setText(bundle.getString("feedback.incorrect"));
            resultadoLabel.setStyle("-fx-text-fill: #E74C3C;");
            botonSeleccionado.getStyleClass().add("wrong-answer");
        }

        for (Button btn : botonesOpcion) {
            if ((boolean) btn.getUserData()) {
                btn.getStyleClass().add("correct-answer");
                break;
            }
        }
    }

    /**
     * Se ejecuta cuando se pulsa el botón "Siguiente".
     */
    @FXML
    void siguientePregunta() {
        preguntaActual++;
        if (preguntaActual < totalPreguntas) {
            cargarPregunta();
        } else {
            preguntaLabel.setText(bundle.getString("game.end.title"));
            opcionesGrid.setVisible(false);
            siguienteButton.setVisible(false);
            resultadoLabel.setText(bundle.getString("game.end.subtitle"));
            resultadoLabel.setStyle("-fx-text-fill: #F39C12;");
        }
    }
    
    /**
     * Limpia los estilos de feedback de los botones antes de cargar una nueva pregunta.
     */
    private void resetearBotones() {
        for (Button btn : botonesOpcion) {
            btn.getStyleClass().removeAll("correct-answer", "wrong-answer");
        }
    }
}