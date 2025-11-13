package com.example.trivial;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class TrivialController {

    @FXML
    private Label preguntaLabel;
    @FXML
    private VBox opcionesBox;
    @FXML
    private Button opcion1Button;
    @FXML
    private Button opcion2Button;
    @FXML
    private Button opcion3Button;
    @FXML
    private Button opcion4Button;
    @FXML
    private Label resultadoLabel;
    @FXML
    private Button siguienteButton;

    private int preguntaActual = 0;

    private static class Pregunta {
        String enunciado;
        String[] opciones;
        int respuestaCorrecta;

        Pregunta(String enunciado, String[] opciones, int respuestaCorrecta) {
            this.enunciado = enunciado;
            this.opciones = opciones;
            this.respuestaCorrecta = respuestaCorrecta;
        }
    }

    private final Pregunta[] preguntas = new Pregunta[]{
            new Pregunta("¿Cuál es la capital de Francia?", new String[]{"Berlín", "Madrid", "París", "Roma"}, 2),
            new Pregunta("¿Cuál es el resultado de 2 + 2?", new String[]{"3", "4", "5", "6"}, 1),
            new Pregunta("¿En qué año se descubrió América?", new String[]{"1492", "1592", "1692", "1792"}, 0)
    };

    @FXML
    public void initialize() {
        cargarPregunta();
    }

    private void cargarPregunta() {
        Pregunta p = preguntas[preguntaActual];
        preguntaLabel.setText(p.enunciado);
        opcion1Button.setText(p.opciones[0]);
        opcion2Button.setText(p.opciones[1]);
        opcion3Button.setText(p.opciones[2]);
        opcion4Button.setText(p.opciones[3]);

        resultadoLabel.setText("");
        siguienteButton.setVisible(false);
        opcionesBox.setDisable(false);
    }

    @FXML
    void opcionSeleccionada(ActionEvent event) {
        Button botonSeleccionado = (Button) event.getSource();
        int indiceSeleccionado = -1;
        if (botonSeleccionado == opcion1Button) {
            indiceSeleccionado = 0;
        } else if (botonSeleccionado == opcion2Button) {
            indiceSeleccionado = 1;
        } else if (botonSeleccionado == opcion3Button) {
            indiceSeleccionado = 2;
        } else if (botonSeleccionado == opcion4Button) {
            indiceSeleccionado = 3;
        }

        if (indiceSeleccionado == preguntas[preguntaActual].respuestaCorrecta) {
            resultadoLabel.setText("¡Correcto!");
            resultadoLabel.setStyle("-fx-text-fill: green;");
        } else {
            resultadoLabel.setText("Incorrecto.");
            resultadoLabel.setStyle("-fx-text-fill: red;");
        }

        opcionesBox.setDisable(true);
        siguienteButton.setVisible(true);
    }

    @FXML
    void siguientePregunta() {
        preguntaActual++;
        if (preguntaActual < preguntas.length) {
            cargarPregunta();
        } else {
            preguntaLabel.setText("¡Fin del juego!");
            opcionesBox.setVisible(false);
            siguienteButton.setVisible(false);
            resultadoLabel.setText("");
        }
    }
}